package com.cognizant.challenge.rest.microservices.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

//This is the annotation when you put when you have Exception Handlers it is like a Controller.
@ControllerAdvice
public class GenericExceptionHandler  {

    @ExceptionHandler(value = {GenericRequestException.class})
    public ResponseEntity<Object> handleGendericException(GenericRequestException e)
    {
        GenericException exception = new GenericException(e.getMessage(),
                e.getStatus(),
                ZonedDateTime.now(ZoneId.of("Z"))
                );
        return new ResponseEntity<>(exception,e.getStatus());
    }
}
