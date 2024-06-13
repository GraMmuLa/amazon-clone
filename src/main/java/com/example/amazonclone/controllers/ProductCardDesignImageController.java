package com.example.amazonclone.controllers;

import com.example.amazonclone.dto.ProductCardDesignImageDto;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.services.ProductCardDesignImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/productCardDesignImage")
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
public class ProductCardDesignImageController {
    private final ProductCardDesignImageService productCardDesignImageService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductCardDesignImageDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "250") int quantity) {
        return ResponseEntity.ok(productCardDesignImageService.getAll(PageRequest.of(page, quantity)));
    }

    @GetMapping
    public ResponseEntity<ProductCardDesignImageDto> get(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(productCardDesignImageService.get(id).deflateImage());
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (IOException ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<ProductCardDesignImageDto> add(@RequestParam MultipartFile file,
                                                         @RequestParam Long productCardDesignId) {
        try {
            return ResponseEntity.ok(productCardDesignImageService.add(new ProductCardDesignImageDto(file, productCardDesignId)));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (IOException ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam Long id) {
        try {
            productCardDesignImageService.delete(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
