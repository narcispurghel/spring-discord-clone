package com.discordclone.api.exception;

import org.springframework.http.HttpStatusCode;

public abstract class ApiException extends RuntimeException {
    private final String description;
    private final String path;
    private final HttpStatusCode httpStatusCode;

    protected ApiException(String message, String description, HttpStatusCode httpStatusCode, String path) {
        super(message);
        if (path == null) {
            path = "";
        }
        this.httpStatusCode = httpStatusCode;
        this.description = description;
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public String getPath() {
        return path;
    }

    public HttpStatusCode getHttpStatusCode() {
        return httpStatusCode;
    }
}
