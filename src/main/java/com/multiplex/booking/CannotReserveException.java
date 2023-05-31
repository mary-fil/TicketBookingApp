package com.multiplex.booking;

class CannotReserveException extends RuntimeException{
    CannotReserveException(){
        super("Could not reserve seat(s)");
    }
}
