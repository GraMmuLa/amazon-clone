package com.example.amazonclone.controllers;

import com.example.amazonclone.dto.CategoryDto;
import com.example.amazonclone.dto.DiscountDto;
import com.example.amazonclone.exceptions.EntityAlreadyExistsException;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.services.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discount")
@CrossOrigin(origins = "http://localhost:3000")
public class DiscountController {
    private final DiscountService discountService;

    @Autowired
    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping
    public ResponseEntity<DiscountDto> getDiscount(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(discountService.get(id));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/productColor")
    public ResponseEntity<DiscountDto> getDiscountByProductColorId(@RequestParam Long productColorId) {
        try {
            return ResponseEntity.ok(discountService.getByProductColorId(productColorId));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<DiscountDto>> getDiscounts(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "250") int quantity) {
        return ResponseEntity.ok(discountService.getAll(PageRequest.of(page, quantity)));
    }

    @GetMapping("/size")
    public ResponseEntity<Integer> getSize() {
        return ResponseEntity.ok(discountService.getSize());
    }

    @PostMapping
    public ResponseEntity<DiscountDto> addDiscount(@RequestBody DiscountDto discountDto) {
        try {
            return ResponseEntity.ok(discountService.add(discountDto));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (EntityAlreadyExistsException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteDiscount(@RequestParam Long id) {
        try {
            discountService.delete(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
