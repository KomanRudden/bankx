package com.komanrudden.common.exceptions;

import lombok.Getter;

@Getter
public class BaseApplicationException extends RuntimeException {

    private final int statusCode;

    public BaseApplicationException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

}
