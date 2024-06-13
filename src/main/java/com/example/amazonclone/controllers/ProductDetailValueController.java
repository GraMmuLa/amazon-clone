package com.example.amazonclone.controllers;

import com.example.amazonclone.dto.CategoryDto;
import com.example.amazonclone.dto.ProductDetailValueDto;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.models.Product;
import com.example.amazonclone.models.ProductDetailValue;
import com.example.amazonclone.services.ProductDetailValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productDetailValue")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductDetailValueController {
    private final ProductDetailValueService productDetailValueService;

    @Autowired
    public ProductDetailValueController(ProductDetailValueService productDetailValueService) {
        this.productDetailValueService = productDetailValueService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDetailValueDto>> getProductDetailValues(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "250") int quantity) {
        return ResponseEntity.ok(productDetailValueService.getAll(PageRequest.of(page, quantity)));
    }

    @GetMapping("/size")
    public ResponseEntity<Integer> getSize() {
        return ResponseEntity.ok(productDetailValueService.getSize());
    }

    @GetMapping
    public ResponseEntity<ProductDetailValueDto> getProductDetailValue(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(productDetailValueService.get(id));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ProductDetailValueDto> addProductDetailValue(@RequestBody ProductDetailValueDto productDetailValueDto) {
        try {
            return ResponseEntity.ok(productDetailValueService.add(productDetailValueDto));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProductDetailValue(@RequestParam Long id) {
        try {
            productDetailValueService.delete(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
