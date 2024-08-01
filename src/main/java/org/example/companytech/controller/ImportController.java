package org.example.companytech.controller;

import org.example.companytech.domain.ImportHistory;
import org.example.companytech.dto.export.ReportDto;
import org.example.companytech.dto.req.importe.ImportHistoryReqDto;
import org.example.companytech.service.ImportHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/import")
public class ImportController {

    private final ImportHistoryService importHistoryService;

    public ImportController(ImportHistoryService importHistoryService) {
        this.importHistoryService = importHistoryService;
    }

    @PostMapping
    public void importing(@RequestBody ImportHistoryReqDto importHistoryReqDto){

        importHistoryService.importing(importHistoryReqDto);

    }

    @GetMapping("/all")
    public ResponseEntity<List<ImportHistory>> getAll(){
        List<ImportHistory> all = importHistoryService.getAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/monthly-report")
    public void getMonthlyReport(@RequestBody ReportDto reportDto){

        importHistoryService.reportMonthly(reportDto);


    }

}
