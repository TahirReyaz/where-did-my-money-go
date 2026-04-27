package com.tahir.where_did_my_money_go.group.service;

import com.tahir.where_did_my_money_go.group.dto.CreateGroupRequest;
import com.tahir.where_did_my_money_go.group.dto.GroupBalanceResponseDTO;
import com.tahir.where_did_my_money_go.group.dto.GroupDetailsResponseDTO;
import com.tahir.where_did_my_money_go.group.dto.GroupMemberDTO;
import com.tahir.where_did_my_money_go.group.dto.GroupResponseDTO;

import java.util.UUID;

public interface ExpenseGroupService {

    GroupResponseDTO createGroup(CreateGroupRequest request, UUID userId);

    void joinGroup(UUID groupId, UUID userId);

    void leaveGroup(UUID groupId, UUID userId);

    void deleteGroup(UUID groupId, UUID userId);

    GroupDetailsResponseDTO getGroupDetails(UUID groupId, UUID userId);

    java.util.List<GroupMemberDTO> getGroupMembers(UUID groupId, UUID userId);

    GroupBalanceResponseDTO getGroupBalances(UUID groupId, UUID userId);
}