package com.tahir.where_did_my_money_go.group.repository;

import com.tahir.where_did_my_money_go.group.entity.ExpenseGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExpenseGroupRepository extends JpaRepository<ExpenseGroup, UUID> {
}