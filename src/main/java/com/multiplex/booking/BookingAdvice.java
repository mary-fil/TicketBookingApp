package com.multiplex.booking;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
class BookingAdvice {
    
    @ResponseBody
    @ExceptionHandler(ShowingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String showingNotFoundHandler(ShowingNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(SeatNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String SeatNotFoundHandler(SeatNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(RoomNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String RoomNotFound(CannotReserveException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(CannotReserveException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String CannotReserveHandler(CannotReserveException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<List<String>> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getMessage());
        }

        return ResponseEntity.badRequest().body(errors);
    }
}
