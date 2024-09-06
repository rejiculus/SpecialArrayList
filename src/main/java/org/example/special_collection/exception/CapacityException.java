package org.example.special_collection.exception;

/**
 * Thrown to indicate that capacity is not in expected range
 */
public class CapacityException extends RuntimeException {
    /**
     * Indicate that current capacity is less than zero.
     *
     * @param capacity current capacity.
     */
    public CapacityException(int capacity) {
        super(String.format("Capacity must be more than zero! Your capacity = %d", capacity));
    }

    /**
     * Indicate that current capacity is less than expected.
     *
     * @param capacity current capacity.
     * @param expected expected capacity.
     */
    public CapacityException(int capacity, int expected) {
        super(String.format("New capacity less than actually capacity! Your capacity = %d, but expected more than %d", capacity, expected));
    }
}
