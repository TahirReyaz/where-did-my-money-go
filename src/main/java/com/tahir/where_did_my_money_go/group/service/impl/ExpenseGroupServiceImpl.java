package com.tahir.where_did_my_money_go.group.service.impl;

import com.tahir.where_did_my_money_go.common.exception.BusinessException;
import com.tahir.where_did_my_money_go.group.dto.CreateGroupRequest;
import com.tahir.where_did_my_money_go.group.dto.GroupResponseDTO;
import com.tahir.where_did_my_money_go.group.entity.ExpenseGroup;
import com.tahir.where_did_my_money_go.group.entity.GroupMember;
import com.tahir.where_did_my_money_go.group.repository.*;
import com.tahir.where_did_my_money_go.group.service.ExpenseGroupService;
import com.tahir.where_did_my_money_go.user.entity.User;
import com.tahir.where_did_my_money_go.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpenseGroupServiceImpl implements ExpenseGroupService {

    private final ExpenseGroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final GroupExpenseParticipantRepository participantRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public GroupResponseDTO createGroup(CreateGroupRequest request, UUID userId) {

        User user = userRepository.findById(userId).orElseThrow();

        ExpenseGroup group = ExpenseGroup.builder()
                .name(request.getName())
                .createdBy(user)
                .createdAt(LocalDateTime.now())
                .build();

        groupRepository.save(group);

        groupMemberRepository.save(GroupMember.builder()
                .group(group)
                .user(user)
                .joinedAt(LocalDateTime.now())
                .build());

        return GroupResponseDTO.builder()
                .id(group.getId())
                .name(group.getName())
                .createdBy(user.getId())
                .createdAt(group.getCreatedAt())
                .build();
    }

    @Override
    @Transactional
    public void joinGroup(UUID groupId, UUID userId) {

        User user = userRepository.findById(userId).orElseThrow();
        ExpenseGroup group = groupRepository.findById(groupId).orElseThrow();

        if (groupMemberRepository.existsByGroupIdAndUserId(groupId, user.getId()))
            return;

        groupMemberRepository.save(GroupMember.builder()
                .group(group)
                .user(user)
                .joinedAt(LocalDateTime.now())
                .build());
    }

    @Override
    @Transactional
    public void leaveGroup(UUID groupId, UUID userId) {

        User user = userRepository.findById(userId).orElseThrow();

        validateUserHasNoPendingBalance(groupId, user.getId());

        GroupMember member = groupMemberRepository
                .findByGroupIdAndUserId(groupId, user.getId())
                .orElseThrow(() -> new BusinessException("User is not part of the group"));

        groupMemberRepository.delete(member);
    }

    @Override
    @Transactional
    public void deleteGroup(UUID groupId, UUID userId) {

        User user = userRepository.findById(userId).orElseThrow();
        ExpenseGroup group = groupRepository.findById(groupId).orElseThrow();

        if (!group.getCreatedBy().getId().equals(user.getId())) {
            throw new BusinessException("Only owner can delete group");
        }

        // Validate all members have zero balance
        groupMemberRepository.findAll().stream()
                .filter(m -> m.getGroup().getId().equals(groupId))
                .forEach(m -> validateUserHasNoPendingBalance(groupId, m.getUser().getId()));

        groupRepository.delete(group);
    }

    // ================= CORE VALIDATION =================

    private void validateUserHasNoPendingBalance(UUID groupId, UUID userId) {

        BigDecimal netBalance = participantRepository.getNetBalanceForUser(userId, groupId);

        if (netBalance.compareTo(BigDecimal.ZERO) != 0) {
            throw new BusinessException("User has pending balance. Please settle before leaving.");
        }
    }
}