package com.example.BE.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("category")
public class CategoryController {
    // Create category
    @PostMapping("/create")
    public ResponseEntity<String>createCategory(){
        return ResponseEntity.ok("Category created");
    }
    // Get all categories
    @GetMapping("/getall")
    public ResponseEntity<String>getAllCategories(){
        return ResponseEntity.ok("All categories");
    }
    // Get category by id
    @GetMapping("/getbyid")
    public ResponseEntity<String>getIdCategories(){
        return ResponseEntity.ok("Category by id");
    }
    // Update category
    @PutMapping("/update")
    public ResponseEntity<String>updateCategory(){
        return ResponseEntity.ok("Category updated");
    }
    // Delete category
    @DeleteMapping("/delete")
    public ResponseEntity<String>deleteCategory(){
        return ResponseEntity.ok("Category deleted");
    }
}
