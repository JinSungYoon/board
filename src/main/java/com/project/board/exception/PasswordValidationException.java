package com.project.board.exception;

public class PasswordValidationException extends RuntimeException{
    public PasswordValidationException(String message){
        super(message);
    }
}
