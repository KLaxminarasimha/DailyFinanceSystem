package com.uniquehire.TransactionAndReport.service;

import com.uniquehire.TransactionAndReport.dto.CollectionReportItemDto;
import com.uniquehire.TransactionAndReport.dto.LoanReportItemDto;
import com.uniquehire.TransactionAndReport.dto.ReportSummaryDto;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    ReportSummaryDto getSummaryReport(LocalDate fromDate, LocalDate toDate);
    List<LoanReportItemDto> getLoanReport();
    List<CollectionReportItemDto> getCollectionReport();
}
