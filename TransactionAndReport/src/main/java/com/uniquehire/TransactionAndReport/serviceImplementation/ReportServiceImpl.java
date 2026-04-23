package com.uniquehire.TransactionAndReport.serviceImplementation;

import com.uniquehire.TransactionAndReport.dto.*;
import com.uniquehire.TransactionAndReport.dto.External.*;
import com.uniquehire.TransactionAndReport.dto.Extra.*;
import com.uniquehire.TransactionAndReport.enums.LoanStatus;
import com.uniquehire.TransactionAndReport.repository.TransactionRepository;
import com.uniquehire.TransactionAndReport.service.ExternalApiService;
import com.uniquehire.TransactionAndReport.service.ReportService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final TransactionRepository transactionRepository;
    private final ExternalApiService externalApiService;

    public ReportServiceImpl(TransactionRepository transactionRepository,
                             ExternalApiService externalApiService) {
        this.transactionRepository = transactionRepository;
        this.externalApiService = externalApiService;
    }

    @Override
    public ReportSummaryDto getSummaryReport(LocalDate fromDate, LocalDate toDate) {
        return buildSummaryResponse(fromDate, toDate);
    }

    @Override
    public List<LoanReportItemDto> getLoanReport() {
        List<ExternalLoanDto> loans = fetchLoansFromLoanService();
        return buildLoanReportResponse(loans);
    }

    @Override
    public List<CollectionReportItemDto> getCollectionReport() {
        return groupCollectionByAgent();
    }

    // =========================
    // NEW ADD-ON REPORT METHODS
    // =========================

    @Override
    public LoanDetailReportDto getLoanDetailReport(Long loanId) {
        ExternalLoanDto loan = externalApiService.getLoanById(loanId);

        if (loan == null) {
            return new LoanDetailReportDto();
        }

        List<ExternalPaymentDto> allPayments = fetchPaymentsFromPaymentService();

        BigDecimal totalEmiCollected = getTotalPaidAmountForLoan(loanId, allPayments);
        BigDecimal totalFineCollected = getTotalFineAmountForLoan(loanId, allPayments);

        BigDecimal disbursedAmount = getSafeAmount(loan.getGivenAmount());
        BigDecimal remainingEmiAmount = disbursedAmount.subtract(totalEmiCollected);

        if (remainingEmiAmount.compareTo(BigDecimal.ZERO) < 0) {
            remainingEmiAmount = BigDecimal.ZERO;
        }

        long completedDays = getCompletedDaysFromStartDate(loan.getStartDate());
        int totalLoanDays = getSafeLoanDays(loan.getDays());

        String completedDaysText = completedDays + "/" + totalLoanDays;

        LoanDetailReportDto response = new LoanDetailReportDto();
        response.setLoanId(loan.getLoanId());
        response.setCustomerName(getCustomerNameById(loan.getCustomerId()));
        response.setAgentName(getAgentNameById(loan.getAgentId()));
        response.setPlanName(getPlanNameByPlanId(loan.getPlanId()));
        response.setLoanAmount(getSafeAmount(loan.getTotalAmount()));
        response.setDisbursedAmount(disbursedAmount);
        response.setEmiCollected(totalEmiCollected);
        response.setFineCollected(totalFineCollected);
        response.setRemainingEmi(remainingEmiAmount);
        response.setDaysDone(completedDaysText);
        response.setStatus(loan.getStatus());

        return response;
    }

    @Override
    public MissedPaymentAlertDto getMissedPaymentAlert(Long loanId) {
        ExternalLoanDto loan = externalApiService.getLoanById(loanId);

        if (loan == null) {
            return new MissedPaymentAlertDto();
        }

        int missedDays = getSafeOverdueDays(loan.getOverduedays());
        BigDecimal emiAmount = getSafeAmount(loan.getDailyEmi());
        BigDecimal fineAmount = BigDecimal.valueOf(missedDays);
        BigDecimal totalDueAmount = emiAmount.add(fineAmount);

        MissedPaymentAlertDto response = new MissedPaymentAlertDto();
        response.setLoanId(loan.getLoanId());
        response.setMissedDays(missedDays);
        response.setEmiAmount(emiAmount);
        response.setFineAmount(fineAmount);
        response.setTotalDue(totalDueAmount);

        if (missedDays > 0) {
            response.setStatus(LoanStatus.OVERDUE.name());
        } else {
            response.setStatus(loan.getStatus());
        }

        return response;
    }

    @Override
    public DefaultWarningDto getDefaultWarningReport(Long loanId) {
        ExternalLoanDto loan = externalApiService.getLoanById(loanId);

        if (loan == null) {
            return new DefaultWarningDto();
        }

        int missedDays = getSafeOverdueDays(loan.getOverduedays());
        BigDecimal dailyEmiAmount = getSafeAmount(loan.getDailyEmi());
        BigDecimal totalDueAmount = dailyEmiAmount.multiply(BigDecimal.valueOf(missedDays));

        DefaultWarningDto response = new DefaultWarningDto();
        response.setLoanId(loan.getLoanId());
        response.setMissedDays(missedDays);
        response.setTotalDue(totalDueAmount);

        if (missedDays >= 5) {
            response.setRiskMessage("Risk of DEFAULT");
            response.setStatus("WARNING");
        } else {
            response.setRiskMessage("No default warning yet");
            response.setStatus(loan.getStatus());
        }

        return response;
    }

    @Override
    public DefaultWarningDto getLoanDefaultReport(Long loanId) {
        ExternalLoanDto loan = externalApiService.getLoanById(loanId);

        if (loan == null) {
            return new DefaultWarningDto();
        }

        int missedDays = getSafeOverdueDays(loan.getOverduedays());
        BigDecimal dailyEmiAmount = getSafeAmount(loan.getDailyEmi());
        BigDecimal totalDueAmount = dailyEmiAmount.multiply(BigDecimal.valueOf(missedDays));

        DefaultWarningDto response = new DefaultWarningDto();
        response.setLoanId(loan.getLoanId());
        response.setMissedDays(missedDays);
        response.setTotalDue(totalDueAmount);

        if (missedDays >= 10) {
            response.setRiskMessage("Loan is in DEFAULT state");
            response.setStatus(LoanStatus.DEFAULT.name());
        } else {
            response.setRiskMessage("Loan is not yet defaulted");
            response.setStatus(loan.getStatus());
        }

        return response;
    }

    @Override
    public LoanCompletedReportDto getLoanCompletedReport(Long loanId) {
        ExternalLoanDto loan = externalApiService.getLoanById(loanId);

        if (loan == null) {
            return new LoanCompletedReportDto();
        }

        List<ExternalPaymentDto> allPayments = fetchPaymentsFromPaymentService();

        BigDecimal totalPaidAmount = getTotalPaidAmountForLoan(loanId, allPayments);
        BigDecimal totalFineAmount = getTotalFineAmountForLoan(loanId, allPayments);
        BigDecimal profitAmount = getSafeAmount(loan.getAdvance()).add(totalFineAmount);

        LoanCompletedReportDto response = new LoanCompletedReportDto();
        response.setLoanId(loan.getLoanId());
        response.setTotalDays(getSafeLoanDays(loan.getDays()));
        response.setTotalPaid(totalPaidAmount);
        response.setTotalFine(totalFineAmount);
        response.setProfit(profitAmount);

        if (LoanStatus.CLOSED.name().equalsIgnoreCase(loan.getStatus())) {
            response.setStatus(LoanStatus.CLOSED.name());
        } else {
            response.setStatus(loan.getStatus());
        }

        return response;
    }

    @Override
    public AdminDashboardDto getAdminDashboardReport() {
        List<ExternalLoanDto> allLoans = fetchLoansFromLoanService();
        List<ExternalPaymentDto> allPayments = fetchPaymentsFromPaymentService();

        BigDecimal totalDisbursedAmount = BigDecimal.ZERO;
        BigDecimal totalAdvanceProfit = BigDecimal.ZERO;
        BigDecimal totalEmiCollected = BigDecimal.ZERO;
        BigDecimal totalFineCollected = BigDecimal.ZERO;

        int totalOverdueLoans = 0;
        int totalDefaultLoans = 0;

        for (ExternalLoanDto loan : allLoans) {
            totalDisbursedAmount = totalDisbursedAmount.add(getSafeAmount(loan.getGivenAmount()));
            totalAdvanceProfit = totalAdvanceProfit.add(getSafeAmount(loan.getAdvance()));

            if (LoanStatus.OVERDUE.name().equalsIgnoreCase(loan.getStatus())) {
                totalOverdueLoans++;
            }

            if (LoanStatus.DEFAULT.name().equalsIgnoreCase(loan.getStatus())) {
                totalDefaultLoans++;
            }
        }

        for (ExternalPaymentDto payment : allPayments) {
            totalEmiCollected = totalEmiCollected.add(getSafeAmount(payment.getPaidAmount()));
            totalFineCollected = totalFineCollected.add(getSafeAmount(payment.getFine()));
        }

        AdminDashboardDto response = new AdminDashboardDto();
        response.setTotalLoans(allLoans.size());
        response.setTotalDisbursed(totalDisbursedAmount);
        response.setAdvanceProfit(totalAdvanceProfit);
        response.setEmiCollected(totalEmiCollected);
        response.setFineCollected(totalFineCollected);
        response.setOverdueLoans(totalOverdueLoans);
        response.setDefaultLoans(totalDefaultLoans);

        return response;
    }

    @Override
    public DailyCollectionReportDto getDailyCollectionReport(LocalDate date) {
        List<ExternalPaymentDto> allPayments = fetchPaymentsFromPaymentService();

        BigDecimal totalEmiCollected = BigDecimal.ZERO;
        BigDecimal totalFineCollected = BigDecimal.ZERO;

        for (ExternalPaymentDto payment : allPayments) {
            if (payment.getPaymentDate() != null && payment.getPaymentDate().equals(date)) {
                totalEmiCollected = totalEmiCollected.add(getSafeAmount(payment.getPaidAmount()));
                totalFineCollected = totalFineCollected.add(getSafeAmount(payment.getFine()));
            }
        }

        BigDecimal totalRevenue = totalEmiCollected.add(totalFineCollected);

        DailyCollectionReportDto response = new DailyCollectionReportDto();
        response.setDate(date);
        response.setEmiCollected(totalEmiCollected);
        response.setFineCollected(totalFineCollected);
        response.setTotalRevenue(totalRevenue);

        return response;
    }



    // =========================
    // HELPER METHODS
    // =========================

    private BigDecimal getSafeAmount(BigDecimal amount) {
        return amount != null ? amount : BigDecimal.ZERO;
    }

    private int getSafeOverdueDays(Integer overdueDays) {
        return overdueDays != null ? overdueDays : 0;
    }

    private int getSafeLoanDays(Integer totalDays) {
        return totalDays != null ? totalDays : 0;
    }

    private long getCompletedDaysFromStartDate(LocalDate startDate) {
        if (startDate == null) {
            return 0;
        }

        long days = LocalDate.now().toEpochDay() - startDate.toEpochDay();

//        ChronoUnit.DAYS.between(date1, date2)
//        “ChronoUnit(Java built-in class) helps find difference between two dates like days, months, years.”
        if (days < 0) {
            return 0;
        }
        return days;
    }

    private BigDecimal getTotalPaidAmountForLoan(Long loanId, List<ExternalPaymentDto> allPayments) {
        BigDecimal totalPaidAmount = BigDecimal.ZERO;

        for (ExternalPaymentDto payment : allPayments) {
            if (payment.getLoanId() != null
                    && payment.getLoanId().equals(loanId)
                    && payment.getPaidAmount() != null) {
                totalPaidAmount = totalPaidAmount.add(payment.getPaidAmount());
            }
        }

        return totalPaidAmount;
    }

    private BigDecimal getTotalFineAmountForLoan(Long loanId, List<ExternalPaymentDto> allPayments) {
        BigDecimal totalFineAmount = BigDecimal.ZERO;

        for (ExternalPaymentDto payment : allPayments) {
            if (payment.getLoanId() != null
                    && payment.getLoanId().equals(loanId)
                    && payment.getFine() != null) {
                totalFineAmount = totalFineAmount.add(payment.getFine());
            }
        }

        return totalFineAmount;
    }

    private List<ExternalLoanDto> fetchLoansFromLoanService() {
        List<ExternalLoanDto> loans = externalApiService.getAllLoans();
        return loans != null ? loans : new ArrayList<>();
    }

    private List<ExternalPaymentDto> fetchPaymentsFromPaymentService() {
        List<ExternalPaymentDto> payments = externalApiService.getAllPayments();
        return payments != null ? payments : new ArrayList<>();
    }

    private List<ExternalAgentDto> fetchAgentsFromAgentService() {
        List<ExternalAgentDto> agents = externalApiService.getAllAgents();
        return agents != null ? agents : new ArrayList<>();
    }

    private List<ExternalPlanDto> fetchPlansFromPlanService() {
        List<ExternalPlanDto> plans = externalApiService.getAllPlans();
        return plans != null ? plans : new ArrayList<>();
    }

    private String getAgentNameById(Long agentId) {
        if (agentId == null) {
            return "Unknown";
        }

        ExternalAgentDto agent = externalApiService.getAgentById(agentId);
        if (agent != null && agent.getName() != null) {
            return agent.getName();
        }

        return "Unknown";
    }

    private String getCustomerNameById(Long customerId) {
        if (customerId == null) {
            return "Unknown";
        }

        ExternalCustomerDto customer = externalApiService.getCustomerById(customerId);
        if (customer != null && customer.getName() != null) {
            return customer.getName();
        }

        return "Unknown";
    }

    private String getPlanNameByPlanId(Long planId) {
        if (planId == null) {
            return "Unknown";
        }

        List<ExternalPlanDto> plans = fetchPlansFromPlanService();

        for (ExternalPlanDto plan : plans) {
            if (plan.getPlanId() != null && plan.getPlanId().equals(planId)) {
                return plan.getName();
            }
        }

        return "Unknown";
    }

    private ReportSummaryDto buildSummaryResponse(LocalDate fromDate, LocalDate toDate) {
        List<ExternalLoanDto> loans = fetchLoansFromLoanService();
        List<ExternalPaymentDto> payments = fetchPaymentsFromPaymentService();
        List<ExternalAgentDto> agents = fetchAgentsFromAgentService();

        if (fromDate == null) {
            fromDate = LocalDate.now().withDayOfMonth(1);
        }

        if (toDate == null) {
            toDate = LocalDate.now();
        }

        int totalLoans = 0;
        int activeLoans = 0;
        int closedLoans = 0;
        int defaultedLoans = 0;

        BigDecimal totalCollected = BigDecimal.ZERO;
        BigDecimal pendingAmount = BigDecimal.ZERO;
        BigDecimal overdueAmount = BigDecimal.ZERO;

        BigDecimal totalFinesDue = BigDecimal.ZERO;
        BigDecimal totalFinesPaid = BigDecimal.ZERO;
        BigDecimal totalFinesWaived = BigDecimal.ZERO;

        Long topAgentId = null;
        String topAgentName = "N/A";
        BigDecimal topAgentCollection = BigDecimal.ZERO;

        List<ExternalLoanDto> filteredLoans = new ArrayList<>();
        List<ExternalPaymentDto> filteredPayments = new ArrayList<>();

        for (ExternalLoanDto loan : loans) {
            LocalDate loanStartDate = loan.getStartDate();

            if (loanStartDate == null || (!loanStartDate.isBefore(fromDate) && !loanStartDate.isAfter(toDate))) {
                filteredLoans.add(loan);
            }
        }

        for (ExternalPaymentDto payment : payments) {
            LocalDate paymentDate = payment.getPaymentDate();

            if (paymentDate == null || (!paymentDate.isBefore(fromDate) && !paymentDate.isAfter(toDate))) {
                filteredPayments.add(payment);
            }
        }

        totalLoans = filteredLoans.size();

        for (ExternalLoanDto loan : filteredLoans) {
            if (LoanStatus.ACTIVE.name().equalsIgnoreCase(loan.getStatus())) {
                activeLoans++;
            } else if (LoanStatus.CLOSED.name().equalsIgnoreCase(loan.getStatus())) {
                closedLoans++;
            } else if (LoanStatus.DEFAULT.name().equalsIgnoreCase(loan.getStatus())) {
                defaultedLoans++;
            }

            if (loan.getGivenAmount() != null) {
                pendingAmount = pendingAmount.add(loan.getGivenAmount());
            }

            if (loan.getOverduedays() != null && loan.getOverduedays() > 0 && loan.getDailyEmi() != null) {
                overdueAmount = overdueAmount.add(loan.getDailyEmi());
            }

            if (loan.getTotalFine() != null) {
                totalFinesDue = totalFinesDue.add(loan.getTotalFine());
            }
        }

        for (ExternalPaymentDto payment : filteredPayments) {
            if (payment.getPaidAmount() != null) {
                totalCollected = totalCollected.add(payment.getPaidAmount());
            }

            if (payment.getFine() != null) {
                totalFinesPaid = totalFinesPaid.add(payment.getFine());
            }
        }

        pendingAmount = pendingAmount.subtract(totalCollected);
        if (pendingAmount.compareTo(BigDecimal.ZERO) < 0) {
            pendingAmount = BigDecimal.ZERO;
        }

        for (ExternalAgentDto agent : agents) {
            BigDecimal agentCollection = BigDecimal.ZERO;

            for (ExternalLoanDto loan : filteredLoans) {
                if (loan.getAgentId() != null && loan.getAgentId().equals(agent.getAgentId())) {
                    for (ExternalPaymentDto payment : filteredPayments) {
                        if (payment.getLoanId() != null
                                && payment.getLoanId().equals(loan.getLoanId())
                                && payment.getPaidAmount() != null) {
                            agentCollection = agentCollection.add(payment.getPaidAmount());
                        }
                    }
                }
            }

            if (agentCollection.compareTo(topAgentCollection) > 0) {
                topAgentCollection = agentCollection;
                topAgentId = agent.getAgentId();
                topAgentName = agent.getName();
            }
        }

        ReportSummaryDto dto = new ReportSummaryDto();

        dto.setDateRange(new ReportSummaryDto.DateRangeDto(fromDate, toDate));
        dto.setSummary(new ReportSummaryDto.SummarySectionDto(
                totalLoans,
                activeLoans,
                closedLoans,
                defaultedLoans
        ));
        dto.setCollections(new ReportSummaryDto.CollectionsSectionDto(
                totalCollected,
                pendingAmount,
                overdueAmount
        ));
        dto.setFines(new ReportSummaryDto.FinesSectionDto(
                totalFinesDue,
                totalFinesPaid,
                totalFinesWaived
        ));
        dto.setAgents(new ReportSummaryDto.AgentsSectionDto(
                agents.size(),
                new ReportSummaryDto.TopPerformerDto(
                        topAgentId,
                        topAgentName,
                        topAgentCollection
                )
        ));
        dto.setGeneratedAt(LocalDateTime.now());

        return dto;
    }

    private List<LoanReportItemDto> buildLoanReportResponse(List<ExternalLoanDto> loans) {
        List<LoanReportItemDto> reportList = new ArrayList<>();
        List<ExternalPaymentDto> allPayments = fetchPaymentsFromPaymentService();

        for (ExternalLoanDto loan : loans) {
            BigDecimal amountPaid = BigDecimal.ZERO;

            for (ExternalPaymentDto payment : allPayments) {
                if (payment.getLoanId() != null
                        && payment.getLoanId().equals(loan.getLoanId())
                        && payment.getPaidAmount() != null) {
                    amountPaid = amountPaid.add(payment.getPaidAmount());
                }
            }

            BigDecimal givenAmount = defaultIfNull(loan.getGivenAmount());
            BigDecimal remainingAmount = givenAmount.subtract(amountPaid);

            if (remainingAmount.compareTo(BigDecimal.ZERO) < 0) {
                remainingAmount = BigDecimal.ZERO;
            }

            double percentageCompleted = 0.0;
            if (givenAmount.compareTo(BigDecimal.ZERO) > 0) {
                percentageCompleted = amountPaid
                        .multiply(BigDecimal.valueOf(100))
                        .divide(givenAmount, 2, RoundingMode.HALF_UP)
                        .doubleValue();
            }

            LoanReportItemDto dto = new LoanReportItemDto();
            dto.setLoanId(loan.getLoanId());
            dto.setCustomerId(loan.getCustomerId());
            dto.setCustomerName(getCustomerNameById(loan.getCustomerId()));
            dto.setAgentId(loan.getAgentId());
            dto.setAgentName(getAgentNameById(loan.getAgentId()));
            dto.setPlanName(getPlanNameByPlanId(loan.getPlanId()));
            dto.setTotalAmount(loan.getTotalAmount());
            dto.setAdvance(loan.getAdvance());
            dto.setGivenAmount(loan.getGivenAmount());
            dto.setAmountPaid(amountPaid);
            dto.setRemainingAmount(remainingAmount);
            dto.setDailyEmi(loan.getDailyEmi());
            dto.setStartDate(loan.getStartDate());
            dto.setStatus(loan.getStatus());
            dto.setOverdueDays(loan.getOverduedays());
            dto.setTotalFine(loan.getTotalFine());
            dto.setPercentageCompleted(percentageCompleted);

            reportList.add(dto);
        }

        return reportList;
    }

    private List<CollectionReportItemDto> groupCollectionByAgent() {
        List<CollectionReportItemDto> reportList = new ArrayList<>();
        List<ExternalLoanDto> loans = fetchLoansFromLoanService();
        List<ExternalPaymentDto> payments = fetchPaymentsFromPaymentService();
        List<ExternalAgentDto> agents = fetchAgentsFromAgentService();

        for (ExternalAgentDto agent : agents) {
            int assignedLoans = 0;
            BigDecimal collectionTarget = BigDecimal.ZERO;
            BigDecimal amountCollected = BigDecimal.ZERO;
            BigDecimal pendingAmount = BigDecimal.ZERO;
            BigDecimal overdueAmount = BigDecimal.ZERO;

            for (ExternalLoanDto loan : loans) {
                if (loan.getAgentId() != null && loan.getAgentId().equals(agent.getAgentId())) {
                    assignedLoans++;

                    if (loan.getGivenAmount() != null) {
                        collectionTarget = collectionTarget.add(loan.getGivenAmount());
                        pendingAmount = pendingAmount.add(loan.getGivenAmount());
                    }

                    if (loan.getOverduedays() != null
                            && loan.getOverduedays() > 0
                            && loan.getDailyEmi() != null) {
                        overdueAmount = overdueAmount.add(loan.getDailyEmi());
                    }

                    for (ExternalPaymentDto payment : payments) {
                        if (payment.getLoanId() != null
                                && payment.getLoanId().equals(loan.getLoanId())
                                && payment.getPaidAmount() != null) {
                            amountCollected = amountCollected.add(payment.getPaidAmount());
                        }
                    }
                }
            }

            pendingAmount = pendingAmount.subtract(amountCollected);
            if (pendingAmount.compareTo(BigDecimal.ZERO) < 0) {
                pendingAmount = BigDecimal.ZERO;
            }

            double targetAchievement = 0.0;
            if (collectionTarget.compareTo(BigDecimal.ZERO) > 0) {
                targetAchievement = amountCollected
                        .multiply(BigDecimal.valueOf(100))
                        .divide(collectionTarget, 2, RoundingMode.HALF_UP)
                        .doubleValue();
            }

            BigDecimal commissionRate = agent.getCommissionRate() != null
                    ? agent.getCommissionRate()
                    : BigDecimal.ZERO;

            BigDecimal commissionEarned = amountCollected
                    .multiply(commissionRate)
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

            CollectionReportItemDto dto = new CollectionReportItemDto();
            dto.setAgentId(agent.getAgentId());
            dto.setAgentName(agent.getName());
            dto.setArea(agent.getArea());
            dto.setAssignedLoans(assignedLoans);
            dto.setCollectionTarget(collectionTarget);
            dto.setAmountCollected(amountCollected);
            dto.setTargetAchievement(targetAchievement);
            dto.setPendingAmount(pendingAmount);
            dto.setOverdueAmount(overdueAmount);
            dto.setCommissionEarned(commissionEarned);

            reportList.add(dto);
        }

        return reportList;
    }

    private BigDecimal defaultIfNull(BigDecimal value) {
        return value != null ? value : BigDecimal.ZERO;
    }
}