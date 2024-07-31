package org.example.companytech.dto.req.importe;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Value;
import org.example.companytech.domain.ImportHistory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link ImportHistory}
 */
@Value
public class ImportHistoryReqDto implements Serializable {
    @NotEmpty
    String productName;

    @NotNull
    BigDecimal price;

    @NotNull
    @Positive
    Integer count;

    @NotEmpty
    String importDate;
}