package com.demo.exception;

import com.demo.dto.ErrorResponse;
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

}
