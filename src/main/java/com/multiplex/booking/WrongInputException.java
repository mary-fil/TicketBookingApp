package com.multiplex.booking;

class WrongInputException extends RuntimeException{
    WrongInputException(){
        super("Wrong input\n");
    }
}
