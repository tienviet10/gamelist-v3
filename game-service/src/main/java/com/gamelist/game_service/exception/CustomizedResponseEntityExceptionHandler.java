package com.gamelist.game_service.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorDetails errorDetails =
                new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public final ResponseEntity<ErrorDetails> handleInternalServerErrorException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails =
                new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleResourceNotFoundException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails =
                new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public final ResponseEntity<ErrorDetails> handleInvalidTokenException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails =
                new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String errorMessage = "Total Errors:" + ex.getErrorCount();
        FieldError fieldError = ex.getFieldError();
        if (fieldError != null && fieldError.getDefaultMessage() != null) {
            errorMessage += " First Error: " + fieldError.getDefaultMessage();
        }

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), errorMessage, request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
