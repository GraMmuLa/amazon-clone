package com.example.amazonclone.controllers;

import com.example.amazonclone.dto.BannerDto;
import com.example.amazonclone.exceptions.EntityAlreadyExistsException;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.services.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/banner")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class BannerController {
    private final BannerService bannerService;

    @GetMapping("/all")
    public ResponseEntity<List<BannerDto>> getAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "250") int quantity) {
        return ResponseEntity.ok(bannerService.getAll(PageRequest.of(page, quantity)));
    }

    @GetMapping
    public ResponseEntity<BannerDto> get(@RequestParam Long id)  {
        try {
            return ResponseEntity.ok(bannerService.get(id).deflateImage());
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user")
    public ResponseEntity<BannerDto> getByUser(@RequestParam Long userId) {
        try {
            return ResponseEntity.ok(bannerService.getByUser(userId));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<BannerDto> add(@RequestParam MultipartFile file, @RequestParam Long userId) {
        try {
            return ResponseEntity.ok(bannerService.add(new BannerDto(file, userId)));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (EntityAlreadyExistsException ex) {
            return ResponseEntity.badRequest().build();
        } catch (IOException ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam Long id) {
        try {
            bannerService.delete(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
