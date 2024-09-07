package org.example.special_collection.exception;

/**
 * Thrown to indicate that collection contains null element and it cant be sorted.
 */
public class SortNullElementException extends RuntimeException {
    public SortNullElementException() {
        super("Array has null element. You can't sort null elements!");

    }
}
