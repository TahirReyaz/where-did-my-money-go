package com.tahir.where_did_my_money_go.group.controller;

import com.tahir.where_did_my_money_go.auth.security.CustomUserDetails;
import com.tahir.where_did_my_money_go.group.dto.CreateGroupExpenseRequest;
import com.tahir.where_did_my_money_go.group.dto.UpdateGroupExpenseRequest;
import com.tahir.where_did_my_money_go.group.service.GroupExpenseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group-expenses")
@RequiredArgsConstructor
public class GroupExpenseController {

    private final GroupExpenseService groupExpenseService;

    @PostMapping
    public void create(@Valid @RequestBody CreateGroupExpenseRequest request,
            @AuthenticationPrincipal CustomUserDetails user) {

        groupExpenseService.createGroupExpense(request, user.getId());
    }

    @PutMapping
    public void update(@Valid @RequestBody UpdateGroupExpenseRequest request,
            @AuthenticationPrincipal CustomUserDetails user) {

        groupExpenseService.updateGroupExpense(request, user.getId());
    }
}