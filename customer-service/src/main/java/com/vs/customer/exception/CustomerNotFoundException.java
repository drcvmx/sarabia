package com.vs.customer.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String id) {
        super("No se encontró el cliente con ID: " + id);
    }
}
