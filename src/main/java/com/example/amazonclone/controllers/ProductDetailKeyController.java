package com.example.amazonclone.controllers;

import com.example.amazonclone.dto.CategoryDto;
import com.example.amazonclone.dto.ProductDetailKeyDto;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.services.ProductDetailKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productDetailKey")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductDetailKeyController {
    private final ProductDetailKeyService productDetailKeyService;

    @Autowired
    public ProductDetailKeyController(ProductDetailKeyService productDetailKeyService) {
        this.productDetailKeyService = productDetailKeyService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDetailKeyDto>> getProductDetailKeys(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "250") int quantity) {
        return ResponseEntity.ok(productDetailKeyService.getAll(PageRequest.of(page, quantity)));
    }

    @GetMapping("/size")
    public ResponseEntity<Integer> getSize() {
        return ResponseEntity.ok(productDetailKeyService.getSize());
    }

    @GetMapping
    public ResponseEntity<ProductDetailKeyDto> getProductDetailKey(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(productDetailKeyService.get(id));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ProductDetailKeyDto> addProductDetailKey(@RequestBody ProductDetailKeyDto productDetailKeyDto) {
        try {
            return ResponseEntity.ok(productDetailKeyService.add(productDetailKeyDto));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProductDetailKey(@RequestParam Long id) {
        try {
            productDetailKeyService.delete(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
