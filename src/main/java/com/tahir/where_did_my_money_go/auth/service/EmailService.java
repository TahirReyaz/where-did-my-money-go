package com.tahir.where_did_my_money_go.auth.service;

public interface EmailService {

    void sendEmail(String to, String subject, String html);
}