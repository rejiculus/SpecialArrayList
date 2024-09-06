package org.example.special_collection.exception;

/**
 * Thrown to indicate that current generic type is not comparable.
 */
public class NotComparableException extends RuntimeException {
    public NotComparableException() {
        super("Generic type is not comparable!");
    }
}
