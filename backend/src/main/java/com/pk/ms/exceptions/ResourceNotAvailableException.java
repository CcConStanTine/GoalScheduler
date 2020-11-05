package com.pk.ms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceNotAvailableException extends RuntimeException {
    public ResourceNotAvailableException() {
        super("Resource not available. ");
    }
}
