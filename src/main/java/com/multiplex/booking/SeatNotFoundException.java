package com.multiplex.booking;

class SeatNotFoundException extends RuntimeException{
    SeatNotFoundException(Long id){
        super("Could not find seat: " + id);
    }
}
