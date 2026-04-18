package com.tahir.where_did_my_money_go.auth.service.impl;

import com.tahir.where_did_my_money_go.auth.entity.EmailVerificationToken;
import com.tahir.where_did_my_money_go.auth.repository.EmailVerificationTokenRepository;
import com.tahir.where_did_my_money_go.auth.service.EmailVerificationService;
import com.tahir.where_did_my_money_go.common.exception.InvalidTokenException;
import com.tahir.where_did_my_money_go.user.entity.User;
import com.tahir.where_did_my_money_go.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private final EmailVerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void generateToken(UUID userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidTokenException("User not found"));

        tokenRepository.deleteByUserId(userId);

        EmailVerificationToken token = EmailVerificationToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiryDate(LocalDateTime.now().plusHours(24))
                .build();

        tokenRepository.save(token);
    }

    @Override
    @Transactional
    public void verifyToken(String tokenValue) {

        EmailVerificationToken token = tokenRepository.findByToken(tokenValue)
                .orElseThrow(() -> new InvalidTokenException("Invalid token"));

        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException("Token expired");
        }

        User user = token.getUser();
        user.setVerified(true);

        userRepository.save(user);
        tokenRepository.delete(token);
    }

    @Override
    @Transactional
    public void resendVerification(UUID userId) {
        generateToken(userId);
    }
}