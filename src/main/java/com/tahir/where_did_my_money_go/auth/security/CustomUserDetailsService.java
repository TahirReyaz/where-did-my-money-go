package com.tahir.where_did_my_money_go.auth.security;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

public interface CustomUserDetailsService extends UserDetailsService {

    CustomUserDetails loadUserById(UUID id);
}