package com.project.board.exception;

public class UserValidationException extends RuntimeException{
    public UserValidationException(String message){
        super(message);
    }
}
