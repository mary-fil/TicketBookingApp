package com.multiplex.booking;

class ShowingNotFoundException extends RuntimeException{
    ShowingNotFoundException(Long id){
        super("Could not find showing: " + id + "\n");
    }

    ShowingNotFoundException(String movieTitle){
        super("Could not find showing: " + movieTitle + "\n");
    }
}
