package com.uniquehire.paymentservice.service;
import java.math.BigDecimal;
import java.time.LocalDate;

public interface LoanIntegrationService {

    LoanDetails getLoanDetails(Long loanId);

    void updateLoanAfterPayment(Long loanId, BigDecimal paidAmount, BigDecimal fineAmount, LocalDate paymentDate);

    class LoanDetails {
        private Long loanId;
        private String loanStatus;
        private BigDecimal dailyEmi;
        private LocalDate startDate;
        private LocalDate endDate;
        private BigDecimal amountPaid;
        private BigDecimal totalFine;

        public LoanDetails() {
        }

        public Long getLoanId() {
            return loanId;
        }

        public void setLoanId(Long loanId) {
            this.loanId = loanId;
        }

        public String getLoanStatus() {
            return loanStatus;
        }

        public void setLoanStatus(String loanStatus) {
            this.loanStatus = loanStatus;
        }

        public BigDecimal getDailyEmi() {
            return dailyEmi;
        }

        public void setDailyEmi(BigDecimal dailyEmi) {
            this.dailyEmi = dailyEmi;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }

        public BigDecimal getAmountPaid() {
            return amountPaid;
        }

        public void setAmountPaid(BigDecimal amountPaid) {
            this.amountPaid = amountPaid;
        }

        public BigDecimal getTotalFine() {
            return totalFine;
        }

        public void setTotalFine(BigDecimal totalFine) {
            this.totalFine = totalFine;
        }
    }
}