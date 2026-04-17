package com.tahir.where_did_my_money_go.expense.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseCategoryCreateRequest {

    @NotBlank
    @Size(max = 100)
    private String name;
}