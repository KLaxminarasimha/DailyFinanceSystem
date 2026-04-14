package com.uniquehire.loanagentmodule.repository;

import com.uniquehire.loanagentmodule.entity.Loan;
import com.uniquehire.loanagentmodule.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan,Long>{

    List<Loan> findByCustomerId(Long customerId);

    List<Loan> findByAgentAgentId(Long agentId);

    List<Loan> findByStatus(LoanStatus status);

}
