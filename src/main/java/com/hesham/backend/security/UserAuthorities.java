package com.hesham.backend.security;

public class UserAuthorities {
    public static final String[] ADMIN_AUTHORITIES = {"user:read", "user:update", "user:delete", "user:create"};
    public static final String[] USER_AUTHORITIES = {"user:read", "user:update"};
}
