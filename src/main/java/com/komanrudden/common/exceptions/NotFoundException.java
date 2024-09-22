package com.komanrudden.common.exceptions;

public class NotFoundException extends BaseApplicationException {

    public NotFoundException(String message) {
        super(message, 404); // HTTP 404 Not Found
    }
}
