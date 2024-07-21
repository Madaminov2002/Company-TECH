package org.example.companytech.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDtoEmployee {
    private String username;
    private String password;
    private Long companyId;
    private Long contractId;
}
