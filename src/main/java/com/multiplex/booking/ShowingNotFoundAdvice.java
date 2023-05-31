package com.multiplex.booking;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class ShowingNotFoundAdvice {
    
    @ResponseBody
    @ExceptionHandler(ShowingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String showingNotFoundHandler(ShowingNotFoundException ex) {
        return ex.getMessage();
    }
}
