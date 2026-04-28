package com.uniquehire.loanagentmodule.service;

import com.uniquehire.loanagentmodule.dto.Request.LoanRequestDTO;
import com.uniquehire.loanagentmodule.dto.Response.LoanResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface LoanService {

    // 🔥 Create Loan
    LoanResponseDTO createLoan(Long customerId, Long planId);

    // 🔥 Get single loan
    LoanResponseDTO getLoan(Long loanId);

    // 🔥 Get all loans
    List<LoanResponseDTO> getAllLoans();

    // 🔥 Get loans by customer
    List<LoanResponseDTO> getLoansByCustomerId(Long customerId);

    // 🔥 Update status (APPROVED / REJECTED / CLOSED)
    void updateLoanStatus(Long loanId, String status, String remarks);

    void updateAfterPayment(Long loanId,
                            BigDecimal paid,
                            BigDecimal due,
                            BigDecimal fine);
    List<LoanResponseDTO> getLoansByUserId(Long userId);
}