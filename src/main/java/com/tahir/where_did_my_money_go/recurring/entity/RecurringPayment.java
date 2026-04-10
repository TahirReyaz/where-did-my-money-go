package com.tahir.where_did_my_money_go.recurring.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.tahir.where_did_my_money_go.common.entity.BaseEntity;
import com.tahir.where_did_my_money_go.expense.entity.ExpenseCategory;
import com.tahir.where_did_my_money_go.user.entity.User;

@Entity
@Table(name = "recurring_payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecurringPayment extends BaseEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(precision = 14, scale = 2, nullable = false)
    private BigDecimal amount;

    private String frequency;

    @Column(name = "interval_value")
    private Integer intervalValue;

    private LocalDate nextRunDate;

    private LocalDate lastRunDate;

    final private Boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private ExpenseCategory category;
}