package com.tahir.where_did_my_money_go.group.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

@Data
public class CreateGroupExpenseRequest {

    @NotNull(message = "Group ID is required")
    private UUID groupId;

    @NotNull(message = "Total amount is required")
    @Positive(message = "Total amount must be greater than 0")
    private BigDecimal totalAmount;

    @NotBlank(message = "Description is required")
    private String description;

    @NotEmpty(message = "Participants list cannot be empty")
    private List<ParticipantDTO> participants;

    @NotNull(message = "Expense date is required")
    @PastOrPresent(message = "Expense date cannot be in the future")
    private LocalDate expenseDate;

    @NotNull(message = "Category ID is required")
    private UUID categoryId;

    @Data
    public static class ParticipantDTO {

        @NotNull(message = "User ID is required")
        private UUID userId;

        @NotNull(message = "Share amount is required")
        @Positive(message = "Share must be greater than 0")
        private BigDecimal shareAmount;
    }
}