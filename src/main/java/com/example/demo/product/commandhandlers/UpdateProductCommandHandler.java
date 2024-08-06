package com.example.demo.product.commandhandlers;

import com.example.demo.Command;
import com.example.demo.exceptions.ProductNotFoundException;
import com.example.demo.exceptions.SimpleResponse;
import com.example.demo.product.ProductRepository;
import com.example.demo.product.model.Product;
import com.example.demo.product.model.ProductDTO;
import com.example.demo.product.model.UpdateProductCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateProductCommandHandler implements Command<UpdateProductCommand, ResponseEntity> {

    @Autowired
    private ProductRepository productRepository;

    @Override
    @CachePut(value = "productCache", key="#updateProductCommand.getId()")
    public ResponseEntity execute(UpdateProductCommand updateProductCommand) {

        Optional<Product> optionalProduct = productRepository.findById(updateProductCommand.getId());
        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException();        }

        Product product = updateProductCommand.getProduct();
        product.setId(updateProductCommand.getId());

        productRepository.save(product);
        return ResponseEntity.ok(new ProductDTO(product));
    }
}
