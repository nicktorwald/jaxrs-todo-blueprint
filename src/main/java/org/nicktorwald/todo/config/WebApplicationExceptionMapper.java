package org.nicktorwald.todo.config;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WebApplicationExceptionMapper 
        implements ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(WebApplicationException exception) {
        return Response
                .status(exception.getResponse().getStatus())
                .type(MediaType.APPLICATION_JSON)
                .entity(new ErrorMessage(exception.getMessage()))
                .build();
    }   
    
}
