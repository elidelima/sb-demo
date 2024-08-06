package com.example.demo.product;

import com.example.demo.product.model.Product;
import com.example.demo.product.model.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT p FROM Product p WHERE p.price < :maxPrice")
    List<Product> findProductWithPriceLessThan(@Param("maxPrice") double maxPrice);

    @Query("SELECT new com.example.demo.product.model.ProductDTO(p.name, p.description, p.price) FROM Product p")
    List<ProductDTO> getAllProductsDTO();

    List<Product> findByDescriptionContaining(String description);
}
