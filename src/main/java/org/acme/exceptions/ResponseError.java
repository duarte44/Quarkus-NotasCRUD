package org.acme.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.ConstraintViolation;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor

public class ResponseError {

    public static final int UNPROCESSABLE_ENTITY_STATUS = 422;
    private String message;
    private Collection<FieldErrors> errors;

    public ResponseError(String message, Collection<FieldErrors> errors) {
        this.message = message;
        this.errors = errors;
    }

    public static <T> ResponseError createFromValidation(Set<ConstraintViolation<T>> violations){

        Collection<FieldErrors> errors = violations.stream()
                .map(cv -> (new FieldErrors(cv.getPropertyPath().toString(), cv.getMessage())))
                .collect(Collectors.toList());

        String message = "Validation Error";

        var responseError = new ResponseError(message,errors);

        return responseError;
    }

    public Response withStatusCode(int code){
        return  Response.status(code).entity(this).build();
    }

}
