package com.levitacode.apiSPA.exceptions;

public class TurnoNotFoundException extends RuntimeException {
    public TurnoNotFoundException(String message) {
        super(message);
    }
}