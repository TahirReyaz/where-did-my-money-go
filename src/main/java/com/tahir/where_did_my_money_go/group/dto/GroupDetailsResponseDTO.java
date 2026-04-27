package com.tahir.where_did_my_money_go.group.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class GroupDetailsResponseDTO {

    private UUID id;
    private String name;
    private UUID createdBy;
    private List<GroupMemberDTO> members;
    private int totalExpenses;
    private int totalAmount;
}