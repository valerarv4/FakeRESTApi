package net.azurewebsites.fakerestapi.exceptions;

public class TAFRuntimeException extends RuntimeException {

    public TAFRuntimeException(String message) {
        super(message);
    }

    public TAFRuntimeException(String message, Throwable exception) {
        super(message, exception);
    }
}
