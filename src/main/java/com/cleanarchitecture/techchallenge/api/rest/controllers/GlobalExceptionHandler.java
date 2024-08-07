package com.cleanarchitecture.techchallenge.api.rest.controllers;

import com.cleanarchitecture.techchallenge.application.exceptions.OrderCannotBeUpdatedException;
import com.cleanarchitecture.techchallenge.application.exceptions.PaymentNotGeneratedException;
import com.cleanarchitecture.techchallenge.application.exceptions.PaymentNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String ERROR = "error";

    @ExceptionHandler(value = {OrderCannotBeUpdatedException.class})
    public ResponseEntity<Object> handleOrderCannotBeUpdatedException(OrderCannotBeUpdatedException e) {
        return ResponseEntity.unprocessableEntity().body(Map.of(ERROR, e.getMessage()));
    }

    @ExceptionHandler(value = {PaymentNotGeneratedException.class})
    public ResponseEntity<Object> handlePaymentNotGeneratedException(PaymentNotGeneratedException e) {
        return ResponseEntity.unprocessableEntity().body(Map.of(ERROR, e.getMessage()));
    }

    @ExceptionHandler(value = {PaymentNotValidException.class})
    public ResponseEntity<Object> handlePaymentNotValidException(PaymentNotValidException e) {
        return ResponseEntity.unprocessableEntity().body(Map.of(ERROR, e.getMessage()));
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleUnexpectedException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}