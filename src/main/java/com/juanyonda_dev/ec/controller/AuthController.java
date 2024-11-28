package com.juanyonda_dev.ec.controller;

import com.juanyonda_dev.ec.config.JwtUtil;
import com.juanyonda_dev.ec.model.dto.UserDto;
import com.juanyonda_dev.ec.model.entity.User;
import com.juanyonda_dev.ec.model.payload.MessageResponse;
import com.juanyonda_dev.ec.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
    @Autowired
    private IUserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        try {
            User savedUser = userService.save(userDto);
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("User registered successfully")
                    .object(savedUser)
                    .build(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Error: " + e.getMessage())
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword())
            );
            String token = jwtUtil.generateToken(userDto.getEmail());
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Login successful")
                    .object(token)
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Invalid credentials")
                    .build(), HttpStatus.UNAUTHORIZED);
        }
    }
}
