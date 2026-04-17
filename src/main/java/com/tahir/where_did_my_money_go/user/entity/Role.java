package com.tahir.where_did_my_money_go.user.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ROLE_OWNER,
    ROLE_MODERATOR,
    ROLE_USER;

    @Override
    public String getAuthority() {
        return name();
    }
}