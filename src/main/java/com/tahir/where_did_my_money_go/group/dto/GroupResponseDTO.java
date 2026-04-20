package com.tahir.where_did_my_money_go.group.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class GroupResponseDTO {

    private UUID id;
    private String name;
    private UUID createdBy;
    private LocalDateTime createdAt;
}