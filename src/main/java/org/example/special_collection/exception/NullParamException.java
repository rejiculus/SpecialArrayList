package org.example.special_collection.exception;

public class NullParamException extends RuntimeException{
    public NullParamException() {
        super("Param can't be null. jerk!"); //todo change message
    }
}
