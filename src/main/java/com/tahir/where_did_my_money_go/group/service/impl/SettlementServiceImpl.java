package com.tahir.where_did_my_money_go.group.service.impl;

import com.tahir.where_did_my_money_go.group.dto.CreateSettlementRequest;
import com.tahir.where_did_my_money_go.group.entity.ExpenseGroup;
import com.tahir.where_did_my_money_go.group.entity.Settlement;
import com.tahir.where_did_my_money_go.group.repository.ExpenseGroupRepository;
import com.tahir.where_did_my_money_go.group.repository.SettlementRepository;
import com.tahir.where_did_my_money_go.group.service.SettlementService;
import com.tahir.where_did_my_money_go.common.exception.ResourceNotFoundException;
import com.tahir.where_did_my_money_go.common.exception.UnauthorizedException;
import com.tahir.where_did_my_money_go.user.entity.User;
import com.tahir.where_did_my_money_go.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SettlementServiceImpl implements SettlementService {

    private final SettlementRepository settlementRepository;
    private final UserRepository userRepository;
    private final ExpenseGroupRepository groupRepository;

    @Override
    @Transactional
    public void createSettlement(CreateSettlementRequest request, UUID userId) {

        User fromUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("From user not found"));
        User toUser = userRepository.findById(request.getToUserId())
                .orElseThrow(() -> new ResourceNotFoundException("To user not found"));
        ExpenseGroup group = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        Settlement settlement = Settlement.builder()
                .group(group)
                .fromUser(fromUser)
                .toUser(toUser)
                .amount(request.getAmount())
                .build();

        settlementRepository.save(settlement);
    }

    @Override
    @Transactional
    public void deleteSettlement(UUID settlementId, UUID userId) {

        User user = userRepository.findById(userId).orElseThrow();

        Settlement settlement = settlementRepository.findById(settlementId)
                .orElseThrow(() -> new ResourceNotFoundException("Settlement not found"));

        if (!settlement.getFromUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("Cannot delete this settlement");
        }

        settlementRepository.delete(settlement);
    }
}