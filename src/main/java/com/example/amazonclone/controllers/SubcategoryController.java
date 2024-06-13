package com.example.amazonclone.controllers;

import com.example.amazonclone.dto.SubcategoryDto;
import com.example.amazonclone.exceptions.EntityAlreadyExistsException;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.services.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/subcategory")
@CrossOrigin(origins = "http://localhost:3000")
public class SubcategoryController {
    private final SubcategoryService subcategoryService;

    @Autowired
    public SubcategoryController(SubcategoryService subcategoryService) {
        this.subcategoryService = subcategoryService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<SubcategoryDto>> getSubcategories(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "250") int quantity) {
        return ResponseEntity.ok(subcategoryService.getAll(PageRequest.of(page, quantity)));
    }

    @GetMapping("/size")
    public ResponseEntity<Integer> getSubcategoriesSize() {
        return ResponseEntity.ok(subcategoryService.getSize());
    }

    @GetMapping
    public ResponseEntity<SubcategoryDto> getSubcategory(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(subcategoryService.get(id));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<SubcategoryDto> addSubcategory(@RequestBody SubcategoryDto subcategoryDto) {
        try {
            return ResponseEntity.ok(subcategoryService.add(subcategoryDto));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/withImage")
    public ResponseEntity<SubcategoryDto> addSubcategoryWithImage(@RequestParam MultipartFile file,
                                                                  @RequestParam String name,
                                                                  @RequestParam Long categoryId) {
        try {
            return ResponseEntity.ok(subcategoryService.addWithImage(file, new SubcategoryDto(name, categoryId)));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (IOException | EntityAlreadyExistsException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteSubcategory(@RequestParam Long id) {
        try {
            subcategoryService.delete(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
