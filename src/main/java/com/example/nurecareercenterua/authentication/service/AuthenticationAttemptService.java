package com.example.nurecareercenterua.authentication.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class AuthenticationAttemptService {
    private static final int MAXIMUM_NUMBER_ATTEMPTS = 5;
    private static final int ATTEMPT_INCREMENT = 1;
    private static final int START_NUMBER_ATTEMPTS = 0;
    private final LoadingCache<String, Integer> loginAttemptCache;

    public AuthenticationAttemptService() {
        super();
        loginAttemptCache = CacheBuilder.newBuilder()
                .expireAfterWrite(15, TimeUnit.MINUTES)
                .maximumSize(100)
                .build(new CacheLoader<>() {
                    @Override
                    public Integer load(String key) throws Exception {
                        return 0;
                    }
                });
    }

    public void evictAccountFromLoginAttemptCache(String email) {
        log.info("Logged in email [{}]", email);
        loginAttemptCache.invalidate(email);
    }

    public void addAccountToLoginAttemptCache(String email) {
        int attempts = ATTEMPT_INCREMENT + loginAttemptCache.asMap().getOrDefault(email, START_NUMBER_ATTEMPTS);
        log.info("Attempt {} for email [{}]",  attempts, email);
        loginAttemptCache.put(email, attempts);
    }

    public boolean hasExceededMaxAttempts(String email) {
        return loginAttemptCache.asMap()
                .getOrDefault(email, START_NUMBER_ATTEMPTS) >= MAXIMUM_NUMBER_ATTEMPTS;
    }
 }
