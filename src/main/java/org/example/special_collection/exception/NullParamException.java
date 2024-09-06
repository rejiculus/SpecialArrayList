package org.example.special_collection.exception;

/**
 * Thrown to indicate that a parameter is null.
 */
public class NullParamException extends RuntimeException {
    public NullParamException() {
        super("Param can't be null!");
    }
}
