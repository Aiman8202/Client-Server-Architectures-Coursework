package com.aiman.campus.filters;

import java.io.IOException;
import java.util.logging.Logger;

import javax.ws.rs.container.*;
import javax.ws.rs.ext.Provider;

@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final Logger LOGGER = Logger.getLogger(LoggingFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        LOGGER.info("Incoming Request: " +
                requestContext.getMethod() + " " +
                requestContext.getUriInfo().getRequestUri());
    }

    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {

        LOGGER.info("Outgoing Response: Status " +
                responseContext.getStatus());
    }
}