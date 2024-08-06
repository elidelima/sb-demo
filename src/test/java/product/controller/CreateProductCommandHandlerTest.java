package product.controller;

import com.example.demo.DemoApplication;
import com.example.demo.exceptions.ProductNotValidException;
import com.example.demo.product.ProductRepository;
import com.example.demo.product.commandhandlers.CreateProductCommandHandler;
import com.example.demo.product.model.Product;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = DemoApplication.class)
public class CreateProductCommandHandlerTest {

    @InjectMocks
    private CreateProductCommandHandler createProductCommandHandler;

    @Mock
    private ProductRepository productRepository;

    //MethodName_StateUnderTest_ExpectedBehaviour
    @Test
    public void createProductCommandHandler_validProduct_returnsSuccess() {
        // Given
        // Arrange
        Product product = new Product();
        product.setId(1);
        product.setName("Sony Headphones");
        product.setDescription("Headphone with noise cancelling");
        product.setPrice(199.99);
        product.setQuantity(10);

        // When
        // Act
        ResponseEntity response = createProductCommandHandler.execute(product);

        // Then
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void createProductCommandHandler_invalidPrice_returnsBadParamsWithMessage() {
        // Given
        Product product = new Product();
        product.setId(1);
        product.setName("Sony Headphones");
        product.setDescription("Headphone with noise cancelling");
        product.setPrice(-199.99);
        product.setQuantity(10);

        // When / Then
        ProductNotValidException exception = assertThrows(ProductNotValidException.class, () -> createProductCommandHandler.execute(product));

        // Then 2
        assertEquals("Product price cannot be negative", exception.getSimpleResponse().getMessage());
    }

}
