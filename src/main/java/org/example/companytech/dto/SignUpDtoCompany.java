package org.example.companytech.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDtoCompany {
    private String name;
    private String username;
    private String password;
    private BigDecimal capital;
}
