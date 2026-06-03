package com.tahir.where_did_my_money_go.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ResendEmailService implements EmailService {

    @Value("${app.resend.api-key}")
    private String apiKey;

    @Value("${app.email.from}")
    private String fromEmail;

    private final RestClient restClient = RestClient.builder()
            .baseUrl("https://api.resend.com")
            .build();

    @Override
    public void sendEmail(String to, String subject, String html) {

        restClient.post()
                .uri("/emails")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + apiKey)
                .body(Map.of(
                        "from", fromEmail,
                        "to", new String[] { to },
                        "subject", subject,
                        "html", html))
                .retrieve()
                .toBodilessEntity();
    }
}