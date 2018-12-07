package com.weston.study.tools.poi.exception;

public class ReflectionException extends RuntimeException {

    private static final long serialVersionUID = -8761716044264242725L;

    public ReflectionException() {
        super();
    }

    public ReflectionException(String message) {
        super(message);
    }

    public ReflectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflectionException(Throwable cause) {
        super(cause);
    }

}

