package com.tahir.where_did_my_money_go.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tahir.where_did_my_money_go.auth.dto.AuthResponse;
import com.tahir.where_did_my_money_go.auth.dto.UserLoginRequest;
import com.tahir.where_did_my_money_go.auth.dto.UserRegisterRequest;
import com.tahir.where_did_my_money_go.auth.util.JwtUtil;
import com.tahir.where_did_my_money_go.user.entity.User;
import com.tahir.where_did_my_money_go.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse register(UserRegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        return generateTokens(user);
    }

    public AuthResponse login(UserLoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        return generateTokens(user);
    }

    public AuthResponse refresh(String refreshToken) {

        if (!jwtUtil.validateToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        var userId = jwtUtil.extractUserId(refreshToken);

        var user = userRepository.findById(userId)
                .orElseThrow();

        return generateTokens(user);
    }

    private AuthResponse generateTokens(User user) {
        return AuthResponse.builder()
                .accessToken(jwtUtil.generateAccessToken(user.getId(), user.getEmail()))
                .refreshToken(jwtUtil.generateRefreshToken(user.getId()))
                .build();
    }
}