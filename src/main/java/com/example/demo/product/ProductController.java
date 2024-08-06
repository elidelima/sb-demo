package com.example.demo.product;

import com.example.demo.exceptions.ProductNotFoundException;
import com.example.demo.product.commandhandlers.CreateProductCommandHandler;
import com.example.demo.product.commandhandlers.DeleteProductCommandHandler;
import com.example.demo.product.commandhandlers.UpdateProductCommandHandler;
import com.example.demo.product.model.Product;
import com.example.demo.product.model.ProductDTO;
import com.example.demo.product.model.UpdateProductCommand;
import com.example.demo.product.queryhandlers.GetAllProductsQueryHandler;
import com.example.demo.product.queryhandlers.GetProductQueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired private ProductRepository productRepository;
    @Autowired private GetAllProductsQueryHandler getAllProductsQueryHandler;
    @Autowired private GetProductQueryHandler getProductQueryHandler;
    @Autowired private CreateProductCommandHandler createProductCommandHandler;
    @Autowired private UpdateProductCommandHandler updateProductCommandHandler;
    @Autowired private DeleteProductCommandHandler deleteProductCommandHandler;

    @GetMapping("/search-price")
    public ResponseEntity<List<Product>> findProductsByPrice(
            @RequestParam(value = "maxPrice") Double maxPrice
    ) {
        return ResponseEntity.ok(productRepository.findProductWithPriceLessThan(maxPrice));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> findProductsByDescription(
            @RequestParam(value = "description", required = false) String description
    ) {
        return ResponseEntity.ok(productRepository.findByDescriptionContaining(description));
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts() {
        return getAllProductsQueryHandler.execute(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Integer id) {
        return getProductQueryHandler.execute(id);
    }

    @PostMapping
    public ResponseEntity createProduct(@RequestBody Product product) {
        return createProductCommandHandler.execute(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        UpdateProductCommand updateProductCommand = new UpdateProductCommand(id, product);
        return updateProductCommandHandler.execute(updateProductCommand);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable Integer id) {
        return deleteProductCommandHandler.execute(id);
    }
}
