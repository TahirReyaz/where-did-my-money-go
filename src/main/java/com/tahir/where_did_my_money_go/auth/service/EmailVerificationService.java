package com.tahir.where_did_my_money_go.auth.service;

import java.util.UUID;

public interface EmailVerificationService {

    void generateToken(UUID userId);

    void verifyToken(String token);

    void resendVerification(UUID userId);
}