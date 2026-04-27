package com.tahir.where_did_my_money_go.group.controller;

import com.tahir.where_did_my_money_go.auth.security.CustomUserDetails;
import com.tahir.where_did_my_money_go.group.dto.CreateGroupRequest;
import com.tahir.where_did_my_money_go.group.dto.GroupActivityResponseDTO;
import com.tahir.where_did_my_money_go.group.dto.GroupBalanceResponseDTO;
import com.tahir.where_did_my_money_go.group.dto.GroupDetailsResponseDTO;
import com.tahir.where_did_my_money_go.group.dto.GroupMemberDTO;
import com.tahir.where_did_my_money_go.group.dto.GroupResponseDTO;
import com.tahir.where_did_my_money_go.group.service.ExpenseGroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/{groupId}")
    public GroupDetailsResponseDTO getGroup(
            @PathVariable UUID groupId,
            @AuthenticationPrincipal CustomUserDetails user) {

        return groupService.getGroupDetails(groupId, user.getId());
    }

    @GetMapping("/{groupId}/members")
    public List<GroupMemberDTO> getMembers(
            @PathVariable UUID groupId,
            @AuthenticationPrincipal CustomUserDetails user) {

        return groupService.getGroupMembers(groupId, user.getId());
    }

    @GetMapping("/{groupId}/balances")
    public GroupBalanceResponseDTO getBalances(
            @PathVariable UUID groupId,
            @AuthenticationPrincipal CustomUserDetails user) {

        return groupService.getGroupBalances(groupId, user.getId());
    }

    @GetMapping("/{groupId}/activities")
    public List<GroupActivityResponseDTO> getActivities(
            @PathVariable UUID groupId,
            @AuthenticationPrincipal CustomUserDetails user) {
        return groupService.getGroupActivities(groupId, user.getId());
    }
}