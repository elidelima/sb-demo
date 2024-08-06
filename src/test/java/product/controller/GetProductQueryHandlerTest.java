package product.controller;

import com.example.demo.DemoApplication;
import com.example.demo.exceptions.ProductNotFoundException;
import com.example.demo.product.ProductRepository;
import com.example.demo.product.model.Product;
import com.example.demo.product.model.ProductDTO;
import com.example.demo.product.queryhandlers.GetProductQueryHandler;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = DemoApplication.class)
public class GetProductQueryHandlerTest {

    @InjectMocks
    private GetProductQueryHandler getProductQueryHandler;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void getProductQueryHandler_validId_returnsProductDTO() {
        // Given
        Product product = new Product();
        product.setId(1);
        product.setName("Sony Headphones");
        product.setDescription("Headphone with noise cancelling");
        product.setPrice(199.99);
        product.setQuantity(10);

        ProductDTO expectedDTO = new ProductDTO(product);
        // Given
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        // When
        ResponseEntity<ProductDTO> actualResponse = getProductQueryHandler.execute(product.getId());

        // Then
        assertEquals(expectedDTO, actualResponse.getBody());
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    public void getProductQueryHandler_invalidId_throwsProductNotFoundException() {
        // Given
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        // When/Then
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> getProductQueryHandler.execute(1));

        // Then
        assertEquals("Product Not Found", exception.getSimpleResponse().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

}
