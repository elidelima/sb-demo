package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends CustomBaseException {
    public ProductNotFoundException() {
        super(HttpStatus.NOT_FOUND, new SimpleResponse("Product Not Found"));
    }
}
