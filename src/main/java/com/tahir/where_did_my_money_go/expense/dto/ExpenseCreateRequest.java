package com.tahir.where_did_my_money_go.expense.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

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
    private BigDecimal amount;

    @NotNull
    private LocalDate expenseDate;

    @NotNull
    @Size(min = 2, max = 255)
    private String description;

    private UUID categoryId;
    private UUID tripId;
}