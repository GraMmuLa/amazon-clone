package com.example.amazonclone.controllers;

import com.example.amazonclone.dto.ColorDto;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.services.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/color")
@CrossOrigin(origins = "http://localhost:3000")
public class ColorController {
    private final ColorService colorService;

    @Autowired
    public ColorController(ColorService colorService) {
        this.colorService = colorService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ColorDto>> getColors(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "250") int quantity) {
        return ResponseEntity.ok(colorService.getAll(PageRequest.of(page, quantity)));
    }

    @GetMapping("/size")
    public ResponseEntity<Integer> getSize() {
        return ResponseEntity.ok(colorService.getSize());
    }

    @GetMapping
    public ResponseEntity<ColorDto> getColor(@RequestParam Long id) {
        try {
            ColorDto colorDto = colorService.get(id);
            return ResponseEntity.ok(colorDto);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/productColor")
    public ResponseEntity<ColorDto> getByProductColorId(@RequestParam Long productColorId) {
        try {
            ColorDto colorDto = colorService.getByProductColorId(productColorId);
            return ResponseEntity.ok(colorDto);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<ColorDto> addColor(@RequestBody ColorDto colorDto) {
        try {
            return ResponseEntity.ok(colorService.add(colorDto));
        } catch (NotFoundException ex) {
            return  ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteColor(@RequestParam Long id) {
        try {
            colorService.delete(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
