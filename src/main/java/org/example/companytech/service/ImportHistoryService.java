package org.example.companytech.service;

import org.example.companytech.domain.ImportHistory;
import org.example.companytech.dto.req.importe.ImportHistoryReqDto;
import org.example.companytech.exception.UnAcceptableException;
import org.example.companytech.mapper.ImportHistoryMapper;
import org.example.companytech.repo.ImportHistoryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class ImportHistoryService {
    private final ImportHistoryRepository importHistoryRepository;
    private final ImportHistoryMapper importHistoryMapper;

    public ImportHistoryService(ImportHistoryRepository importHistoryRepository,
                                ImportHistoryMapper importHistoryMapper) {
        this.importHistoryRepository = importHistoryRepository;
        this.importHistoryMapper = importHistoryMapper;
    }

    public void importing(ImportHistoryReqDto importHistoryReqDto) {
        if (importHistoryReqDto.getPrice().compareTo(new BigDecimal(0))<0) {
            throw new UnAcceptableException("Price must be greater than zero");
        }


//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
//        Date parsedDate;
//        try {
//            parsedDate = dateFormat.parse(importHistoryReqDto.getImportDate());
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }

        LocalDate parsedDate = LocalDate.parse(importHistoryReqDto.getImportDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm"));

        BigDecimal fullPrice = importHistoryReqDto.getPrice().multiply(new BigDecimal(importHistoryReqDto.getCount()));

        ImportHistory forSave = importHistoryMapper.toEntity(importHistoryReqDto);

        forSave.setImportDate(parsedDate);

        forSave.setFullPrice(fullPrice);

        System.out.println();

        importHistoryRepository.save(forSave);

    }

    public List<ImportHistory> getAll() {
        return importHistoryRepository.findAll();
    }
}
