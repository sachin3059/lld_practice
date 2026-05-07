package com.library.exceptions;

public class BookNotAvailableException extends RuntimeException {
    public BookNotAvailableException(String barcode) {
        super("BookItem not available for checkout: " + barcode);
    }
}


