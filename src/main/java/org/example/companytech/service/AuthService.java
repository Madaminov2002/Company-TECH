package org.example.companytech.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.companytech.domain.Company;
import org.example.companytech.domain.Role;
import org.example.companytech.dto.LoginDtoCompany;
import org.example.companytech.dto.SignUpDtoCompany;
import org.example.companytech.exception.PasswordIncorrectException;
import org.example.companytech.exception.UserNameAlreadyExistsException;
import org.example.companytech.exception.UserNameNotFoundException;
import org.example.companytech.jwt.JwtProvider;
import org.example.companytech.jwt.JwtResponse;
import org.example.companytech.repo.CompanyRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final CompanyRepository companyRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public Company dtoToeEntity(SignUpDtoCompany dtoCompany) {
        return Company.builder()
                .name(dtoCompany.getName())
                .userName(dtoCompany.getUsername())
                .password(passwordEncoder.encode(dtoCompany.getPassword()))
                .capital(dtoCompany.getCapital())
                .roles(List.of(Role.builder().id(1L).name("SUPER ADMIN").build()))
                .build();
    }

    public JwtResponse signupCompany(SignUpDtoCompany dtoCompany) {
        if (companyRepository.existsByUserName(dtoCompany.getUsername())) {
            throw new UserNameAlreadyExistsException(dtoCompany.getUsername());
        }

        Company savedCompany = companyRepository.save(dtoToeEntity(dtoCompany));

        String token = jwtProvider.generateForCompany(savedCompany);

        return new JwtResponse(savedCompany.getId(), token);
    }

    public JwtResponse loginCompany(LoginDtoCompany dtoCompany) {
        if (!companyRepository.existsByUserName(dtoCompany.getUsername())) {
            throw new UserNameNotFoundException(dtoCompany.getUsername());
        }
        Optional<Company> company = companyRepository.findByUserName(dtoCompany.getUsername());
        if (!passwordEncoder.matches(dtoCompany.getPassword(), company.get().getPassword())) {
            throw new PasswordIncorrectException(dtoCompany.getPassword());
        }
        String token = jwtProvider.generateForCompany(company.get());
        return new JwtResponse(company.get().getId(), token);
    }
}
