package com.tahir.where_did_my_money_go.group.repository;

import com.tahir.where_did_my_money_go.group.entity.GroupExpenseParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface GroupExpenseParticipantRepository extends JpaRepository<GroupExpenseParticipant, UUID> {

    @Query("""
                SELECT COALESCE(SUM(p.shareAmount - COALESCE(p.paidAmount, 0)), 0)
                FROM GroupExpenseParticipant p
                WHERE p.user.id = :userId AND p.groupExpense.group.id = :groupId
            """)
    BigDecimal getNetBalanceForUser(UUID userId, UUID groupId);

    @Query("""
                SELECT p FROM GroupExpenseParticipant p
                WHERE p.groupExpense.group.id = :groupId
            """)
    List<GroupExpenseParticipant> findAllByGroupId(UUID groupId);
}