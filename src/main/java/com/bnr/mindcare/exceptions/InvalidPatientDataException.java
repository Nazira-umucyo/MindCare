package com.bnr.mindcare.exceptions;

public class InvalidPatientDataException extends RuntimeException {

    public InvalidPatientDataException(String message) {
        super(message);
    }
}