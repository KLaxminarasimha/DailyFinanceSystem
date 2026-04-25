package com.dailyfinance.fund_service.controller;

import com.dailyfinance.fund_service.dto.FundRequestDTO;
import com.dailyfinance.fund_service.dto.FundResponseDTO;
import com.dailyfinance.fund_service.service.FundService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/fund")
@RequiredArgsConstructor
public class FundController {

    private final FundService fundService;

    @GetMapping("/balance")
    public FundResponseDTO getBalance() {
        return fundService.getBalance();
    }

    @PostMapping("/loan")
    public String loan(@RequestBody FundRequestDTO req) {
        fundService.loanDisbursement(req.getAmount(), req.getReferenceId());
        return "Loan disbursed";
    }

    @PostMapping("/emi")
    public String emi(@RequestBody FundRequestDTO req) {
        fundService.emiPayment(req.getAmount(), req.getReferenceId());
        return "EMI received";
    }

    @PostMapping("/penalty")
    public String penalty(@RequestBody FundRequestDTO req) {
        fundService.penalty(req.getAmount(), req.getReferenceId());
        return "Penalty added";
    }

    @PostMapping("/interest")
    public String interest(@RequestBody FundRequestDTO req) {
        fundService.interest(req.getAmount(), req.getReferenceId());
        return "Interest added";
    }
}
