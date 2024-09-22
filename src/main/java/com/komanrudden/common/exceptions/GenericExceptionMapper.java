package com.komanrudden.common.exceptions;

import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.Response;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<BaseApplicationException> {

    @Override
    public Response toResponse(BaseApplicationException exception) {
        return Response
                .status(exception.getStatusCode())
                .entity(exception.getMessage())
                .build();
    }
}