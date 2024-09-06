package org.example.special_collection.exception;

/**
 * Thrown to indicate that the index is outside the array
 */
public class IndexOutOfRangeException extends RuntimeException {

    /**
     * indicate that the index is outside a specific range
     *
     * @param capacity max capacity of array.
     * @param index    current index.
     */
    public IndexOutOfRangeException(int capacity, int index) {
        super(String.format("Index out of rang! It must be in range from 0 to %d (include). Your index = %d", capacity - 1, index));
    }

    /**
     * Indicate that the index is less than zero
     *
     * @param index current index.
     */
    public IndexOutOfRangeException(int index) {
        super(String.format("Index out of rang! It must be more than zero! Your index = %d", index));
    }
}
