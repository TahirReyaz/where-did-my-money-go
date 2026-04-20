package com.tahir.where_did_my_money_go.group.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateGroupRequest {

    @NotBlank
    private String name;
}