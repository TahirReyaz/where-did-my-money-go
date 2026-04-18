package com.tahir.where_did_my_money_go.expense.repository;

import org.springframework.data.jpa.repository.*;

import com.tahir.where_did_my_money_go.expense.entity.Expense;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

    List<Expense> findByUserId(UUID userId);

    @Query("""
            SELECT SUM(e.amount)
            FROM Expense e
            WHERE e.user.id = :userId
            AND e.createdAt BETWEEN :start AND :end
            """)
    BigDecimal getTotalSpending(UUID userId, LocalDateTime start, LocalDateTime end);

    @Query("""
            SELECT COUNT(e)
            FROM Expense e
            WHERE e.user.id = :userId
            AND e.createdAt BETWEEN :start AND :end
            """)
    Long getTransactionCount(UUID userId, LocalDateTime start, LocalDateTime end);

    @Query("""
            SELECT MAX(e.amount)
            FROM Expense e
            WHERE e.user.id = :userId
            AND e.createdAt BETWEEN :start AND :end
            """)
    BigDecimal getHighestExpense(UUID userId, LocalDateTime start, LocalDateTime end);

    @Query("""
            SELECT e.category.name, SUM(e.amount)
            FROM Expense e
            WHERE e.user.id = :userId
            AND e.createdAt BETWEEN :start AND :end
            GROUP BY e.category.name
            ORDER BY SUM(e.amount) DESC
            """)
    List<Object[]> getCategoryStats(UUID userId, LocalDateTime start, LocalDateTime end);

    @Query("""
            SELECT DATE(e.createdAt), SUM(e.amount)
            FROM Expense e
            WHERE e.user.id = :userId
            AND e.createdAt BETWEEN :start AND :end
            GROUP BY DATE(e.createdAt)
            ORDER BY DATE(e.createdAt)
            """)
    List<Object[]> getDailyTimeSeries(UUID userId, LocalDateTime start, LocalDateTime end);
}