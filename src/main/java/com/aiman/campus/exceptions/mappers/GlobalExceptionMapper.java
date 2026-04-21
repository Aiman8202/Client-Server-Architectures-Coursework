// STUDENT ID: w2076700
// STUDENT NAME: AIMAN CHOWDHURY

package com.aiman.campus.exceptions.mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable ex) {

        // Log internally
        ex.printStackTrace();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"error\": \"An unexpected error occurred\"}")
                .build();
    }
}