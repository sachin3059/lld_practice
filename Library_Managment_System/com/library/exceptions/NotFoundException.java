package com.library.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String entity, String id) {
        super(entity + " not found: " + id);
    }
}



