package com.discordclone.api.exception;

public class MissingOrMalformedJwtException extends RuntimeException {

    public MissingOrMalformedJwtException(String message) {
        super(message);
    }

    public MissingOrMalformedJwtException(String message, Throwable cause) {
        super(message, cause);
    }

}