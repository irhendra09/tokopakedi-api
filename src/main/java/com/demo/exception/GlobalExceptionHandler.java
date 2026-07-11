package com.demo.exception;

import com.demo.dto.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.NoContentException;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

public class GlobalExceptionHandler {
    @ServerExceptionMapper
    public Response handleNotFoundException(NotFoundException ex){
        return Response.status(404)
                .entity(new ErrorResponse(
                        404, ex.getMessage()))
                .build();
    }

    @ServerExceptionMapper
    public Response handleBadRequest(ConstraintViolationException ex){
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(400, ex.getMessage())).build();
    }

    @ServerExceptionMapper
    public Response handleBadRequest(BadRequestException ex){
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(400, ex.getMessage())).build();
    }

}
