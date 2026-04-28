package com.tahir.where_did_my_money_go.expense.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.tahir.where_did_my_money_go.auth.security.CustomUserDetails;
import com.tahir.where_did_my_money_go.expense.dto.ExpenseCreateRequest;
import com.tahir.where_did_my_money_go.expense.dto.ExpenseResponse;
import com.tahir.where_did_my_money_go.expense.dto.UpdateExpenseRequestDTO;
import com.tahir.where_did_my_money_go.expense.service.ExpenseService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

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

    @PutMapping("/{expenseId}")
    public void updateExpense(
            @PathVariable UUID expenseId,
            @Valid @RequestBody UpdateExpenseRequestDTO request,
            @AuthenticationPrincipal CustomUserDetails user) {
        service.updateExpense(expenseId, request, user.getId());
    }
}