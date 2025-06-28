package com.rukayun.bff.exceptions;

import org.springframework.http.HttpStatusCode;

public class PassthroughException extends RuntimeException {
    private final HttpStatusCode status;
    private final String body;

    public PassthroughException(HttpStatusCode status, String body) {
        this.status = status;
        this.body = body;
    }

    public HttpStatusCode getStatus() {
        return status;
    }

    public String getBody() {
        return body;
    }
}