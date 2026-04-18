package com.tahir.where_did_my_money_go.stats.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TimeSeriesDTO {
    private LocalDate date;
    private BigDecimal totalAmount;
}