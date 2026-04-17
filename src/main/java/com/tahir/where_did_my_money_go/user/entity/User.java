package com.tahir.where_did_my_money_go.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tahir.where_did_my_money_go.common.entity.BaseEntity;
import com.tahir.where_did_my_money_go.expense.entity.Expense;
import com.tahir.where_did_my_money_go.recurring.entity.RecurringPayment;
import com.tahir.where_did_my_money_go.trip.entity.Trip;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @Column(name = "password_hash")
    private String passwordHash;

    // Relationships

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Expense> expenses;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<RecurringPayment> recurringPayments;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Trip> trips;
}