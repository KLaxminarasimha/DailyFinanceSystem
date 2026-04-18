package com.uniquehire.TransactionAndReport.serviceImplementation;

import com.uniquehire.TransactionAndReport.dto.CollectionReportItemDto;
import com.uniquehire.TransactionAndReport.dto.ExternalAgentDto;
import com.uniquehire.TransactionAndReport.dto.ExternalCustomerDto;
import com.uniquehire.TransactionAndReport.dto.ExternalLoanDto;
import com.uniquehire.TransactionAndReport.dto.ExternalPaymentDto;
import com.uniquehire.TransactionAndReport.dto.ExternalPlanDto;
import com.uniquehire.TransactionAndReport.dto.LoanReportItemDto;
import com.uniquehire.TransactionAndReport.dto.ReportSummaryDto;
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

        int totalLoans = loans.size();
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
        String topAgentName = null;
        BigDecimal topAgentCollection = BigDecimal.ZERO;

        for (ExternalLoanDto loan : loans) {
            if ("active".equalsIgnoreCase(loan.getStatus())) {
                activeLoans++;
            } else if ("closed".equalsIgnoreCase(loan.getStatus())) {
                closedLoans++;
            } else if ("defaulted".equalsIgnoreCase(loan.getStatus())) {
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

        for (ExternalPaymentDto payment : payments) {
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

            for (ExternalLoanDto loan : loans) {
                if (loan.getAgentId() != null && loan.getAgentId().equals(agent.getAgentId())) {
                    for (ExternalPaymentDto payment : payments) {
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

        totalFinesWaived = BigDecimal.ZERO;

        if (fromDate == null) {
            fromDate = LocalDate.now().withDayOfMonth(1);
        }
        if (toDate == null) {
            toDate = LocalDate.now();
        }

        ReportSummaryDto.DateRangeDto dateRange =
                new ReportSummaryDto.DateRangeDto(fromDate, toDate);

        ReportSummaryDto.SummarySectionDto summary =
                new ReportSummaryDto.SummarySectionDto(
                        totalLoans,
                        activeLoans,
                        closedLoans,
                        defaultedLoans
                );

        ReportSummaryDto.CollectionsSectionDto collections =
                new ReportSummaryDto.CollectionsSectionDto(
                        totalCollected,
                        pendingAmount,
                        overdueAmount
                );

        ReportSummaryDto.FinesSectionDto fines =
                new ReportSummaryDto.FinesSectionDto(
                        totalFinesDue,
                        totalFinesPaid,
                        totalFinesWaived
                );

        ReportSummaryDto.TopPerformerDto topPerformer =
                new ReportSummaryDto.TopPerformerDto(
                        topAgentId,
                        topAgentName,
                        topAgentCollection
                );

        ReportSummaryDto.AgentsSectionDto agentsSection =
                new ReportSummaryDto.AgentsSectionDto(
                        agents.size(),
                        topPerformer
                );

        ReportSummaryDto dto = new ReportSummaryDto();
        dto.setDateRange(dateRange);
        dto.setSummary(summary);
        dto.setCollections(collections);
        dto.setFines(fines);
        dto.setAgents(agentsSection);
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

            BigDecimal givenAmount = loan.getGivenAmount() != null
                    ? loan.getGivenAmount()
                    : BigDecimal.ZERO;

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

            String agentName = getAgentNameById(loan.getAgentId());
            String customerName = getCustomerNameById(loan.getCustomerId());
            String planName = getPlanNameByPlanId(loan.getPlanId());

            LoanReportItemDto dto = new LoanReportItemDto();
            dto.setLoanId(loan.getLoanId());
            dto.setCustomerId(loan.getCustomerId());
            dto.setCustomerName(customerName);
            dto.setAgentId(loan.getAgentId());
            dto.setAgentName(agentName);
            dto.setPlanName(planName);
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

            BigDecimal commissionEarned = amountCollected.multiply(BigDecimal.valueOf(0.025));

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
}