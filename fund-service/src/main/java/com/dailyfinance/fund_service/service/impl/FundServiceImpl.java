package com.dailyfinance.fund_service.service.impl;

import com.dailyfinance.fund_service.dto.FundResponseDTO;
import com.dailyfinance.fund_service.entity.Fund;
import com.dailyfinance.fund_service.entity.FundTransactionLog;
import com.dailyfinance.fund_service.repository.FundRepository;
import com.dailyfinance.fund_service.repository.FundTransactionLogRepository;
import com.dailyfinance.fund_service.service.FundService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FundServiceImpl implements FundService {

    private final FundRepository fundRepository;
    private final FundTransactionLogRepository logRepository;

    private Fund getFund() {
        return fundRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Fund not found"));
    }

    @Override
    public FundResponseDTO getBalance() {

        Fund fund = getFund();

        FundResponseDTO dto = new FundResponseDTO();
        dto.setId(fund.getId());
        dto.setBalance(fund.getBalance());

        return dto;
    }

    // 🔥 LOAN DISBURSEMENT (DEBIT)
    @Override
    public void loanDisbursement(BigDecimal amount, Long loanId) {

        Fund fund = getFund();

        if (fund.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        fund.setBalance(fund.getBalance().subtract(amount));
        fundRepository.save(fund);

        saveLog("DEBIT", "LOAN_DISBURSEMENT", amount, loanId, fund.getBalance());

        System.out.println("💸 Loan Disbursed: " + amount);
    }

    // 🔥 EMI (CREDIT)
    @Override
    public void emiPayment(BigDecimal amount, Long loanId) {

        Fund fund = getFund();

        fund.setBalance(fund.getBalance().add(amount));
        fundRepository.save(fund);

        saveLog("CREDIT", "EMI", amount, loanId, fund.getBalance());

        System.out.println("💰 EMI Received: " + amount);
    }

    // 🔥 PENALTY
    @Override
    public void penalty(BigDecimal amount, Long loanId) {

        Fund fund = getFund();

        fund.setBalance(fund.getBalance().add(amount));
        fundRepository.save(fund);

        saveLog("CREDIT", "PENALTY", amount, loanId, fund.getBalance());

        System.out.println("⚠️ Penalty Added: " + amount);
    }

    // 🔥 INTEREST
    @Override
    public void interest(BigDecimal amount, Long loanId) {

        Fund fund = getFund();

        fund.setBalance(fund.getBalance().add(amount));
        fundRepository.save(fund);

        saveLog("CREDIT", "INTEREST", amount, loanId, fund.getBalance());

        System.out.println("📈 Interest Added: " + amount);
    }

    // 🔥 COMMON LOG METHOD
    private void saveLog(String type, String reason,
                         BigDecimal amount, Long loanId,
                         BigDecimal balance) {

        FundTransactionLog log = new FundTransactionLog();

        log.setType(type);
        log.setReason(reason);
        log.setAmount(amount);
        log.setReferenceId(loanId);
        log.setBalanceAfter(balance);
        log.setTimestamp(LocalDateTime.now());

        logRepository.save(log);
    }
}