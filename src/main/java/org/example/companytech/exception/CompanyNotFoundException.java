package org.example.companytech.exception;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(String param) {
        super("Company not found by [" + param + "]");
    }
}
