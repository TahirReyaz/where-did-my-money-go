package com.tahir.where_did_my_money_go.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tahir.where_did_my_money_go.user.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}