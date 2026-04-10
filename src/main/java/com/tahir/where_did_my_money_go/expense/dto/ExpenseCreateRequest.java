package com.tahir.where_did_my_money_go.expense.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseCreateRequest {

    @NotNull
    @Positive
    private java.math.BigDecimal amount;

    @NotNull
    private java.time.LocalDate expenseDate;

    private String description;

    private java.util.UUID categoryId;
    private java.util.UUID tripId;
}