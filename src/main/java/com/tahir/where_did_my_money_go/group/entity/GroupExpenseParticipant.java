package com.tahir.where_did_my_money_go.group.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

import com.tahir.where_did_my_money_go.user.entity.User;

@Entity
@Table(name = "group_expense_participants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupExpenseParticipant {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "share_amount", precision = 14, scale = 2)
    private BigDecimal shareAmount;

    private Boolean paid = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_expense_id")
    private GroupExpense groupExpense;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}