package org.example.special_collection.exception;

public class NotComparableException extends RuntimeException{
    public NotComparableException() {
        super("Generic type is not comparable!");
    }
}
