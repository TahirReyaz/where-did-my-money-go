package com.tahir.where_did_my_money_go.group.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.tahir.where_did_my_money_go.common.entity.BaseEntity;
import com.tahir.where_did_my_money_go.user.entity.User;

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

    @Column(name = "total_amount", precision = 14, scale = 2)
    private BigDecimal totalAmount;

    private String description;

    private LocalDate expenseDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payer_id")
    private User payer;

    @OneToMany(mappedBy = "groupExpense", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupExpenseParticipant> participants;
}