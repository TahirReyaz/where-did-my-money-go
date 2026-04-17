package com.tahir.where_did_my_money_go.expense.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.tahir.where_did_my_money_go.auth.security.CustomUserDetails;
import com.tahir.where_did_my_money_go.expense.dto.ExpenseCreateRequest;
import com.tahir.where_did_my_money_go.expense.dto.ExpenseResponse;
import com.tahir.where_did_my_money_go.expense.service.ExpenseService;

import java.util.List;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService service;

    @PostMapping
    public ExpenseResponse create(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody ExpenseCreateRequest request) {
        return service.create(user.getId(), request);
    }

    @GetMapping
    public List<ExpenseResponse> getAll(
            @AuthenticationPrincipal CustomUserDetails user) {
        return service.getUserExpenses(user.getId());
    }
}