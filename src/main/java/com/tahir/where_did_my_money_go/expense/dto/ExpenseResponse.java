package com.tahir.where_did_my_money_go.expense.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseResponse {

    private UUID id;
    private BigDecimal amount;
    private String categoryName;
    private String description;
    private LocalDate expenseDate;
}