package org.example.companytech.dto.export;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExportUpdateDto {
    private Long id;
    private Integer count;
    private LocalDate date;
}
