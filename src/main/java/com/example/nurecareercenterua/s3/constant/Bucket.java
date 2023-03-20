package com.example.nurecareercenterua.s3.constant;

public class Bucket {
    private static final String HTTPS = "https://";
    public static final String BUCKET_NAME = "nure-career-center-ua";
    public static final String AMAZON_S3 = ".s3.amazonaws.com";

    public static final String ACCOUNT_FOLDER = "/accounts";
    public static final String ACCOUNT_PATH = BUCKET_NAME + ACCOUNT_FOLDER;
    public static final String ACCOUNT_LINK = HTTPS + BUCKET_NAME + AMAZON_S3 + ACCOUNT_FOLDER;
    public static final String ACCOUNT_DEFAULT_IMAGE = "/default_account_avatar.png";
}
