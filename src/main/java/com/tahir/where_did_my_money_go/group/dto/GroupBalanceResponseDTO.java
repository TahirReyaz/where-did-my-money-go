package com.tahir.where_did_my_money_go.group.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class GroupBalanceResponseDTO {

    private List<UserBalanceDTO> balances;
    private List<DebtDTO> debts;

    @Data
    @Builder
    public static class UserBalanceDTO {
        private UUID userId;
        private String name;
        private BigDecimal owes;
        private BigDecimal owed;
        private BigDecimal netBalance;
    }

    @Data
    @Builder
    public static class DebtDTO {
        private UUID fromUserId;
        private String fromUserName;
        private UUID toUserId;
        private String toUserName;
        private BigDecimal amount;
    }
}