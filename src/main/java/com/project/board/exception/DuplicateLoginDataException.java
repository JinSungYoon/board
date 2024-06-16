package com.project.board.exception;

public class DuplicateLoginDataException extends RuntimeException{

    public DuplicateLoginDataException(String message){
        super(message);
    }

}
