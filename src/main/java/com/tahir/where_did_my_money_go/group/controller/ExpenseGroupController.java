package com.tahir.where_did_my_money_go.group.controller;

import com.tahir.where_did_my_money_go.auth.security.CustomUserDetails;
import com.tahir.where_did_my_money_go.group.dto.CreateGroupRequest;
import com.tahir.where_did_my_money_go.group.dto.GroupResponseDTO;
import com.tahir.where_did_my_money_go.group.service.ExpenseGroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class ExpenseGroupController {

    private final ExpenseGroupService groupService;

    @PostMapping
    public GroupResponseDTO createGroup(@RequestBody @Valid CreateGroupRequest request,
            @AuthenticationPrincipal CustomUserDetails user) {
        return groupService.createGroup(request, user.getId());
    }

    @PostMapping("/{groupId}/join")
    public void joinGroup(@PathVariable UUID groupId,
            @AuthenticationPrincipal CustomUserDetails user) {
        groupService.joinGroup(groupId, user.getId());
    }

    @PostMapping("/{groupId}/leave")
    public void leaveGroup(@PathVariable UUID groupId,
            @AuthenticationPrincipal CustomUserDetails user) {
        groupService.leaveGroup(groupId, user.getId());
    }

    @DeleteMapping("/{groupId}")
    public void deleteGroup(@PathVariable UUID groupId,
            @AuthenticationPrincipal CustomUserDetails user) {
        groupService.deleteGroup(groupId, user.getId());
    }
}