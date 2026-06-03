package com.tahir.where_did_my_money_go.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.tahir.where_did_my_money_go.auth.dto.*;
import com.tahir.where_did_my_money_go.auth.security.CustomUserDetails;
import com.tahir.where_did_my_money_go.auth.service.AuthService;
import com.tahir.where_did_my_money_go.auth.service.EmailVerificationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final EmailVerificationService emailVerificationService;

    @GetMapping("/verify-email")
    public ResponseEntity<VerifyEmailResponseDTO> verifyEmail(
            @RequestParam String token) {

        return ResponseEntity.ok(
                emailVerificationService.verifyEmail(token));
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<Void> resendVerification(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        emailVerificationService.resendVerification(userDetails);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody UserRegisterRequest req) {
        return authService.register(req);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody UserLoginRequest req) {
        return authService.login(req);
    }
}