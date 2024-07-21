package org.example.companytech.exception;

public class UserNameNotFoundException extends RuntimeException {
    public UserNameNotFoundException(String username) {
        super("Username not found: " + username);
    }
}
