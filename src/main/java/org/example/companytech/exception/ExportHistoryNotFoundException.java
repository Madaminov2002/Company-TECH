package org.example.companytech.exception;

public class ExportHistoryNotFoundException extends RuntimeException {
    public ExportHistoryNotFoundException(Long id) {
        super("Product Not Found with "+id);
    }
}
