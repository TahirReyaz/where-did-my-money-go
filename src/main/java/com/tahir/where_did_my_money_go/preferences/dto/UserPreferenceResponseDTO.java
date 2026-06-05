package com.tahir.where_did_my_money_go.preferences.dto;

import com.tahir.where_did_my_money_go.preferences.enums.AnalyticsPeriod;
import com.tahir.where_did_my_money_go.preferences.enums.Theme;
import com.tahir.where_did_my_money_go.preferences.enums.WeekStartsOn;
import lombok.Data;

import java.time.LocalTime;
import java.util.UUID;

@Data
public class UserPreferenceResponseDTO {

    private UUID id;

    private Theme theme;

    private String currency;

    private WeekStartsOn weekStartsOn;

    private UUID defaultExpenseCategoryId;

    private String defaultExpenseCategoryName;

    private AnalyticsPeriod defaultAnalyticsPeriod;

    private Boolean includeGroupExpensesInAnalytics;

    private Boolean expenseReminderEnabled;

    private LocalTime expenseReminderTime;

    private Boolean emailNotifications;

    private Boolean groupNotifications;

    private Boolean hideAmounts;
}