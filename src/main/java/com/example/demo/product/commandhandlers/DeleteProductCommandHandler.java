package com.example.demo.product.commandhandlers;

import com.example.demo.Command;
import com.example.demo.exceptions.ProductNotFoundException;
import com.example.demo.exceptions.SimpleResponse;
import com.example.demo.product.ProductRepository;
import com.example.demo.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteProductCommandHandler implements Command<Integer, ResponseEntity> {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ResponseEntity<ResponseEntity> execute(Integer id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            throw new ProductNotFoundException();
        }

        productRepository.delete(product.get());
        return ResponseEntity.ok().build();
    }
}
