package com.uniquehire.loanagentmodule.service;

import com.uniquehire.loanagentmodule.dto.Request.LoanRequestDTO;
import com.uniquehire.loanagentmodule.dto.Response.LoanResponseDTO;

import java.util.List;

public interface LoanService {

    // 🔹 Create Loan
    LoanResponseDTO createLoan(LoanRequestDTO request);

    // 🔹 Get single loan
    LoanResponseDTO getLoan(Long loanId);

    // 🔹 Get all loans
    List<LoanResponseDTO> getAllLoans();

    // 🔹 Get loans by customer
    List<LoanResponseDTO> getLoansByCustomerId(Long customerId);

    // 🔹 Update status (optional for later)
    void updateLoanStatus(Long loanId, String status, String remarks);
}