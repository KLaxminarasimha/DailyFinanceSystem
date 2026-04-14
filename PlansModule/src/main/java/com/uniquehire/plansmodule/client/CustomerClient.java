package com.uniquehire.plansmodule.client;



import com.uniquehire.plansmodule.config.CustomerServiceProperties;
import com.uniquehire.plansmodule.constants.PlanConstants;
import com.uniquehire.plansmodule.dto.response.CustomerIncomeResponse;
import com.uniquehire.plansmodule.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerClient {

    private final RestTemplate restTemplate;
    private final CustomerServiceProperties customerServiceProperties;

    public CustomerIncomeResponse getCustomerIncome(Long customerId) {
        String endpoint = customerServiceProperties.getIncomeEndpoint().replace("{customerId}", String.valueOf(customerId));
        String url = customerServiceProperties.getBaseUrl() + endpoint;

        log.info("Calling customer service for income. customerId={}, url={}", customerId, url);

        CustomerIncomeResponse response = restTemplate.getForObject(url, CustomerIncomeResponse.class);

        if (response == null || response.getIncome() == null) {
            throw new ResourceNotFoundException(PlanConstants.CUSTOMER_INCOME_NOT_FOUND);
        }

        return response;
    }
}