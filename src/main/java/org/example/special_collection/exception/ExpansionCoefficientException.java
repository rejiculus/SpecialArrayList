package org.example.special_collection.exception;

/**
 * Thrown to indicate that current coefficient in out of expected range
 */
public class ExpansionCoefficientException extends RuntimeException {
    public ExpansionCoefficientException(Double coefficient) {
        super(String.format("Expansion coefficient must be more than 1.0. Your coefficient = %f", coefficient));
    }
}
