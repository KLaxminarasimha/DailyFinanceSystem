package com.uniquehire.TransactionAndReport.controller;

import com.uniquehire.TransactionAndReport.dto.*;
import com.uniquehire.TransactionAndReport.dto.Extra.*;
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
        return ResponseUtil.success(response, "Summary report fetched successfully");
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

    @GetMapping("/loan/{loanId}")
    public ResponseEntity<?> getLoanDetailReport(@PathVariable Long loanId) {
        LoanDetailReportDto response = reportService.getLoanDetailReport(loanId);
        return ResponseUtil.success(response, "Loan detail report fetched successfully");
    }

    @GetMapping("/loan/{loanId}/missed")
    public ResponseEntity<?> getMissedPaymentAlert(@PathVariable Long loanId) {
        MissedPaymentAlertDto response = reportService.getMissedPaymentAlert(loanId);
        return ResponseUtil.success(response, "Missed payment alert fetched successfully");
    }

    @GetMapping("/loan/{loanId}/warning")
    public ResponseEntity<?> getDefaultWarning(@PathVariable Long loanId) {
        DefaultWarningDto response = reportService.getDefaultWarningReport(loanId);
        return ResponseUtil.success(response, "Default warning report fetched successfully");
    }

    @GetMapping("/loan/{loanId}/default")
    public ResponseEntity<?> getLoanDefaultReport(@PathVariable Long loanId) {
        DefaultWarningDto response = reportService.getLoanDefaultReport(loanId);
        return ResponseUtil.success(response, "Loan default report fetched successfully");
    }

    @GetMapping("/loan/{loanId}/completed")
    public ResponseEntity<?> getLoanCompletedReport(@PathVariable Long loanId) {
        LoanCompletedReportDto response = reportService.getLoanCompletedReport(loanId);
        return ResponseUtil.success(response, "Loan completed report fetched successfully");
    }

    @GetMapping("/admin-dashboard")
    public ResponseEntity<?> getAdminDashboardReport() {
        AdminDashboardDto response = reportService.getAdminDashboardReport();
        return ResponseUtil.success(response, "Admin dashboard fetched successfully");
    }

    @GetMapping("/daily-collection")
    public ResponseEntity<?> getDailyCollectionReport(@RequestParam LocalDate date) {
        DailyCollectionReportDto response = reportService.getDailyCollectionReport(date);
        return ResponseUtil.success(response, "Daily collection report fetched successfully");
    }
}