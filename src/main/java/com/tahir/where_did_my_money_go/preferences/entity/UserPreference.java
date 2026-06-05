package com.tahir.where_did_my_money_go.preferences.entity;

import com.tahir.where_did_my_money_go.common.entity.BaseEntity;
import com.tahir.where_did_my_money_go.expense.entity.ExpenseCategory;
import com.tahir.where_did_my_money_go.preferences.enums.AnalyticsPeriod;
import com.tahir.where_did_my_money_go.preferences.enums.Theme;
import com.tahir.where_did_my_money_go.preferences.enums.WeekStartsOn;
import com.tahir.where_did_my_money_go.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "user_preferences", indexes = {
        @Index(name = "idx_user_preferences_user", columnList = "user_id")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPreference extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Theme theme;

    @Column(nullable = false, length = 10)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WeekStartsOn weekStartsOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "default_expense_category_id")
    private ExpenseCategory defaultExpenseCategory;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnalyticsPeriod defaultAnalyticsPeriod;

    @Column(nullable = false)
    private Boolean includeGroupExpensesInAnalytics;

    @Column(nullable = false)
    private Boolean expenseReminderEnabled;

    private LocalTime expenseReminderTime;

    @Column(nullable = false)
    private Boolean emailNotifications;

    @Column(nullable = false)
    private Boolean groupNotifications;

    @Column(nullable = false)
    private Boolean hideAmounts;
}