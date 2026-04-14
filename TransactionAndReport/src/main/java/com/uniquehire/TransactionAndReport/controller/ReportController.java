package com.uniquehire.TransactionAndReport.controller;

import com.uniquehire.TransactionAndReport.dto.ReportSummaryDto;
import com.uniquehire.TransactionAndReport.service.ReportService;
import com.uniquehire.TransactionAndReport.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/summary")
    public ResponseEntity<?> getSummaryReport(){
        ReportSummaryDto response = reportService.getSummaryReport();

        return ResponseUtil.success(response, "Summary report fetched successfully");
    }

    @GetMapping("/loans")
    public ResponseEntity<?> getLoanReport(){
        Object response = reportService.getLoanReport();
        return ResponseUtil.success(response, "Loan report fetched successfully");
    }
    @GetMapping("/collections")
    public ResponseEntity<?> getCollectionsReport(){
        Object response = reportService.getCollectionReport();
        return ResponseUtil.success(response, "Collections report fetched successfully");
    }
}
