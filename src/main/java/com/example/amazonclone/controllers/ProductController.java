package com.example.amazonclone.controllers;


import com.example.amazonclone.dto.ProductDto;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.services.ProductDetailValueService;
import com.example.amazonclone.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController{

    private final ProductService productService;
    private final ProductDetailValueService productDetailValueService;

    @Autowired
    public ProductController(ProductService productService, ProductDetailValueService productDetailValueService) {
        this.productService = productService;
        this.productDetailValueService = productDetailValueService;
    }

    @GetMapping
    public ResponseEntity<ProductDto> get(@RequestParam Long id){
        try {

            return ResponseEntity.ok(productService.get(id));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "250") int quantity) {

        return ResponseEntity.ok(productService.getAll(PageRequest.of(page, quantity)));
    }

    @GetMapping("/user")
    public ResponseEntity<List<ProductDto>> getAllByUser(@RequestParam Long userId) {
        try {
            return ResponseEntity.ok(productService.getAllByUser(userId));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/size")
    public ResponseEntity<Integer> getSize() {
        return ResponseEntity.ok(productService.getSize());
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        try {
            return ResponseEntity.ok(productService.add(productDto));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/discountType")
    public ResponseEntity<List<ProductDto>> getProductsByDiscounts(@RequestParam String discountTypeName) {
        try {
            return ResponseEntity.ok(productService.getAllByDiscountTypeName(discountTypeName));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProduct(@RequestParam Long id) {
        try {
            productService.delete(id);
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }
}
