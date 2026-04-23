package com.uniquehire.paymentservice.repository;

import com.uniquehire.paymentservice.entity.Fine;
import com.uniquehire.paymentservice.enums.FineStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FineRepository extends JpaRepository<Fine,Long > {
    List<Fine> findByLoanId(Long loanId);//get all fines for loan

    List<Fine> findByLoanIdAndStatus(Long loanId, FineStatus status);


}
