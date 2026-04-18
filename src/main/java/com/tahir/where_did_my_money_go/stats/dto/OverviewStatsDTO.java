package com.tahir.where_did_my_money_go.stats.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OverviewStatsDTO {

    private BigDecimal totalSpending;
    private BigDecimal averageDailySpending;
    private BigDecimal highestExpense;
    private Long totalTransactions;
}