package com.example.amazonclone.controllers;

import com.example.amazonclone.dto.CategoryDto;
import com.example.amazonclone.dto.DiscountTypeDto;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.models.DiscountType;
import com.example.amazonclone.services.DiscountTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discountType")
@CrossOrigin(origins = "http://localhost:3000")
public class DiscountTypeController {
    private final DiscountTypeService discountTypeService;

    @Autowired
    public DiscountTypeController(DiscountTypeService discountTypeService) {
        this.discountTypeService = discountTypeService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<DiscountTypeDto>> getDiscountTypes(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "250") int quantity) {
        return ResponseEntity.ok(discountTypeService.getAll(PageRequest.of(page, quantity)));
    }

    @GetMapping("/size")
    public ResponseEntity<Integer> getSize() {
        return ResponseEntity.ok(discountTypeService.getSize());
    }

    @GetMapping
    public ResponseEntity<DiscountTypeDto> getDiscountType(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(discountTypeService.get(id));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/otherDiscounts")
    public ResponseEntity<List<DiscountTypeDto>> getOtherDiscountTypeDtos(@RequestParam String exceptDiscountTypeName) {
        try {
            return ResponseEntity.ok(discountTypeService.getOtherDiscountTypes(exceptDiscountTypeName));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    public ResponseEntity<DiscountTypeDto> addDiscountType(@RequestBody DiscountTypeDto discountTypeDto) {
        return ResponseEntity.ok(discountTypeService.add(discountTypeDto));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteDiscountType(@RequestParam Long id) {
        try {
            discountTypeService.delete(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
