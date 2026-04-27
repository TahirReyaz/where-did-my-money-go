package com.tahir.where_did_my_money_go.group.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class GroupMemberDTO {

    private UUID userId;
    private String name;
    private LocalDateTime joinedAt;
}