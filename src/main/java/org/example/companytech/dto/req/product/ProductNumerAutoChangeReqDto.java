package org.example.companytech.dto.req.product;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class ProductNumerAutoChangeReqDto {
    @NotNull
    private Long productId;

    @NotNull
    private Integer numberForAutoChange;
}
