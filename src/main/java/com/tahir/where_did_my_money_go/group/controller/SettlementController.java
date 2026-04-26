package com.tahir.where_did_my_money_go.group.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.tahir.where_did_my_money_go.auth.security.CustomUserDetails;
import com.tahir.where_did_my_money_go.group.dto.CreateSettlementRequest;
import com.tahir.where_did_my_money_go.group.service.SettlementService;

import jakarta.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping("/settlements")
@RequiredArgsConstructor
public class SettlementController {

    private final SettlementService settlementService;

    @PostMapping
    public void create(@Valid @RequestBody CreateSettlementRequest request,
            @AuthenticationPrincipal CustomUserDetails user) {

        settlementService.createSettlement(request, user.getId());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id,
            @AuthenticationPrincipal CustomUserDetails user) {

        settlementService.deleteSettlement(id, user.getId());
    }
}