package com.example.amazonclone.controllers;

import com.example.amazonclone.dto.ProductColorImageDto;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.services.ProductColorImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/productColorImage")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductColorImageController {
    private final ProductColorImageService productColorImageService;

    @Autowired
    public ProductColorImageController(ProductColorImageService productImageService) {
        this.productColorImageService = productImageService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductColorImageDto>> getProductColorImages(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "250") int quantity) {
        return ResponseEntity.ok(productColorImageService.getAll(PageRequest.of(page, quantity)));
    }

    @GetMapping("/size")
    public ResponseEntity<Integer> getSize() {
        return ResponseEntity.ok(productColorImageService.getSize());
    }

    @GetMapping("/productColor")
    public ResponseEntity<List<ProductColorImageDto>> getProductColorImagesByProductColorId(@RequestParam Long productColorId) {
        try {
            return ResponseEntity.ok(productColorImageService.getAllByProductColorId(productColorId));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<Object> getProductImage(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(productColorImageService.get(id).deflateImage());
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = "multipart/form-data;charset=UTF-8")
    public ResponseEntity<ProductColorImageDto> addProductImage(@RequestParam MultipartFile file, @RequestParam Long productColorId) throws IOException {
        try {
            return ResponseEntity.ok(productColorImageService.add(new ProductColorImageDto(file, productColorId)));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProductImage(@RequestParam Long id) {
        try {
            productColorImageService.delete(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
