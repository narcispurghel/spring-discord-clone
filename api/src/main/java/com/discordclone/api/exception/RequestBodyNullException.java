package com.discordclone.api.exception;

public class RequestBodyNullException extends RuntimeException {
    public RequestBodyNullException() {
        super("Request body cannot be null");
    }
}
