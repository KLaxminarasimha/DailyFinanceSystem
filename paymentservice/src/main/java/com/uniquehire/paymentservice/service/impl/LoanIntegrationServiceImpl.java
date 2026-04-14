package com.uniquehire.paymentservice.service.impl;

import com.uniquehire.paymentservice.service.LoanIntegrationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoanIntegrationServiceImpl implements LoanIntegrationService {

    private final RestTemplate restTemplate;

    @Value("${loan.service.base-url}")
    private String loanServiceBaseUrl;

    public LoanIntegrationServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public LoanDetails getLoanDetails(Long loanId) {
        // temporary mock data
        // later replace with actual loan service API call

        LoanDetails loanDetails = new LoanDetails();
        loanDetails.setLoanId(loanId);
        loanDetails.setLoanStatus("ACTIVE");
        loanDetails.setDailyEmi(new BigDecimal("2000.00"));
        loanDetails.setStartDate(LocalDate.now().minusDays(10));
        loanDetails.setEndDate(LocalDate.now().plusDays(40));
        loanDetails.setAmountPaid(BigDecimal.ZERO);
        loanDetails.setTotalFine(BigDecimal.ZERO);

        return loanDetails;
    }

    @Override
    public void updateLoanAfterPayment(Long loanId, BigDecimal paidAmount, BigDecimal fineAmount, LocalDate paymentDate) {
        Map<String, Object> request = new HashMap<>();
        request.put("paidAmount", paidAmount);
        request.put("fineAmount", fineAmount);
        request.put("paymentDate", paymentDate);

        // later:
        // restTemplate.put(loanServiceBaseUrl + "/" + loanId + "/payment-update", request);
    }
}