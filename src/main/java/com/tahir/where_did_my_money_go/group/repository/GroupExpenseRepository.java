package com.tahir.where_did_my_money_go.group.repository;

import com.tahir.where_did_my_money_go.group.entity.ExpenseGroup;
import com.tahir.where_did_my_money_go.group.entity.GroupExpense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GroupExpenseRepository extends JpaRepository<GroupExpense, UUID> {
    // Custom query to find expenses by group ID
    List<GroupExpense> findByGroup(ExpenseGroup group);
}