package com.tahir.where_did_my_money_go.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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
    private final EmailVerificationService verificationService;

    @PostMapping("/verify-email")
    public void verifyEmail(@RequestParam String token) {
        verificationService.verifyToken(token);
    }

    @PostMapping("/resend-verification")
    public void resendVerification(@AuthenticationPrincipal CustomUserDetails user) {
        verificationService.resendVerification(user.getId());
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