package com.tahir.where_did_my_money_go.group.service.impl;

import com.tahir.where_did_my_money_go.common.exception.BusinessException;
import com.tahir.where_did_my_money_go.common.exception.ResourceNotFoundException;
import com.tahir.where_did_my_money_go.common.exception.UnauthorizedException;
import com.tahir.where_did_my_money_go.group.dto.CreateGroupRequest;
import com.tahir.where_did_my_money_go.group.dto.GroupBalanceResponseDTO;
import com.tahir.where_did_my_money_go.group.dto.GroupDetailsResponseDTO;
import com.tahir.where_did_my_money_go.group.dto.GroupMemberDTO;
import com.tahir.where_did_my_money_go.group.dto.GroupResponseDTO;
import com.tahir.where_did_my_money_go.group.entity.ExpenseGroup;
import com.tahir.where_did_my_money_go.group.entity.GroupExpenseParticipant;
import com.tahir.where_did_my_money_go.group.entity.GroupMember;
import com.tahir.where_did_my_money_go.group.repository.*;
import com.tahir.where_did_my_money_go.group.service.BalanceSimplifier;
import com.tahir.where_did_my_money_go.group.service.ExpenseGroupService;
import com.tahir.where_did_my_money_go.user.entity.User;
import com.tahir.where_did_my_money_go.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private void validateUserHasNoPendingBalance(UUID groupId, UUID userId) {

        BigDecimal netBalance = participantRepository.getNetBalanceForUser(userId, groupId);

        if (netBalance.compareTo(BigDecimal.ZERO) != 0) {
            throw new BusinessException("User has pending balance. Please settle before leaving.");
        }
    }

    public GroupDetailsResponseDTO getGroupDetails(UUID groupId, UUID userId) {

        ExpenseGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        validateMembership(groupId, userId);

        List<GroupMemberDTO> members = groupMemberRepository.findAll().stream()
                .filter(m -> m.getGroup().getId().equals(groupId))
                .map(m -> GroupMemberDTO.builder()
                        .userId(m.getUser().getId())
                        .name(m.getUser().getName())
                        .joinedAt(m.getJoinedAt())
                        .build())
                .toList();

        int totalAmount = group.getExpenses() != null ? group.getExpenses().stream()
                .reduce(0, (acc, e) -> acc + e.getTotalAmount().intValue(), Integer::sum) : 0;

        return GroupDetailsResponseDTO.builder()
                .id(group.getId())
                .name(group.getName())
                .createdBy(group.getCreatedBy().getId())
                .members(members)
                .totalExpenses(group.getExpenses() != null ? group.getExpenses().size() : 0)
                .totalAmount(totalAmount)
                .build();
    }

    public List<GroupMemberDTO> getGroupMembers(UUID groupId, UUID userId) {

        validateMembership(groupId, userId);

        return groupMemberRepository.findAll().stream()
                .filter(m -> m.getGroup().getId().equals(groupId))
                .map(m -> GroupMemberDTO.builder()
                        .userId(m.getUser().getId())
                        .name(m.getUser().getName())
                        .joinedAt(m.getJoinedAt())
                        .build())
                .toList();
    }

    public GroupBalanceResponseDTO getGroupBalances(UUID groupId, UUID userId) {

        validateMembership(groupId, userId);

        List<GroupExpenseParticipant> participants = participantRepository.findAllByGroupId(groupId);

        Map<UUID, BigDecimal> netBalances = new HashMap<>();

        for (GroupExpenseParticipant p : participants) {

            BigDecimal paid = p.getPaidAmount();

            BigDecimal net = paid.subtract(p.getShareAmount());

            netBalances.merge(
                    p.getUser().getId(),
                    net,
                    BigDecimal::add);
        }

        List<GroupBalanceResponseDTO.UserBalanceDTO> balances = netBalances.entrySet().stream()
                .map(e -> {
                    BigDecimal net = e.getValue().setScale(2, RoundingMode.HALF_UP);
                    return GroupBalanceResponseDTO.UserBalanceDTO.builder()
                            .userId(e.getKey())
                            .name(userRepository.findById(e.getKey()).orElseThrow().getName())
                            .netBalance(net)
                            .owes(net.compareTo(BigDecimal.ZERO) < 0 ? net.abs() : BigDecimal.ZERO)
                            .owed(net.compareTo(BigDecimal.ZERO) > 0 ? net : BigDecimal.ZERO)
                            .build();
                }).toList();

        List<GroupBalanceResponseDTO.DebtDTO> debts = BalanceSimplifier.simplify(netBalances);

        return GroupBalanceResponseDTO.builder()
                .balances(balances)
                .debts(debts)
                .build();
    }

    private void validateMembership(UUID groupId, UUID userId) {
        boolean isMember = groupMemberRepository.existsByGroupIdAndUserId(groupId, userId);
        if (!isMember) {
            throw new UnauthorizedException("User is not part of this group");
        }
    }
}