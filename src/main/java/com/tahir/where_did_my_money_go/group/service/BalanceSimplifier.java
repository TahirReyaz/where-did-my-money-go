package com.tahir.where_did_my_money_go.group.service;

import com.tahir.where_did_my_money_go.group.dto.GroupBalanceResponseDTO;
import com.tahir.where_did_my_money_go.user.repository.UserRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class BalanceSimplifier {

    private static UserRepository userRepository;

    public static List<GroupBalanceResponseDTO.DebtDTO> simplify(Map<UUID, BigDecimal> netBalances) {

        List<Map.Entry<UUID, BigDecimal>> creditors = new ArrayList<>();
        List<Map.Entry<UUID, BigDecimal>> debtors = new ArrayList<>();

        for (var entry : netBalances.entrySet()) {
            if (entry.getValue().compareTo(BigDecimal.ZERO) > 0) {
                creditors.add(entry);
            } else if (entry.getValue().compareTo(BigDecimal.ZERO) < 0) {
                debtors.add(entry);
            }
        }

        List<GroupBalanceResponseDTO.DebtDTO> result = new ArrayList<>();

        int i = 0, j = 0;

        while (i < debtors.size() && j < creditors.size()) {

            var debtor = debtors.get(i);
            var creditor = creditors.get(j);

            BigDecimal debt = debtor.getValue().abs();
            BigDecimal credit = creditor.getValue();

            BigDecimal settled = debt.min(credit).setScale(2, RoundingMode.HALF_UP);

            result.add(GroupBalanceResponseDTO.DebtDTO.builder()
                    .fromUserId(debtor.getKey())
                    .fromUserName(userRepository.findById(debtor.getKey()).orElseThrow().getName())
                    .toUserId(creditor.getKey())
                    .toUserName(userRepository.findById(creditor.getKey()).orElseThrow().getName())
                    .amount(settled)
                    .build());

            debtor.setValue(debtor.getValue().add(settled));
            creditor.setValue(creditor.getValue().subtract(settled));

            if (debtor.getValue().compareTo(BigDecimal.ZERO) == 0)
                i++;
            if (creditor.getValue().compareTo(BigDecimal.ZERO) == 0)
                j++;
        }

        return result;
    }
}