package com.uniquehire.loanagentmodule.service;

import com.uniquehire.loanagentmodule.dto.Request.LoanRequestDTO;
import com.uniquehire.loanagentmodule.dto.Response.LoanResponseDTO;

import java.util.List;

public interface LoanService {

    LoanResponseDTO createLoan(LoanRequestDTO request);

    LoanResponseDTO getLoan(Long id);

    List<LoanResponseDTO> getAllLoans();

    void updateLoanStatus(Long loanId, String status, String remarks);

}
