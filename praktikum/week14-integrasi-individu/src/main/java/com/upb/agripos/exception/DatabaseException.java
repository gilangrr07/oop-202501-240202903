package com.upb.agripos.exception;

/**
 * DatabaseException - Exception untuk error database
 * Integrasi: Bab 9 (Exception Handling)
 */
public class DatabaseException extends Exception {
    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}