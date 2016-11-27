package org.nicktorwald.todo.config;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;
import javax.validation.Path;

import javax.validation.ValidationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionMapper 
        implements ExceptionMapper<ValidationException> {

    @Override
    public Response toResponse(ValidationException exception) {
        final ConstraintViolationException cve = (ConstraintViolationException) exception;
       
        return Response.status(getResponseStatus(cve))
                .type(MediaType.APPLICATION_JSON)
                .entity(getErrorMessages(cve))
                .build();
    }
    
    public int getResponseStatus(final ConstraintViolationException violation) {
        final Iterator<ConstraintViolation<?>> iterator 
                = violation.getConstraintViolations().iterator();
        
        if (iterator.hasNext()) {
            for (final Path.Node node : iterator.next().getPropertyPath()) {
                final ElementKind kind = node.getKind();

                if (ElementKind.RETURN_VALUE.equals(kind)) {
                    return Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
                }
            }
        }

        // return '422 UNPROCESSABLE ENTTY' for any structured violations
        return 422;
    }
    
    public List<ErrorMessage> getErrorMessages(final ConstraintViolationException violation) {
        return violation.getConstraintViolations().stream()
                .map(v -> new ErrorMessage(v.getMessage()))
                .collect(Collectors.toList());
    } 
}
