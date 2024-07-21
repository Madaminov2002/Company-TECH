package org.example.companytech.exception;

public class UserNameAlreadyExistsException extends RuntimeException {
    public UserNameAlreadyExistsException(String username) {
        super("Username already exists with username: " + username);
    }
}
