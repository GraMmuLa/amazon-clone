package com.example.amazonclone.controllers;

import com.example.amazonclone.dto.ReviewImageDto;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.services.ReviewImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/reviewImage")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ReviewImageController {
    private final ReviewImageService reviewImageService;

    @GetMapping("/all")
    public ResponseEntity<List<ReviewImageDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "250") int quantity) {
        return ResponseEntity.ok(reviewImageService.getAll(PageRequest.of(page, quantity)));
    }

    @GetMapping
    public ResponseEntity<ReviewImageDto> get(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(reviewImageService.get(id).deflateImage());
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (IOException ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<ReviewImageDto> add(@RequestParam MultipartFile file, @RequestParam Long reviewId) {
        try {
            return ResponseEntity.ok(reviewImageService.add(new ReviewImageDto(file, reviewId)));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (IOException ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam Long id) {
        try {
            reviewImageService.delete(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
