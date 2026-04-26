package com.uniquehire.loanagentmodule.controller;

import com.uniquehire.loanagentmodule.dto.Request.LoanRequestDTO;
import com.uniquehire.loanagentmodule.dto.Response.LoanResponseDTO;
import com.uniquehire.loanagentmodule.service.LoanService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    // 🔥 CREATE LOAN
    @PostMapping
    public LoanResponseDTO createLoan(
            @RequestBody LoanRequestDTO request,
            HttpServletRequest httpRequest) {

        Long userId = (Long) httpRequest.getAttribute("userId");

        return loanService.createLoan(userId, request.getPlanId());
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
    @GetMapping("/my")
    public List<LoanResponseDTO> getMyLoans(
            @RequestHeader("X-USER-ID") Long userId) {

        return loanService.getLoansByUserId(userId);
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

    @PutMapping("/update-payment")
    public String updateAfterPayment(@RequestParam Long loanId,
                                     @RequestParam BigDecimal paid,
                                     @RequestParam BigDecimal due,
                                     @RequestParam BigDecimal fine) {

        loanService.updateAfterPayment(loanId, paid, due, fine);

        return "Loan updated after payment";
    }
}