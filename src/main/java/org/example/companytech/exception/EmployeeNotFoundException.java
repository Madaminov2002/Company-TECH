package org.example.companytech.exception;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(String username) {
        super("Employee Not Found with username: "+username);
    }
}
