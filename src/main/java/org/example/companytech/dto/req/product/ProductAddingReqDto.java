package org.example.companytech.dto.req.product;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Value;
import org.example.companytech.domain.Product;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link Product}
 */
@Value
public class ProductAddingReqDto implements Serializable {

    @NotEmpty
    String name;

    @NotNull
    BigDecimal price;

    String description;

    @NotEmpty
    String place;

    @NotNull
    @PositiveOrZero
    Integer count;
}