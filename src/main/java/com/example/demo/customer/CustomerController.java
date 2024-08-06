package com.example.demo.customer;

import com.example.demo.exceptions.CustomBaseException;
import com.example.demo.exceptions.ProductNotFoundException;
import com.example.demo.exceptions.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired CustomerRepository customerRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isEmpty()) {
            throw new CustomBaseException(HttpStatus.NOT_FOUND, new SimpleResponse("Customer not found"));
        }

        return  ResponseEntity.ok(customer.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCustomer(@PathVariable Integer id, @RequestBody Customer customer) {
        Optional<Customer> actualCustomer = customerRepository.findById(id);

        if (actualCustomer.isEmpty()) {
            throw new CustomBaseException(HttpStatus.NOT_FOUND, new SimpleResponse("Customer not found"));
        }
        customer.setId(id);
        customerRepository.save(customer);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
