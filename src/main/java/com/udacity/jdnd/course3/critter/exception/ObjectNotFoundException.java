package com.udacity.jdnd.course3.critter.exception;

public class ObjectNotFoundException extends RuntimeException{
    public ObjectNotFoundException() {
        super();
    }
    public ObjectNotFoundException(String message) {
        super(message);
    }
}
