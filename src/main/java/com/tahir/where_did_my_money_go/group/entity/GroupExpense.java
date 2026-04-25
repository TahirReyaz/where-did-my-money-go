package com.tahir.where_did_my_money_go.group.entity;

import com.tahir.where_did_my_money_go.common.entity.BaseEntity;
import com.tahir.where_did_my_money_go.expense.entity.ExpenseCategory;
import com.tahir.where_did_my_money_go.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "group_expenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupExpense extends BaseEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ExpenseGroup group;

    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    private BigDecimal totalAmount;

    private String description;

    private LocalDate expenseDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private ExpenseCategory category;

    @OneToMany(mappedBy = "groupExpense", cascade = CascadeType.ALL, orphanRemoval = true // ✅ VERY IMPORTANT
    )
    private List<GroupExpenseParticipant> participants;
}