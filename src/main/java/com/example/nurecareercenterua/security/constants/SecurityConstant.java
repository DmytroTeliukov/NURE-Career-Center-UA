package com.example.nurecareercenterua.security.constants;

public class SecurityConstant {
    public static final long EXPIRATION_TIME = 432_000_000;
    public static final String TOKEN_HEADER = "Bearer ";
    public static final String JWT_TOKEN_HEADER_NAME = "Jwt-Token";
    public static final String TOKEN_ISSUE = "NURE Center Career UA";
    public static final String TOKEN_NOT_VERIFIED_MESSAGE = "The token cannot be verified";
    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";
    public static final String [] PUBLIC_URLS = {"/login"};
    public static final String ROLE = "Role";
}
