//package com.uniquehire.paymentservice.service.impl;
//
//import com.uniquehire.paymentservice.service.LoanIntegrationService;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class LoanIntegrationServiceImpl implements LoanIntegrationService {
//
//    private final RestTemplate restTemplate;
//
//    @Value("${loan.service.base-url}")
//    private String loanServiceBaseUrl;
//
//    public LoanIntegrationServiceImpl(RestTemplate restTemplate) {
//
//        this.restTemplate = restTemplate;
//    }
//
//    @Override
//    public LoanDetails getLoanDetails(Long loanId) {
//
//        String url = loanServiceBaseUrl + "/" + loanId;
//
//        Map response = restTemplate.getForObject(url, Map.class);
//
//        Map data = (Map) response.get("data");
//
//        LoanDetails loan = new LoanDetails();
//
//        loan.setLoanId(Long.valueOf(data.get("loanId").toString()));
//        loan.setLoanStatus(data.get("status").toString());
//        loan.setDailyEmi(new BigDecimal(data.get("dailyEmi").toString()));
//        loan.setStartDate(LocalDate.parse(data.get("startDate").toString()));
//        loan.setEndDate(LocalDate.parse(data.get("endDate").toString()));
//
//        loan.setAmountPaid(BigDecimal.ZERO);
//        loan.setTotalFine(new BigDecimal(data.get("totalFine").toString()));
//
//        return loan;
//
//    }
//
//    @Override
//    public void updateLoanAfterPayment(Long loanId, BigDecimal paidAmount, BigDecimal fineAmount, LocalDate paymentDate) {
//        Map<String, Object> request = new HashMap<>();
//        request.put("paidAmount", paidAmount);
//        request.put("fineAmount", fineAmount);
//        request.put("paymentDate", paymentDate);
//
//        // later:
//        // restTemplate.put(loanServiceBaseUrl + "/" + loanId + "/payment-update", request);
//    }
//}