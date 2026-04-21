package com.uniquehire.TransactionAndReport.dto.Extra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyCollectionReportDto {
    private LocalDate date;
    private BigDecimal emiCollected;
    private BigDecimal fineCollected;
    private BigDecimal totalRevenue;
}
