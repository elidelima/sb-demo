package com.example.demo.headers;

import com.example.demo.product.model.Product;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeaderController {

    @GetMapping("/header")
    public String getRegionRequest(@RequestHeader(required = false) String region) {
        if ("USA".equals(region)) return "MEU CU";
        if ("CAN".equals(region)) return "I am too polite";
        return "Country not supported :|";
    }

    @GetMapping(value = "/header2", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Product> getProduct() {
        Product product = new Product();
        product.setName("My Product");
        product.setId(1);
        product.setDescription("Best product ever");
        return ResponseEntity.ok(product);
    }

}
