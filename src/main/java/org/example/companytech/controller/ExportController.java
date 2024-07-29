package org.example.companytech.controller;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.example.companytech.domain.ExportHistory;
import org.example.companytech.dto.export.ExportDto;
import org.example.companytech.dto.export.ExportUpdateDto;
import org.example.companytech.dto.export.ReportDto;
import org.example.companytech.service.ExportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/export")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SUPER ADMIN','EXPORT')")
public class ExportController {
    private final ExportService exportService;

    @PostMapping("/save")
    public ResponseEntity<ExportHistory> save(@RequestBody ExportDto exportDto) {
        return ResponseEntity.ok(exportService.save(exportDto));
    }

    @PutMapping("/update")
    public ResponseEntity<ExportHistory> update(@RequestBody ExportUpdateDto exportDto) {
        return ResponseEntity.ok(exportService.update(exportDto));
    }

    @PostMapping("/monthly-report")
    public ResponseEntity<ExportHistory> monthlyReport(@RequestBody ReportDto reportDto) {
        return ResponseEntity.ok(exportService.showMonthlyReport(reportDto));
    }

    @PostMapping("/monthly-report-total-sum")
    public ResponseEntity<BigDecimal> monthlyReportTotalSum(@RequestBody ReportDto reportDto) {
        return ResponseEntity.ok(exportService.getTotalMonthlySum(reportDto));
    }
}
