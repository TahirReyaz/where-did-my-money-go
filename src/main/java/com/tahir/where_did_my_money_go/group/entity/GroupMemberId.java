package com.tahir.where_did_my_money_go.group.entity;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupMemberId implements Serializable {

    private UUID group;
    private UUID user;
}