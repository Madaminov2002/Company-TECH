package org.example.companytech.dto.auth;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyUpdateDto {
    private Long id;
    private String companyName;
    private String username;
    private String password;
    private BigDecimal capital;
}
