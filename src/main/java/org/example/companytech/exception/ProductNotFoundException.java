package org.example.companytech.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Product Not Found with "+id);
    }
}
