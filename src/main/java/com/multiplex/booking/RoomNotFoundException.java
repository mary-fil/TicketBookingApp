package com.multiplex.booking;

class RoomNotFoundException extends RuntimeException{
    RoomNotFoundException(Long id){
        super("Could not find room: " + id);
    }
}
