package com.tahir.where_did_my_money_go.group.entity;

import com.tahir.where_did_my_money_go.expense.entity.Expense;
import com.tahir.where_did_my_money_go.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

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

    @ManyToOne(fetch = FetchType.LAZY)
    private GroupExpense groupExpense;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private BigDecimal shareAmount;

    private BigDecimal paidAmount;

    @OneToOne(fetch = FetchType.LAZY)
    private Expense expense;
}