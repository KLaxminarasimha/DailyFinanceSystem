package com.uniquehire.TransactionAndReport.serviceImplementation;

import com.uniquehire.TransactionAndReport.dto.ReportSummaryDto;
import com.uniquehire.TransactionAndReport.repository.TransactionRepository;
import com.uniquehire.TransactionAndReport.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.uniquehire.TransactionAndReport.enums.TransactionStatus.SUCCESS;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public ReportSummaryDto getSummaryReport() {
        return buildSummaryResponse();
    }

    @Override
    public Object getLoanReport() {
        List<Object> loans = fetchLoansFromLoanService();
        return buildLoanReportResponse(loans);
    }


    @Override
    public Object getCollectionReport() {
        List<Object> data = groupCollectionByAgent();
        return buildCollectionResponse(data);
    }

    private List<Object> fetchLoansFromLoanService(){
        return List.of();
    }

    private List<Object> fetchPaymentsFromPaymentService(){
        return List.of();
    }

    private List<Object> fetchFinesFromFineService(){
        return List.of();
    }

    private ReportSummaryDto aggregateSummaryData(){
        ReportSummaryDto dto= new ReportSummaryDto();

        Long successCount = transactionRepository.countByStatus(SUCCESS);

        BigDecimal total = transactionRepository.sumAmountByStatus(SUCCESS);
        dto.setTotalLoans(successCount.intValue());
        dto.setTotalCollection(total);
        return dto;
    }

    private List<Object> filterLoanReportData(){
        return List.of();
    }

    private List<Object> groupCollectionByAgent(){
        return List.of();
    }

    private void calculateAgentPerformance(){

    }

    private ReportSummaryDto buildSummaryResponse(){
        return aggregateSummaryData();
    }

    private Object buildLoanReportResponse(List<Object> data){
        return data;
    }

    private Object buildCollectionResponse(List<Object> data){
        return data;
    }
}
