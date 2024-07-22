package org.example.companytech.advice;

import jakarta.servlet.http.HttpServletResponse;
import org.example.companytech.dto.ErrorResponseDto;
import org.example.companytech.exception.PasswordIncorrectException;
import org.example.companytech.exception.UnAcceptableException;
import org.example.companytech.exception.UserNameAlreadyExistsException;
import org.example.companytech.exception.UserNameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNameAlreadyExistsException(UserNameAlreadyExistsException exception) {
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

    @ExceptionHandler(UnAcceptableException.class)
    public ResponseEntity<ErrorResponseDto> unAcceptableHandling(UnAcceptableException exception) {
        return ResponseEntity.ok(
                ErrorResponseDto.builder()
                        .message(exception.getMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .code(HttpServletResponse.SC_BAD_REQUEST)
                        .build()
        );
    }

}
