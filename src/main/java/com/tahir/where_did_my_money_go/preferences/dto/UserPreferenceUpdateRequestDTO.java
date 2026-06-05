package com.tahir.where_did_my_money_go.preferences.dto;

import com.tahir.where_did_my_money_go.preferences.enums.AnalyticsPeriod;
import com.tahir.where_did_my_money_go.preferences.enums.Theme;
import com.tahir.where_did_my_money_go.preferences.enums.WeekStartsOn;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalTime;
import java.util.UUID;

@Data
public class UserPreferenceUpdateRequestDTO {

    private Theme theme;

    @Size(max = 10, message = "Currency cannot exceed 10 characters")
    private String currency;

    private WeekStartsOn weekStartsOn;

    private UUID defaultExpenseCategoryId;

    private AnalyticsPeriod defaultAnalyticsPeriod;

    private Boolean includeGroupExpensesInAnalytics;

    private Boolean expenseReminderEnabled;

    private LocalTime expenseReminderTime;

    private Boolean emailNotifications;

    private Boolean groupNotifications;

    private Boolean hideAmounts;
}