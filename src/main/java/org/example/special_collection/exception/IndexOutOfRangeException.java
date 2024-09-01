package org.example.special_collection.exception;

public class IndexOutOfRangeException extends RuntimeException{
    public IndexOutOfRangeException(int capacity, int index) {
        super(String.format("Index out of rang! It must be in range from 0 to %d (include). Your index = %d",capacity-1,index));
    }
}
