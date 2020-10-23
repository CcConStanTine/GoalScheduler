package com.pk.ms.controllers;

import com.pk.ms.security.request.LoginRequest;
import com.pk.ms.security.request.SignupRequest;
import com.pk.ms.security.response.JwtResponse;
import com.pk.ms.security.response.MessageResponse;
import com.pk.ms.services.user.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/home")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.authenticateUser(loginRequest));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest){
        return ResponseEntity.ok(authService.registerUser(signUpRequest));
    }

}
