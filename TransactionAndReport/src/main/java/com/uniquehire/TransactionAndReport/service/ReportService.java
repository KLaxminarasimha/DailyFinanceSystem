package com.uniquehire.TransactionAndReport.service;

import com.uniquehire.TransactionAndReport.dto.ReportSummaryDto;

public interface ReportService {
    ReportSummaryDto getSummaryReport();
    Object getLoanReport();
    Object getCollectionReport();
}
