package org.example.special_collection.exception;

public class CapacityException extends RuntimeException{
    public CapacityException(int capacity) {
        super(String.format("Capacity must be more than zero! Your capacity = %d", capacity));
    }
    public CapacityException(int capacity,int expected) {
        super(String.format("New capacity less than actually capacity! Your capacity = %d, but expected more than %d", capacity, expected));
    }
}
