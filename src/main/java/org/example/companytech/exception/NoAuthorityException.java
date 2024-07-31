package org.example.companytech.exception;

public class NoAuthorityException extends RuntimeException {
    public NoAuthorityException() {
        super("You do not have the authority to change password.");
    }
}
