package com.estudo.eCommerce.services.Exceptions;

public class DbException extends RuntimeException {
    public DbException(String message) {
        super(message);
    }
}
