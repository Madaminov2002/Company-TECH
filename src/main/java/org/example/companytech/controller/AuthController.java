package org.example.companytech.controller;

import lombok.RequiredArgsConstructor;
import org.example.companytech.dto.LoginDtoCompany;
import org.example.companytech.dto.SignUpDtoCompany;
import org.example.companytech.jwt.JwtResponse;
import org.example.companytech.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup_company")
    public ResponseEntity<JwtResponse> saveCompany(@RequestBody SignUpDtoCompany dtoCompany){
        return ResponseEntity.ok(authService.signupCompany(dtoCompany));
    }
    @PostMapping("/login_company")
    public ResponseEntity<JwtResponse> loginCompany(@RequestBody LoginDtoCompany dtoCompany){
        return ResponseEntity.ok(authService.loginCompany(dtoCompany));
    }

}
