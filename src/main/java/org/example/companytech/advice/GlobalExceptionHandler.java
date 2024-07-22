package org.example.companytech.advice;

import jakarta.servlet.http.HttpServletResponse;
import org.example.companytech.dto.ErrorResponseDto;
import org.example.companytech.exception.CompanyNotFoundException;
import org.example.companytech.exception.PasswordIncorrectException;
import org.example.companytech.exception.UserNameNotFoundException;
import org.example.companytech.exception.UserNameOrEmailAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNameOrEmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNameAlreadyExistsException(UserNameOrEmailAlreadyExistsException exception) {
        return ResponseEntity.ok(
                ErrorResponseDto.builder()
                        .message(exception.getMessage())
                        .status(HttpStatus.CONFLICT)
                        .code(HttpServletResponse.SC_CONFLICT)
                        .build()
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> userNameNotFound(UserNameNotFoundException exception) {
        return ResponseEntity.ok(
                ErrorResponseDto.builder()
                        .message(exception.getMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .code(HttpServletResponse.SC_BAD_REQUEST)
                        .build()
        );
    }

    @ExceptionHandler(PasswordIncorrectException.class)
    public ResponseEntity<ErrorResponseDto> incorrectPassword(PasswordIncorrectException exception) {
        return ResponseEntity.ok(
                ErrorResponseDto.builder()
                        .message(exception.getMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .code(HttpServletResponse.SC_BAD_REQUEST)
                        .build()
        );
    }

    @ExceptionHandler({CompanyNotFoundException.class})
    public ResponseEntity<ErrorResponseDto> userNotFound(CompanyNotFoundException exception) {
        return ResponseEntity.ok(
                ErrorResponseDto.builder()
                        .message(exception.getMessage())
                        .status(HttpStatus.NOT_FOUND)
                        .code(HttpServletResponse.SC_NOT_FOUND)
                        .build()
        );
    }

}
