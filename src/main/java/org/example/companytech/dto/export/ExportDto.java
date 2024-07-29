package org.example.companytech.dto.export;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExportDto {
    private Integer count;
    private LocalDate date;
    private Long productId;
    private String companyUserName;
}
