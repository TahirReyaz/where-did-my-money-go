package com.tahir.where_did_my_money_go.stats.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryStatsDTO {

    private String categoryName;
    private BigDecimal totalAmount;
    private Double percentage;
}