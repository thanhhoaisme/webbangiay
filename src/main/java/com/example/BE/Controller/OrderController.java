package com.example.BE.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {
    // Create order
    @PostMapping("/create")
    public ResponseEntity<String>createOrder(){
        return ResponseEntity.ok("Order created");
    }
}
