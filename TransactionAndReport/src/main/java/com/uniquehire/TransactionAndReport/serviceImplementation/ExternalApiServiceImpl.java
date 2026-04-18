package com.uniquehire.TransactionAndReport.serviceImplementation;

import com.uniquehire.TransactionAndReport.dto.*;
import com.uniquehire.TransactionAndReport.service.ExternalApiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class ExternalApiServiceImpl implements ExternalApiService {

    private final RestTemplate restTemplate;

    @Value("${loan.service.base-url}")
    private String loanServiceBaseUrl;

    @Value("${payment.service.base-url}")
    private String paymentServiceBaseUrl;

    @Value("${customer.service.base-url}")
    private String customerServiceBaseUrl;

    @Value("${plans.service.base-url}")
    private String plansServiceBaseUrl;

    @Value("${agent.service.base-url}")
    private String agentServiceBaseUrl;

    public ExternalApiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<ExternalLoanDto> getAllLoans() {
        try {
            ResponseEntity<ApiWrapperDto<List<ExternalLoanDto>>> response =
                    restTemplate.exchange(
                            loanServiceBaseUrl + "/loans",
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<ApiWrapperDto<List<ExternalLoanDto>>>() {}
                    );

            ApiWrapperDto<List<ExternalLoanDto>> body = response.getBody();
            return (body != null && body.getData() != null) ? body.getData() : Collections.emptyList();

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public ExternalLoanDto getLoanById(Long loanId) {
        try {
            ResponseEntity<ApiWrapperDto<ExternalLoanDto>> response =
                    restTemplate.exchange(
                            loanServiceBaseUrl + "/loans/" + loanId,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<ApiWrapperDto<ExternalLoanDto>>() {}
                    );

            ApiWrapperDto<ExternalLoanDto> body = response.getBody();
            return (body != null) ? body.getData() : null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ExternalPaymentDto> getAllPayments() {
        try {
            System.out.println("=== PAYMENT URL FROM PROPERTY === " + paymentServiceBaseUrl);

            ResponseEntity<ApiWrapperDto<List<ExternalPaymentDto>>> response =
                    restTemplate.exchange(
                            paymentServiceBaseUrl,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<ApiWrapperDto<List<ExternalPaymentDto>>>() {}
                    );

            System.out.println("=== PAYMENT RESPONSE STATUS === " + response.getStatusCode());

            ApiWrapperDto<List<ExternalPaymentDto>> body = response.getBody();
            System.out.println("=== PAYMENT RESPONSE BODY === " + body);

            return (body != null && body.getData() != null) ? body.getData() : Collections.emptyList();

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public ExternalPaymentDto getPaymentById(Long paymentId) {
        try {
            List<ExternalPaymentDto> payments = getAllPayments();

            return payments.stream()
                    .filter(payment -> payment != null && paymentId.equals(payment.getPaymentId()))
                    .findFirst()
                    .orElse(null);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ExternalAgentDto> getAllAgents() {
        try {
            ResponseEntity<ApiWrapperDto<List<ExternalAgentDto>>> response =
                    restTemplate.exchange(
                            agentServiceBaseUrl,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<ApiWrapperDto<List<ExternalAgentDto>>>() {}
                    );

            ApiWrapperDto<List<ExternalAgentDto>> body = response.getBody();
            return (body != null && body.getData() != null) ? body.getData() : Collections.emptyList();

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public ExternalAgentDto getAgentById(Long agentId) {
        try {
            ResponseEntity<ApiWrapperDto<ExternalAgentDto>> response =
                    restTemplate.exchange(
                            agentServiceBaseUrl + "/" + agentId,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<ApiWrapperDto<ExternalAgentDto>>() {}
                    );

            ApiWrapperDto<ExternalAgentDto> body = response.getBody();
            return (body != null) ? body.getData() : null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ExternalCustomerDto getCustomerById(Long customerId) {
        try {
            ResponseEntity<ApiWrapperDto<ExternalCustomerDto>> response =
                    restTemplate.exchange(
                            customerServiceBaseUrl + "/customers/" + customerId,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<ApiWrapperDto<ExternalCustomerDto>>() {}
                    );

            ApiWrapperDto<ExternalCustomerDto> body = response.getBody();
            return (body != null) ? body.getData() : null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ExternalPlanDto> getAllPlans() {
        try {
            ResponseEntity<ApiWrapperDto<List<ExternalPlanDto>>> response =
                    restTemplate.exchange(
                            plansServiceBaseUrl + "/plans",
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<ApiWrapperDto<List<ExternalPlanDto>>>() {}
                    );

            ApiWrapperDto<List<ExternalPlanDto>> body = response.getBody();
            return (body != null && body.getData() != null) ? body.getData() : Collections.emptyList();

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}