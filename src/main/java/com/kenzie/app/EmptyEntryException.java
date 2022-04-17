package com.kenzie.app;

// Customer Exception to catch an empty string
public class EmptyEntryException extends RuntimeException {

    public EmptyEntryException(String message){
        super(message);
    }
}
