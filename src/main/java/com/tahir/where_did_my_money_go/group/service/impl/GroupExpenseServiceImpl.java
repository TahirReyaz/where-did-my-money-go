package com.tahir.where_did_my_money_go.group.service.impl;

import com.tahir.where_did_my_money_go.common.exception.ResourceNotFoundException;
import com.tahir.where_did_my_money_go.expense.entity.Expense;
import com.tahir.where_did_my_money_go.expense.entity.ExpenseCategory;
import com.tahir.where_did_my_money_go.expense.repository.ExpenseCategoryRepository;
import com.tahir.where_did_my_money_go.expense.repository.ExpenseRepository;
import com.tahir.where_did_my_money_go.group.dto.CreateGroupExpenseRequest;
import com.tahir.where_did_my_money_go.group.entity.*;
import com.tahir.where_did_my_money_go.group.repository.ExpenseGroupRepository;
import com.tahir.where_did_my_money_go.group.repository.GroupExpenseRepository;
import com.tahir.where_did_my_money_go.group.service.GroupExpenseService;
import com.tahir.where_did_my_money_go.user.entity.User;
import com.tahir.where_did_my_money_go.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupExpenseServiceImpl implements GroupExpenseService {

        private final ExpenseGroupRepository groupRepository;
        private final GroupExpenseRepository groupExpenseRepository;
        private final ExpenseRepository expenseRepository;
        private final UserRepository userRepository;
        private final ExpenseCategoryRepository categoryRepository;

        @Override
        @Transactional
        public void createGroupExpense(CreateGroupExpenseRequest request, UUID userId) {

                User creator = userRepository.findById(userId)
                                .orElseThrow(() -> new IllegalArgumentException("User not found"));

                ExpenseGroup group = groupRepository.findById(request.getGroupId())
                                .orElseThrow(() -> new IllegalArgumentException("Group not found"));

                ExpenseCategory category;

                if (request.getCategoryId() != null) {
                        category = categoryRepository.findById(request.getCategoryId())
                                        .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
                } else {
                        category = categoryRepository.findByName("others")
                                        .orElseGet(() -> categoryRepository.save(
                                                        ExpenseCategory.builder()
                                                                        .name("others")
                                                                        .build()));
                }

                GroupExpense groupExpense = GroupExpense.builder()
                                .group(group)
                                .createdBy(creator)
                                .totalAmount(request.getTotalAmount())
                                .description(request.getDescription())
                                .expenseDate(request.getExpenseDate())
                                .category(category)
                                .build();

                List<GroupExpenseParticipant> participants = request.getParticipants().stream().map(p -> {

                        User user = userRepository.findById(p.getUserId()).orElseThrow();

                        Expense expense = Expense.builder()
                                        .user(user)
                                        .amount(p.getShareAmount())
                                        .expenseDate(request.getExpenseDate())
                                        .description(request.getDescription())
                                        .category(category)
                                        .build();

                        expenseRepository.save(expense);

                        return GroupExpenseParticipant.builder()
                                        .groupExpense(groupExpense)
                                        .user(user)
                                        .shareAmount(p.getShareAmount())
                                        .paidAmount(user.getId().equals(creator.getId()) ? request.getTotalAmount()
                                                        : null)
                                        .expense(expense)
                                        .build();

                }).toList();

                groupExpense.setParticipants(participants);

                groupExpenseRepository.save(groupExpense);
        }
}