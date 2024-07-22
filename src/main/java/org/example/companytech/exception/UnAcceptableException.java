package org.example.companytech.exception;

public class UnAcceptableException extends RuntimeException {
    public UnAcceptableException(String reason) {
        super(reason);
    }
}
