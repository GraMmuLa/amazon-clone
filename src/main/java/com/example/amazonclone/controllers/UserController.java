package com.example.amazonclone.controllers;

import com.example.amazonclone.configuration.UserAuthProvider;
import com.example.amazonclone.dto.UserDto;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserAuthProvider userAuthProvider;

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping
    public ResponseEntity<UserDto> get(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(userService.getById(id));
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/addFavouriteProductColor")
    public ResponseEntity<Map<String, String>> addFavouriteColorToUser(@RequestParam Long userId,
                                                                       @RequestParam Long productColorId,
                                                                       @RequestParam Long validityFrom,
                                                                       @RequestParam Long validityTo) {
        try {
            Map<String, String> response = new HashMap<>();

            response.put("token", userAuthProvider.createTokenWithTime(userService.addFavouriteProductColor(userId, productColorId), validityFrom, validityTo));

            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteFavouriteProductColor")
    public ResponseEntity<Map<String, String>> deleteFavouriteColorToUser(@RequestParam Long userId,
                                                                       @RequestParam Long productColorId,
                                                                       @RequestParam Long validityFrom,
                                                                       @RequestParam Long validityTo) {
        try {
            Map<String, String> response = new HashMap<>();

            response.put("token", userAuthProvider.createTokenWithTime(userService.deleteFavouriteProductColor(userId, productColorId), validityFrom, validityTo));

            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
