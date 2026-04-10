package com.tahir.where_did_my_money_go.group.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.tahir.where_did_my_money_go.user.entity.User;

@Entity
@Table(name = "group_members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(GroupMemberId.class)
public class GroupMember {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String role;

    private LocalDateTime joinedAt;
}