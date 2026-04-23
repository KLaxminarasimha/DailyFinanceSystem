package com.uniquehire.TransactionAndReport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportSummaryDto {
    private DateRangeDto dateRange;
    private SummarySectionDto summary;
    private CollectionsSectionDto collections;
    private FinesSectionDto fines;
    private AgentsSectionDto agents;
    private LocalDateTime generatedAt;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DateRangeDto {
        private LocalDate from;
        private LocalDate to;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SummarySectionDto {
        private Integer totalLoans;
        private Integer activeLoans;
        private Integer closedLoans;
        private Integer defaultedLoans;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CollectionsSectionDto {
        private java.math.BigDecimal totalCollected;
        private java.math.BigDecimal pendingAmount;
        private java.math.BigDecimal overdueAmount;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FinesSectionDto {
        private java.math.BigDecimal totalFinesDue;
        private java.math.BigDecimal totalFinesPaid;
        private java.math.BigDecimal totalFinesWaived;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AgentsSectionDto {
        private Integer totalAgents;
        private TopPerformerDto topPerformer;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TopPerformerDto {
        private Long agentId;
        private String agentName;
        private java.math.BigDecimal collectionsAmount;
    }
}