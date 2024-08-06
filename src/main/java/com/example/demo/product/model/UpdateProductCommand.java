package com.example.demo.product.model;

import lombok.Data;

@Data
public class UpdateProductCommand {
    private int id;
    private Product product;

    public UpdateProductCommand(int id, Product product) {
        this.product = product;
        this.id = id;
    }
}
