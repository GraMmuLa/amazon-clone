package com.example.amazonclone.controllers;

import com.example.amazonclone.dto.ProductTypeDto;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.services.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productType")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductTypeController {
    private final ProductTypeService productTypeService;

    @Autowired
    public ProductTypeController(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductTypeDto>> getProductTypes(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "250") int quantity) {
        return ResponseEntity.ok(productTypeService.getAll(PageRequest.of(page, quantity)));
    }

    @GetMapping("/size")
    public ResponseEntity<Integer> getSize() {
        return ResponseEntity.ok(productTypeService.getSize());
    }

    @GetMapping
    public ResponseEntity<ProductTypeDto> getProductType(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(productTypeService.get(id));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/subcategory")
    public ResponseEntity<List<ProductTypeDto>> getProductTypesBySubcategory(@RequestParam Long subcategoryId) {
        return ResponseEntity.ok(productTypeService.getAllBySubcategory(subcategoryId));
    }

    @PostMapping
    public ResponseEntity<ProductTypeDto> addProductType(@RequestBody ProductTypeDto productTypeDto) {
        try {
            return ResponseEntity.ok(productTypeService.add(productTypeDto));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProductType(@RequestParam Long id) {
        try {
            productTypeService.delete(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
