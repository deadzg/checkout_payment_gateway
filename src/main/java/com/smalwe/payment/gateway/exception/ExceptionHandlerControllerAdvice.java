package com.smalwe.payment.gateway.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.smalwe.payment.gateway.bean.ApiError;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({PersistenceException.class})
    public ResponseEntity<Object> handlePersistenceException(PersistenceException ex, WebRequest request) {
        ApiError errors = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<Object>(errors, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ApiError> handlePersistenceException(ResourceNotFoundException ex, WebRequest request) {
        ApiError errors = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<ApiError>(errors, HttpStatus.NOT_FOUND);
    }

    protected ResponseEntity<Object> handleHttpMessageNotReadable (HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError errors = new ApiError(HttpStatus.BAD_REQUEST, "Invalid Json via controller advice");
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidPaymentInfoException.class})
    protected ResponseEntity<ApiError> handleInvalidPaymentInfo (InvalidPaymentInfoException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError errors = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<ApiError>(errors, HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.info(ex.getMessage());

        List<String> errList = new ArrayList<>();
        ApiError errors = new ApiError(HttpStatus.BAD_REQUEST, "Invalid request payload object", errList);
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            logger.info("Message:" + message);
            errList.add(message);
        });
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UnAuthorizedException.class})
    public ResponseEntity<Object> badRequestHandler(Exception ex, WebRequest request) {
        ApiError errors = new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return new ResponseEntity<Object>(errors, HttpStatus.UNAUTHORIZED);
    }
}