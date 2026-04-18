package com.uniquehire.TransactionAndReport.controller;

import com.uniquehire.TransactionAndReport.dto.ReportSummaryDto;
import com.uniquehire.TransactionAndReport.service.ReportService;
import com.uniquehire.TransactionAndReport.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/summary")
    public ResponseEntity<?> getSummaryReport(
            @RequestParam(required = false) LocalDate fromDate,
            @RequestParam(required = false) LocalDate toDate) {

        ReportSummaryDto response = reportService.getSummaryReport(fromDate, toDate);
        return ResponseUtil.success(response, "Summary report generated");
    }

    @GetMapping("/loans")
    public ResponseEntity<?> getLoanReport() {
        Object response = reportService.getLoanReport();
        return ResponseUtil.success(response, "Loan report fetched successfully");
    }

    @GetMapping("/collections")
    public ResponseEntity<?> getCollectionsReport() {
        Object response = reportService.getCollectionReport();
        return ResponseUtil.success(response, "Collections report fetched successfully");
    }
}