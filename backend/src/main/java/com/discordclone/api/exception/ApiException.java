package com.discordclone.api.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public abstract class ApiException extends ResponseStatusException {
    private final String description;
    private final String path;
    private final HttpStatusCode httpStatusCode;

    protected ApiException(String message, String description, HttpStatusCode httpStatusCode, String path) {
        super(httpStatusCode, message);
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
