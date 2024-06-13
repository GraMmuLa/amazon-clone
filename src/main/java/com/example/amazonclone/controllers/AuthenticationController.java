package com.example.amazonclone.controllers;

import com.example.amazonclone.configuration.UserAuthProvider;
import com.example.amazonclone.dto.CredentialsDto;
import com.example.amazonclone.dto.SignUpDto;
import com.example.amazonclone.dto.UserDto;
import com.example.amazonclone.exceptions.AuthenticationException;
import com.example.amazonclone.exceptions.EntityAlreadyExistsException;
import com.example.amazonclone.exceptions.NotFoundException;
import com.example.amazonclone.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final UserAuthProvider userAuthProvider;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody CredentialsDto credentialsDto) {
        Map<String, String> response = new HashMap<>();
        try {
            UserDto user = userService.login(credentialsDto);

            user.setToken(userAuthProvider.createToken(user));


            response.put("token", user.getToken());

            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (AuthenticationException ex) {

            response.put("errorMessage", "Неправильний пароль чи пошта!");

            return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(response);
        }
    }

    @PostMapping("/loginByEmail")
    public ResponseEntity<Map<String, String>> loginByEmail(@RequestBody CredentialsDto credentialsDto) {
        try {
            UserDto user = userService.loginByEmail(credentialsDto);

            user.setToken(userAuthProvider.createToken(user));

            Map<String, String> response = new HashMap<>();

            response.put("token", user.getToken());

            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/loginByPhone")
    public ResponseEntity<Map<String, String>> loginByPhone(@RequestBody CredentialsDto credentialsDto) {
        try {
            UserDto user = userService.loginByPhone(credentialsDto);

            user.setToken(userAuthProvider.createToken(user));

            Map<String, String> response = new HashMap<>();

            response.put("token", user.getToken());

            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody SignUpDto signUpDto) {
        try {
            UserDto user = userService.register(signUpDto);

            user.setToken(userAuthProvider.createToken(user));

            Map<String, String> response = new HashMap<>();

            response.put("token", user.getToken());

            return ResponseEntity.ok(response);
        } catch (EntityAlreadyExistsException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
