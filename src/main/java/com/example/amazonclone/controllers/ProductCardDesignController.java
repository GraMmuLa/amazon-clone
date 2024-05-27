package com.example.amazonclone.controllers;

import com.example.amazonclone.dto.ProductCardDesignDto;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.services.ProductCardDesignService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(("/productCardDesign"))
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ProductCardDesignController {
    private final ProductCardDesignService productCardDesignService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductCardDesignDto>> getAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "250") int quantity
    ) {
        return ResponseEntity.ok(productCardDesignService.getAll(PageRequest.of(page, quantity)));
    }

    @GetMapping
    public ResponseEntity<ProductCardDesignDto> get(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(productCardDesignService.get(id));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ProductCardDesignDto> add(@RequestBody ProductCardDesignDto dtoEntity) {
        return ResponseEntity.ok(productCardDesignService.add(dtoEntity));
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam Long id) {
        try {
            productCardDesignService.delete(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
