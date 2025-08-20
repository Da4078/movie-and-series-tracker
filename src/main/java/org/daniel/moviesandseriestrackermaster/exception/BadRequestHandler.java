package org.daniel.moviesandseriestrackermaster.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class BadRequestHandler {

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> handleNotFoundException(BadRequestException badRequestException){
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorResponse error = new ErrorResponse(
                badRequestException.getMessage(),
                badRequestException,
                status,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(error, status);
    }
}
