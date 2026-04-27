package com.tahir.where_did_my_money_go.group.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class GroupActivityResponseDTO {

    private String type; // EXPENSE or SETTLEMENT

    private UUID id;
    private UUID groupId;

    private BigDecimal amount;

    private String description;

    private UUID fromUserId;
    private UUID toUserId;

    private LocalDateTime date;
}