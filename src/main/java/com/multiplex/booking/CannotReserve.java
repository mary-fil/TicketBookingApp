package com.multiplex.booking;

class CannotReserve extends RuntimeException{
    CannotReserve(){
        super("Could not reserve seat(s)");
    }
}
