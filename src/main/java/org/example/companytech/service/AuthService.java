package org.example.companytech.service;

import io.jsonwebtoken.Claims;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.companytech.domain.Company;
import org.example.companytech.domain.Contract;
import org.example.companytech.domain.Employee;
import org.example.companytech.domain.ForgotPassword;
import org.example.companytech.domain.Role;
import org.example.companytech.domain.Verification;
import org.example.companytech.dto.ChangePasswordDto;
import org.example.companytech.dto.CompanyUpdateDto;
import org.example.companytech.dto.LoginDtoCompany;
import org.example.companytech.dto.LoginDtoEmployee;
import org.example.companytech.dto.SendMailDto;
import org.example.companytech.dto.SignUpDtoCompany;
import org.example.companytech.dto.SignUpDtoEmployee;
import org.example.companytech.exception.CompanyNotFoundException;
import org.example.companytech.exception.EmailNotFoundException;
import org.example.companytech.exception.NoAuthorityException;
import org.example.companytech.exception.PasswordIncorrectException;
import org.example.companytech.exception.UserNameNotFoundException;
import org.example.companytech.exception.UserNameOrEmailAlreadyExistsException;
import org.example.companytech.exception.UserNotEnableForChangingPasswordException;
import org.example.companytech.jwt.JwtProvider;
import org.example.companytech.jwt.JwtResponse;
import org.example.companytech.projection.CompanyDtoProjection;
import org.example.companytech.projection.ForgotPasswordProjection;
import org.example.companytech.repo.CompanyRepository;
import org.example.companytech.repo.ContractRepository;
import org.example.companytech.repo.EmployeeRepository;
import org.example.companytech.repo.ForgotPasswordRepository;
import org.example.companytech.repo.VerificationRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final CompanyRepository companyRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final ContractRepository contractRepository;
    private final EmployeeRepository employeeRepository;
    private final VerificationRepository verificationRepository;
    private final JavaMailSender mailSender;
    private final ForgotPasswordRepository forgotPasswordRepository;

    public Company dtoToeEntityCompany(SignUpDtoCompany dtoCompany) {
        return Company.builder()
                .name(dtoCompany.getName())
                .userName(dtoCompany.getUsername())
                .email(dtoCompany.getEmail())
                .password(passwordEncoder.encode(dtoCompany.getPassword()))
                .capital(dtoCompany.getCapital())
                .roles(List.of(Role.builder().id(1L).name("SUPER ADMIN").build()))
                .build();
    }

    public JwtResponse signupCompany(SignUpDtoCompany dtoCompany) {
        if (companyRepository.existsByUserName(dtoCompany.getUsername()) || companyRepository.existsByEmail(dtoCompany.getEmail())) {
            throw new UserNameOrEmailAlreadyExistsException(dtoCompany.getUsername(), dtoCompany.getEmail());
        }

        Company savedCompany = companyRepository.save(dtoToeEntityCompany(dtoCompany));

        String token = jwtProvider.generateForCompany(savedCompany);

        saveToVerification(token);

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

    public Employee dtoToeEntityEmployee(SignUpDtoEmployee dtoEmployee) {
        Optional<Company> company = companyRepository.findById(dtoEmployee.getCompanyId());
        Optional<Contract> contract = contractRepository.findById(dtoEmployee.getContractId());
        return Employee.builder()
                .username(dtoEmployee.getUsername())
                .password(passwordEncoder.encode(dtoEmployee.getPassword()))
                .roles(List.of(Role.builder().id(2L).name("EMPLOYEE").build()))
                .company(company.get())
                .contract(contract.get())
                .build();
    }

    public JwtResponse signupEmployee(SignUpDtoEmployee dtoEmployee) {
        if (employeeRepository.existsByUsername(dtoEmployee.getUsername())) {
            throw new UserNameOrEmailAlreadyExistsException(dtoEmployee.getUsername(), "");
        }

        Employee savedEmployee = employeeRepository.save(dtoToeEntityEmployee(dtoEmployee));

        String token = jwtProvider.generateForEmployee(savedEmployee);

        return new JwtResponse(savedEmployee.getId(), token);
    }

    public JwtResponse loginEmployee(LoginDtoEmployee dtoEmployee) {
        if (!employeeRepository.existsByUsername(dtoEmployee.getUsername())) {
            throw new UserNameNotFoundException(dtoEmployee.getUsername());
        }
        Optional<Employee> employee = employeeRepository.findByUsername(dtoEmployee.getUsername());
        if (!passwordEncoder.matches(dtoEmployee.getPassword(), employee.get().getPassword())) {
            throw new PasswordIncorrectException(dtoEmployee.getPassword());
        }
        String token = jwtProvider.generateForEmployee(employee.get());

        return new JwtResponse(employee.get().getId(), token);
    }

    @SneakyThrows
    public void saveToVerification(String token) {
        Claims claims = jwtProvider.parse(token);
        String password = claims.get("password", String.class);
        String email = claims.get("email", String.class);

        sendMail(password, email);
        verificationRepository.save(
                Verification.builder()
                        .email(email)
                        .password(passwordEncoder.encode(password))
                        .expiryTime(LocalTime.now().plusHours(2L))
                        .build()
        );
    }

    @Async
    public void sendMail(String password, String email) throws MessagingException {
        SendMailDto dto = SendMailDto.builder()
                .to(email)
                .content(password)
                .subject("Password").build();

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        mimeMessage.setSubject(dto.getSubject());
        mimeMessage.setSentDate(new Date());
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(dto.getTo()));
        mimeMessage.setContent("""
                        <html lang="en">
                        <head>
                            <meta charset="UTF-8">
                            <title>Title</title>
                        </head>
                        <body>
                        <h2>HELLO %s</h2>
                        <p>Your password is %s</p>
                        </body>
                        </html>
                """.formatted(dto.getTo(), dto.getContent()), "text/html; charset=utf-8");
        mailSender.send(mimeMessage);
    }

    public Company checking(String password, Long companyId) {
        Company company = companyRepository.findById(companyId).get();
        Verification verification = verificationRepository.findByEmail(company.getEmail());
        if (passwordEncoder.matches(password, verification.getPassword()) && verification.getExpiryTime().isAfter(LocalTime.now())) {
            return company;
        }
        throw new PasswordIncorrectException(password + " or password expiry time is finished ");
    }

    public Company updateCompany(CompanyUpdateDto updateDto) {

        Company company = companyRepository.findById(updateDto.getId()).orElse(null);


        if (company == null) {
            throw new CompanyNotFoundException(String.valueOf(updateDto.getId()));
        }

        if (updateDto.getUsername() != null) {
            company.setUserName(updateDto.getUsername());
        }

        if (updateDto.getPassword() != null) {
            company.setPassword(updateDto.getPassword());
        }

        if (updateDto.getCompanyName() != null) {
            company.setName(updateDto.getCompanyName());
        }

        if (updateDto.getCapital() != null) {
            company.setCapital(updateDto.getCapital());
        }

        return companyRepository.save(company);
    }

    public ForgotPassword generatePasswordAndSaveToForgotPassword(String email) throws MessagingException {
        Optional<Company> company = companyRepository.findByEmail(email);
        if (company.isEmpty()) {
            throw new CompanyNotFoundException(email);
        }
        if (!company.get().getRoles().get(0).getName().equals("SUPER ADMIN")) {
            throw new NoAuthorityException();
        }
        int password = new Random().nextInt(100000, 1000000);
        sendMail(String.valueOf(password), email);
        return forgotPasswordRepository.save(
                ForgotPassword.builder()
                        .email(email)
                        .password(passwordEncoder.encode(String.valueOf(password)))
                        .build()
        );
    }

    public ForgotPasswordProjection checkForgotPassword(String password, String email) {
        var forgotPasswordByEmailAndPassword = forgotPasswordRepository.findForgotPasswordByEmail(email);
        if (forgotPasswordByEmailAndPassword == null) {
            throw new EmailNotFoundException(email);
        }
        if (!passwordEncoder.matches(password, forgotPasswordByEmailAndPassword.getPassword())) {
            throw new PasswordIncorrectException(password);
        }
        forgotPasswordRepository.updateEnabledToTrue(email);
        return forgotPasswordByEmailAndPassword;
    }

    public CompanyDtoProjection checkUserEnabledFromForgotPasswordAndChangePassword(ChangePasswordDto dto) {
        Boolean enabled = forgotPasswordRepository.checkByEmailEnabled(dto.getEmail());
        if (enabled == null) {
            throw new UserNotEnableForChangingPasswordException(dto.getEmail());
        }
        companyRepository.changePassword(passwordEncoder.encode(dto.getNewPassword()), dto.getEmail());
        return companyRepository.getChangedPasswordUser(dto.getEmail());
    }

}
