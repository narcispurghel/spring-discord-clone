package com.discordclone.api.exception;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.lang.reflect.InvocationTargetException;

@Aspect
@Component
public class ServiceExp {
    @ExceptionHandler(InvocationTargetException.class)
    public void handleServiceException(InvocationTargetException ex) {
        Throwable cause = ex.getCause();
        if (cause != null) {
            System.err.println("Service layer InvocationTargetException: " + cause);
            cause.printStackTrace();
            //Handle the error.
        }
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public void handleServisssceException(UnsupportedOperationException ex) {
        Throwable cause = ex.getCause();
        if (cause != null) {
            System.err.println("Service layer InvocationTargetException: " + cause);
            cause.printStackTrace();
            //Handle the error.
        }
    }
}
