package com.tahir.where_did_my_money_go.group.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CreateSettlementRequest {

    @NotNull(message = "Group ID is required")
    private UUID groupId;

    @NotNull(message = "Recipient user ID (toUserId) is required")
    private UUID toUserId;

    @NotNull(message = "Settlement amount is required")
    @DecimalMin(value = "0.01", inclusive = true, message = "Settlement amount must be greater than 0")
    private BigDecimal amount;
}