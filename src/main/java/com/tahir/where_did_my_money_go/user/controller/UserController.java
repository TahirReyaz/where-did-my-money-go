package com.tahir.where_did_my_money_go.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.tahir.where_did_my_money_go.auth.security.CustomUserDetails;
import com.tahir.where_did_my_money_go.user.dto.UserResponseDTO;
import com.tahir.where_did_my_money_go.user.dto.UserUpdateRequestDTO;
import com.tahir.where_did_my_money_go.user.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserResponseDTO getCurrentUser(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return userService.getCurrentUser(userDetails.getId());
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable UUID id) {
        return userService.getUserById(userDetails.getId(), id);
    }

    @PutMapping("/me")
    public UserResponseDTO updateCurrentUser(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody UserUpdateRequestDTO request) {
        return userService.updateCurrentUser(userDetails.getId(), request);
    }
}