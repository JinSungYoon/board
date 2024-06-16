package com.project.board.exception;

public class MissingLoginDataException extends RuntimeException{
    public MissingLoginDataException(String message){
        super(message);
    }
}
