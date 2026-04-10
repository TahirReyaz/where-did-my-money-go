package com.tahir.where_did_my_money_go.expense.mapper;

import org.mapstruct.Mapper;
import com.tahir.where_did_my_money_go.expense.dto.ExpenseCreateRequest;
import com.tahir.where_did_my_money_go.expense.dto.ExpenseResponse;
import com.tahir.where_did_my_money_go.expense.entity.Expense;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    Expense toEntity(ExpenseCreateRequest request);

    ExpenseResponse toResponse(Expense expense);
}