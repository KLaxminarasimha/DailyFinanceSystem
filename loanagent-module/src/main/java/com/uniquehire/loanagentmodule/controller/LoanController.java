package com.uniquehire.loanagentmodule.controller;

import com.uniquehire.loanagentmodule.dto.Request.LoanRequestDTO;
import com.uniquehire.loanagentmodule.dto.Response.LoanResponseDTO;
import com.uniquehire.loanagentmodule.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    // 🔥 CREATE LOAN
    @PostMapping
    public LoanResponseDTO createLoan(@RequestBody LoanRequestDTO request) {

        return loanService.createLoan(
                request.getCustomerId(),
                request.getPlanId()
        );
    }

    // 🔥 GET LOAN BY ID
    @GetMapping("/{id}")
    public LoanResponseDTO getLoan(@PathVariable Long id) {
        return loanService.getLoan(id);
    }

    // 🔥 GET ALL LOANS
    @GetMapping
    public List<LoanResponseDTO> getAllLoans() {
        return loanService.getAllLoans();
    }

    // 🔥 GET LOANS BY CUSTOMER
    @GetMapping("/customer/{id}")
    public List<LoanResponseDTO> getLoansByCustomer(@PathVariable Long id) {
        return loanService.getLoansByCustomerId(id);
    }

    // 🔥 UPDATE STATUS
    @PutMapping("/{id}")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam String status,
                               @RequestParam String remarks) {

        loanService.updateLoanStatus(id, status, remarks);
        return "Loan status updated";
    }
}