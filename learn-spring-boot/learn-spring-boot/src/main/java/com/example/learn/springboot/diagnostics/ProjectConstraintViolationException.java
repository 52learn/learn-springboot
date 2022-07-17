package com.example.learn.springboot.diagnostics;

public class ProjectConstraintViolationException extends RuntimeException {
    public ProjectConstraintViolationException() {
    }

    public ProjectConstraintViolationException(String message) {
        super(message);
    }

    public ProjectConstraintViolationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProjectConstraintViolationException(Throwable cause) {
        super(cause);
    }

    public ProjectConstraintViolationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
