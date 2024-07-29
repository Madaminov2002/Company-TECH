package org.example.companytech.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ForgotPasswordDto {
    private String email;
    private String password;
}
