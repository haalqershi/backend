package com.hesham.backend.security;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 604_800_000; // expiration days in Milliseconds for the token - 7 days
    public static final String TOKEN_PREFIX = "Bear ";
    public static final String JWT_TOKEN_HEADER ="Jwt-Token";
    public static final String PRODUCT_MANAGER ="product manager, llc";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String AUTHORITIES = "Authorities";
    public static final String FORBIDDEN_MESSAGE ="You must log in to access the page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have the permission to access the page";
    //public static final String[] PUBLIC_URLS = {"/user/login", "/user/register", "/user/resetpassword/**"};
    public static final String[] PUBLIC_URLS = {"**"};



}
