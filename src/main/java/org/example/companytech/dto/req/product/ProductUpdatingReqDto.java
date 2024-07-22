package org.example.companytech.dto.req.product;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link org.example.companytech.domain.Product}
 */
@Value
public class ProductUpdatingReqDto implements Serializable {
    @NotNull
    Long id;
    @NotEmpty
    String name;
    BigDecimal price;
    @NotEmpty
    String description;
    @PositiveOrZero
    Integer count;
    @NotEmpty
    String place;
}