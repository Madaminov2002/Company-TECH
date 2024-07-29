package org.example.companytech.service;

import java.math.BigDecimal;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.companytech.domain.Company;
import org.example.companytech.domain.ExportHistory;
import org.example.companytech.domain.Product;
import org.example.companytech.dto.export.ExportDto;
import org.example.companytech.dto.export.ExportUpdateDto;
import org.example.companytech.dto.export.ReportDto;
import org.example.companytech.exception.CompanyNotFoundException;
import org.example.companytech.exception.ExportHistoryNotFoundException;
import org.example.companytech.exception.ProductNotFoundException;
import org.example.companytech.repo.CompanyRepository;
import org.example.companytech.repo.ExportHistoryRepository;
import org.example.companytech.repo.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExportService {
    private final ProductRepository productRepository;
    private final ExportHistoryRepository exportHistoryRepository;
    private final CompanyRepository companyRepository;

    public ExportHistory dtoToEntity(ExportDto exportDto) {
        Optional<Product> product = productRepository.findById(exportDto.getProductId());
        if (product.isEmpty()) {
            throw new ProductNotFoundException(exportDto.getProductId());
        }
        return ExportHistory.builder()
                .count(exportDto.getCount())
                .date(exportDto.getDate())
                .product(product.get())
                .build();
    }

    public ExportHistory save(ExportDto exportDto) {
        Optional<Product> product = productRepository.findById(exportDto.getProductId());

        if (product.isEmpty()) {
            throw new ProductNotFoundException(exportDto.getProductId());
        }

        Optional<Company> company = companyRepository.findByUserName(exportDto.getCompanyUserName());

        if (company.isEmpty()) {
            throw new CompanyNotFoundException(exportDto.getCompanyUserName());
        }

        BigDecimal multiply = product.get().getPrice().multiply(new BigDecimal(exportDto.getCount()));
        BigDecimal capital = company.get().getCapital().add(multiply);
        companyRepository.updateCapital(capital);
        productRepository.updateById(product.get().getId(), exportDto.getCount());

        return exportHistoryRepository.save(dtoToEntity(exportDto));
    }

    public ExportHistory update(ExportUpdateDto updateDto) {
        Optional<ExportHistory> exportHistory = exportHistoryRepository.findById(updateDto.getId());
        if (exportHistory.isEmpty()) {
            throw new ExportHistoryNotFoundException(updateDto.getId());
        }
        if (updateDto.getCount() != null) {
            exportHistory.get().setCount(updateDto.getCount());
        }
        if (updateDto.getDate() != null) {
            exportHistory.get().setDate(updateDto.getDate());
        }
        return exportHistoryRepository.save(exportHistory.get());
    }

    public ExportHistory showMonthlyReport(ReportDto reportDto) {
        var exportHistoryByYearAndMonth = exportHistoryRepository.findExportHistoryByYearAndMonth(reportDto.getYear(), reportDto.getMonth());
        if (exportHistoryByYearAndMonth.isEmpty()) {
            throw new ExportHistoryNotFoundException(0L);
        }
        return exportHistoryByYearAndMonth.get();
    }

    public BigDecimal getTotalMonthlySum(ReportDto reportDto) {
        return exportHistoryRepository.getTotalSum(reportDto.getYear(), reportDto.getMonth());
    }

}
