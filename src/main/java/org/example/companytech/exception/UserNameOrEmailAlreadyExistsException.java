package org.example.companytech.exception;

public class UserNameOrEmailAlreadyExistsException extends RuntimeException {
    public UserNameOrEmailAlreadyExistsException(String username, String email) {
        super("Username or email already exists with username: " + username+ " and email: " + email);
    }
}
