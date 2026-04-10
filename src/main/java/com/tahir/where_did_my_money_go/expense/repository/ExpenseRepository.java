package com.tahir.where_did_my_money_go.expense.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import com.tahir.where_did_my_money_go.expense.entity.Expense;

import java.util.*;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

    List<Expense> findByUserId(UUID userId);

    @Query("""
        SELECT FUNCTION('DATE_TRUNC', 'month', e.expenseDate), SUM(e.amount)
        FROM Expense e
        WHERE e.user.id = :userId
        GROUP BY FUNCTION('DATE_TRUNC', 'month', e.expenseDate)
        ORDER BY FUNCTION('DATE_TRUNC', 'month', e.expenseDate)
    """)
    List<Object[]> getMonthlySpending(@Param("userId") UUID userId);

    @Query("""
        SELECT c.name, SUM(e.amount)
        FROM Expense e
        JOIN e.category c
        WHERE e.user.id = :userId
        GROUP BY c.name
    """)
    List<Object[]> getCategorySpending(@Param("userId") UUID userId);
}