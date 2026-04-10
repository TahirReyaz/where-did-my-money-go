package com.tahir.where_did_my_money_go.recurring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tahir.where_did_my_money_go.recurring.entity.RecurringPayment;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface RecurringPaymentRepository extends JpaRepository<RecurringPayment, UUID> {

    List<RecurringPayment> findByUserId(UUID userId);

    List<RecurringPayment> findByNextRunDateLessThanEqual(LocalDate date);
}