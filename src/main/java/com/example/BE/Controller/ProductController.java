package com.example.BE.Controller;

import com.example.BE.DTO.request.CreateProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("products")
public class ProductController {
    // Create product
    @PostMapping("")
    public ResponseEntity<String>createProduct(@RequestBody CreateProductRequest productDTO){
        System.out.println("Received productDTO: " + productDTO);
        return ResponseEntity.ok("Product created");

    }
    // Get all products
    @GetMapping("/getall")
    public ResponseEntity<String>getAllProducts(){
        return ResponseEntity.ok("All products");
    }
    // Get product by id
    @GetMapping("/getbyid")
    public ResponseEntity<String>getIdProducts(){
        return ResponseEntity.ok("Product by id");
    }
    // Update product
    @PutMapping("/update")
    public ResponseEntity<String>updateProduct(){
        return ResponseEntity.ok("Product updated");
    }
    // Delete product
    @DeleteMapping("/delete")
    public ResponseEntity<String>deleteProduct(){
        return ResponseEntity.ok("Product deleted");
    }
}
