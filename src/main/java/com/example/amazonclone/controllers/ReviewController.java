package com.example.amazonclone.controllers;

import com.example.amazonclone.dto.ReviewDto;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productReview")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReviewDto>> getProductReviews(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "250") int quantity) {
        return ResponseEntity.ok(reviewService.getAll(PageRequest.of(page, quantity)));
    }

    @GetMapping("/size")
    public ResponseEntity<Integer> getSize() {
        return ResponseEntity.ok(reviewService.getSize());
    }

    @GetMapping
    public ResponseEntity<ReviewDto> get(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(reviewService.get(id));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ReviewDto> addProductReview(@RequestBody ReviewDto reviewDto) {
        try {
            return ResponseEntity.ok(reviewService.add(reviewDto));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProductReview(@RequestParam Long id) {
        try {
            reviewService.delete(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
