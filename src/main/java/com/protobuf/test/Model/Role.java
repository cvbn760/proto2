package com.protobuf.test.Model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    ADMIN,
    ANONIMUS;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
