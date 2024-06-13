package com.example.amazonclone.controllers;

import com.example.amazonclone.dto.ProductCardDto;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.services.ProductCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(("/productCard"))
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ProductCardController {
    private final ProductCardService productCardService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductCardDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "250") int quantity) {
        return ResponseEntity.ok(productCardService.getAll(PageRequest.of(page, quantity)));
    }

    @GetMapping
    public ResponseEntity<ProductCardDto> get(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(productCardService.get(id));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ProductCardDto> add(@RequestBody ProductCardDto dtoEntity) {
        try {
            return ResponseEntity.ok(productCardService.add(dtoEntity));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam Long id) {
        try {
            productCardService.delete(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
