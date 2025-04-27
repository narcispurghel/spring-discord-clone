package com.discordclone.api.exception.impl;

import com.discordclone.api.exception.ApiException;
import org.springframework.http.HttpStatusCode;

public class EmailAlreadyUsedException extends ApiException {
    public EmailAlreadyUsedException(
            String message, String description, HttpStatusCode httpStatusCode, String path) {
        super(message, description, httpStatusCode, path);
    }
}
