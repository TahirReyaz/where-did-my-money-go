package com.tahir.where_did_my_money_go.preferences.controller;

import com.tahir.where_did_my_money_go.auth.security.CustomUserDetails;
import com.tahir.where_did_my_money_go.preferences.dto.UserPreferenceResponseDTO;
import com.tahir.where_did_my_money_go.preferences.dto.UserPreferenceUpdateRequestDTO;
import com.tahir.where_did_my_money_go.preferences.service.UserPreferenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/preferences")
@RequiredArgsConstructor
public class UserPreferenceController {

    private final UserPreferenceService userPreferenceService;

    @GetMapping("/me")
    public UserPreferenceResponseDTO getMyPreferences(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        return userPreferenceService.getCurrentUserPreferences(
                userDetails.getId());
    }

    @PutMapping("/me")
    public UserPreferenceResponseDTO updatePreferences(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody UserPreferenceUpdateRequestDTO request) {

        return userPreferenceService.updatePreferences(
                userDetails.getId(),
                request);
    }
}