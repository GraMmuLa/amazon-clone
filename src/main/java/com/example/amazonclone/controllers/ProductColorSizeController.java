package com.example.amazonclone.controllers;

import com.example.amazonclone.dto.ProductColorSizeDto;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.services.ProductColorSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productColorSize")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductColorSizeController {

    private final ProductColorSizeService productColorSizeService;

    @Autowired
    public ProductColorSizeController(ProductColorSizeService productColorSizeService) {
        this.productColorSizeService = productColorSizeService;
    }

    @GetMapping
    public ResponseEntity<ProductColorSizeDto> getProductColorSize(@RequestParam Long id){
        try {

            return ResponseEntity.ok(productColorSizeService.get(id));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductColorSizeDto>> getProductColorSizes(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "250") int quantity) {

        return ResponseEntity.ok(productColorSizeService.getAll(PageRequest.of(page, quantity)));
    }

    @PostMapping
    public ResponseEntity<ProductColorSizeDto> addProductColorSIze(@RequestBody ProductColorSizeDto productColorSizeDto) {
        try {
            return ResponseEntity.ok(productColorSizeService.add(productColorSizeDto));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProductColorSize(@RequestParam Long id) {
        try {
            productColorSizeService.delete(id);
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }

}
