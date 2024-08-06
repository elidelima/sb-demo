package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;

public class ExternalCatsFactsDownException extends CustomBaseException {
    public ExternalCatsFactsDownException(HttpStatus status, SimpleResponse simpleResponse) {
        super(status, simpleResponse);
    }
}
