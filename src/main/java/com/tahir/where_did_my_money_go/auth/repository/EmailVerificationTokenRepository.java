package com.tahir.where_did_my_money_go.auth.repository;

import com.tahir.where_did_my_money_go.auth.entity.EmailVerificationToken;
import com.tahir.where_did_my_money_go.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, UUID> {

    Optional<EmailVerificationToken> findByToken(String token);

    Optional<EmailVerificationToken> findByUser(User user);

    void deleteByUser(User user);
}