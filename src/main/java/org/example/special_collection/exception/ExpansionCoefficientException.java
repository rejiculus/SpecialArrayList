package org.example.special_collection.exception;

public class ExpansionCoefficientException extends RuntimeException{
    public ExpansionCoefficientException(Double coeff, Double expected){
        super(String.format("Expansion coefficient must be more than 1.0 and NOT more than %f. Your coefficient = %f",expected,coeff));
    }
}
