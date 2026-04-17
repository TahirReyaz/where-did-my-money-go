package com.tahir.where_did_my_money_go.expense.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.tahir.where_did_my_money_go.expense.dto.ExpenseCreateRequest;
import com.tahir.where_did_my_money_go.expense.dto.ExpenseResponse;
import com.tahir.where_did_my_money_go.expense.entity.Expense;
import com.tahir.where_did_my_money_go.expense.mapper.ExpenseMapper;
import com.tahir.where_did_my_money_go.expense.repository.ExpenseRepository;
import com.tahir.where_did_my_money_go.user.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository repo;
    private final UserRepository userRepo;
    private final ExpenseMapper mapper;

    public ExpenseResponse create(UUID userId, ExpenseCreateRequest req) {

        var user = userRepo.findById(userId).orElseThrow();

        Expense expense = mapper.toEntity(req);
        expense.setUser(user);

        repo.save(expense);

        return mapper.toResponse(expense);
    }

    public List<ExpenseResponse> getUserExpenses(UUID userId) {
        return repo.findByUserId(userId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}