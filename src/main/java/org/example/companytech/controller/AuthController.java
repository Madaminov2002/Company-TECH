package org.example.companytech.controller;

import lombok.RequiredArgsConstructor;
import org.example.companytech.domain.Company;
import org.example.companytech.dto.CheckDto;
import org.example.companytech.dto.CompanyUpdateDto;
import org.example.companytech.dto.LoginDtoCompany;
import org.example.companytech.dto.LoginDtoEmployee;
import org.example.companytech.dto.SignUpDtoCompany;
import org.example.companytech.dto.SignUpDtoEmployee;
import org.example.companytech.jwt.JwtResponse;
import org.example.companytech.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup_company")
    public ResponseEntity<JwtResponse> saveCompany(@RequestBody SignUpDtoCompany dtoCompany) {
        return ResponseEntity.ok(authService.signupCompany(dtoCompany));
    }

    @PostMapping("/login_company")
    public ResponseEntity<JwtResponse> loginCompany(@RequestBody LoginDtoCompany dtoCompany) {
        return ResponseEntity.ok(authService.loginCompany(dtoCompany));
    }

    @PostMapping("/signup_employee")
    public ResponseEntity<JwtResponse> saveEmployee(@RequestBody SignUpDtoEmployee dtoEmployee) {
        return ResponseEntity.ok(authService.signupEmployee(dtoEmployee));
    }

    @PostMapping("/login_employee")
    public ResponseEntity<JwtResponse> loginEmployee(@RequestBody LoginDtoEmployee dtoEmployee) {
        return ResponseEntity.ok(authService.loginEmployee(dtoEmployee));
    }

    @PostMapping("/check-company")
    public ResponseEntity<Company> checkingCompany(@RequestBody CheckDto dto) {
        return ResponseEntity.ok(authService.checking(dto.getPassword(), dto.getId()));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('SUPER ADMIN')")
    public ResponseEntity<Company> updateUser(@RequestBody CompanyUpdateDto updateDto) {
        return ResponseEntity.ok(authService.updateCompany(updateDto));
    }

}
