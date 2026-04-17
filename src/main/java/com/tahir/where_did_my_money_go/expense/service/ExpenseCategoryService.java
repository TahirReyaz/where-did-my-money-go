package com.tahir.where_did_my_money_go.expense.service;

import com.tahir.where_did_my_money_go.expense.dto.ExpenseCategoryCreateRequest;
import com.tahir.where_did_my_money_go.expense.dto.ExpenseCategoryResponse;

import java.util.List;
import java.util.UUID;

public interface ExpenseCategoryService {

    List<ExpenseCategoryResponse> getAll();

    ExpenseCategoryResponse create(ExpenseCategoryCreateRequest request);

    ExpenseCategoryResponse update(UUID id, ExpenseCategoryCreateRequest request);

    void delete(UUID id);
}