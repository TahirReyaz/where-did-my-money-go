package com.tahir.where_did_my_money_go.auth.service;

import com.tahir.where_did_my_money_go.auth.dto.VerifyEmailResponseDTO;
import com.tahir.where_did_my_money_go.auth.entity.EmailVerificationToken;
import com.tahir.where_did_my_money_go.auth.repository.EmailVerificationTokenRepository;
import com.tahir.where_did_my_money_go.auth.security.CustomUserDetails;
import com.tahir.where_did_my_money_go.common.exception.BadRequestException;
import com.tahir.where_did_my_money_go.common.exception.ResourceNotFoundException;
import com.tahir.where_did_my_money_go.user.entity.User;
import com.tahir.where_did_my_money_go.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final EmailVerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    private static final int TOKEN_BYTES = 32;

    @Transactional
    public void createAndSendVerificationToken(User user) {

        tokenRepository.deleteByUser(user);

        String token = generateSecureToken();

        EmailVerificationToken verificationToken = EmailVerificationToken.builder()
                .token(token)
                .user(user)
                .expiresAt(LocalDateTime.now().plusHours(24))
                .used(false)
                .build();

        tokenRepository.save(verificationToken);

        String verificationLink = frontendUrl + "/verify-email?token=" + token;

        String html = buildVerificationEmail(user.getEmail(), verificationLink);

        emailService.sendEmail(
                user.getEmail(),
                "Verify your email",
                html);
    }

    @Transactional
    public VerifyEmailResponseDTO verifyEmail(String token) {

        EmailVerificationToken verificationToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid verification token"));

        if (verificationToken.isUsed()) {
            throw new BadRequestException("Verification token already used");
        }

        if (verificationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Verification token expired");
        }

        User user = verificationToken.getUser();

        user.setVerified(true);

        verificationToken.setUsed(true);

        userRepository.save(user);
        tokenRepository.save(verificationToken);

        return VerifyEmailResponseDTO.builder()
                .message("Email verified successfully")
                .build();
    }

    @Transactional
    public void resendVerification(UserDetails userDetails) {

        User user = userRepository.findById(
                ((CustomUserDetails) userDetails).getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.isVerified()) {
            throw new BadRequestException("Email already verified");
        }

        createAndSendVerificationToken(user);
    }

    private String generateSecureToken() {

        byte[] bytes = new byte[TOKEN_BYTES];

        new SecureRandom().nextBytes(bytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }

    private String buildVerificationEmail(String username, String verificationLink) {

        return """
                <html>
                <body style="font-family: Arial, sans-serif;">
                    <h2>Verify Your Email</h2>
                    <p>Hello %s,</p>
                    <p>Please verify your email address by clicking below:</p>
                    <a href="%s"
                       style="
                            display:inline-block;
                            padding:12px 20px;
                            background:#2563eb;
                            color:white;
                            text-decoration:none;
                            border-radius:6px;
                       ">
                        Verify Email
                    </a>
                    <p>This link expires in 24 hours.</p>
                </body>
                </html>
                """.formatted(username, verificationLink);
    }
}