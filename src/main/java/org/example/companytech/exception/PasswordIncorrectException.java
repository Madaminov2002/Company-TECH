package org.example.companytech.exception;

public class PasswordIncorrectException extends RuntimeException {
    public PasswordIncorrectException(String password) {
        super("You have entered an incorrect password. Please try again. [" + password + "]");
    }
}
