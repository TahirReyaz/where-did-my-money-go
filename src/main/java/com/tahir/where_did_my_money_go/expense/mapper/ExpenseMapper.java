package com.tahir.where_did_my_money_go.expense.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tahir.where_did_my_money_go.expense.dto.ExpenseCreateRequest;
import com.tahir.where_did_my_money_go.expense.dto.ExpenseResponse;
import com.tahir.where_did_my_money_go.expense.entity.Expense;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trip", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "recurringPayment", ignore = true)
    @Mapping(target = "groupExpense", ignore = true)
    @Mapping(target = "category", ignore = true)
    Expense toEntity(ExpenseCreateRequest request);

    @Mapping(source = "category.name", target = "categoryName")
    ExpenseResponse toResponse(Expense expense);
}