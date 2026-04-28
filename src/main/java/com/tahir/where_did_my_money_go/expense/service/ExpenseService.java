package com.tahir.where_did_my_money_go.expense.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.tahir.where_did_my_money_go.common.exception.ResourceNotFoundException;
import com.tahir.where_did_my_money_go.common.exception.UnauthorizedException;
import com.tahir.where_did_my_money_go.expense.dto.ExpenseCreateRequest;
import com.tahir.where_did_my_money_go.expense.dto.ExpenseResponse;
import com.tahir.where_did_my_money_go.expense.dto.UpdateExpenseRequestDTO;
import com.tahir.where_did_my_money_go.expense.entity.Expense;
import com.tahir.where_did_my_money_go.expense.entity.ExpenseCategory;
import com.tahir.where_did_my_money_go.expense.mapper.ExpenseMapper;
import com.tahir.where_did_my_money_go.expense.repository.ExpenseCategoryRepository;
import com.tahir.where_did_my_money_go.expense.repository.ExpenseRepository;
import com.tahir.where_did_my_money_go.user.entity.User;
import com.tahir.where_did_my_money_go.user.repository.UserRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository repo;
    private final ExpenseCategoryRepository categoryRepository;
    private final UserRepository userRepo;
    private final ExpenseMapper mapper;

    public ExpenseResponse create(UUID userId, ExpenseCreateRequest req) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        ExpenseCategory category;

        if (req.getCategoryId() != null) {
            category = categoryRepository.findById(req.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        } else {
            category = categoryRepository.findByName("others")
                    .orElseGet(() -> categoryRepository.save(
                            ExpenseCategory.builder()
                                    .name("others")
                                    .build()));
        }

        Expense expense = mapper.toEntity(req);
        expense.setUser(user);
        expense.setCategory(category);

        return mapper.toResponse(repo.save(expense));
    }

    public List<ExpenseResponse> getUserExpenses(UUID userId) {
        return repo.findByUserId(userId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional
    public void updateExpense(UUID expenseId, UpdateExpenseRequestDTO request, UUID userId) {

        Expense expense = repo.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        if (!expense.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("You can only update your own expenses");
        }

        expense.setAmount(request.getAmount());
        expense.setDescription(request.getDescription());
        expense.setExpenseDate(request.getExpenseDate());

        if (request.getCategoryId() != null) {
            ExpenseCategory category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            expense.setCategory(category);
        }

        repo.save(expense);
    }

}