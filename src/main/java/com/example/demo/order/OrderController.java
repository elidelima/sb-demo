package com.example.demo.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired private OrderRepository orderRepository;

    @PostMapping
    public ResponseEntity createOrder() {
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setTotal(19.99);
        orderRepository.save(order);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable String id) {
        return ResponseEntity.ok(orderRepository.findById(UUID.fromString(id)).get());
    }

}
