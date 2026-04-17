package com.tahir.where_did_my_money_go.expense.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tahir.where_did_my_money_go.expense.entity.ExpenseCategory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, UUID> {

    List<ExpenseCategory> findByUserId(UUID userId);

    boolean existsByUserIdAndName(UUID userId, String name);

    Optional<ExpenseCategory> findByName(String name);
}