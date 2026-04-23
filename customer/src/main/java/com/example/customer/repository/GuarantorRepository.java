package com.example.customer.repository;

import com.example.customer.entity.Guarantor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuarantorRepository extends JpaRepository<Guarantor, Long> {

//    List<Guarantor> findByCustomerCustomerId(Long customerId);
//
//    boolean existsByPhoneAndCustomerCustomerId(String phone, Long customerId);
//
//    int countByCustomerCustomerId(Long customerId);
//
//    boolean existsByEmailAndCustomerCustomerId(String email, Long customerId);



        // ✅ Get all guarantors of a customer
        List<Guarantor> findByCustomerCustomerId(Long customerId);

        // ✅ Count guarantors for limit check
        int countByCustomerCustomerId(Long customerId);

        // 🔥 NEW: Global uniqueness using PAN
        boolean existsByPanNumber(String panNumber);

}