package com.example.nurecareercenterua.authentication.listener;

import com.example.nurecareercenterua.authentication.model.AccountPrincipal;
import com.example.nurecareercenterua.authentication.service.AuthenticationAttemptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationSuccessListener implements
        ApplicationListener<AuthenticationSuccessEvent> {
    private final AuthenticationAttemptService authenticationAttemptService;


    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        if (principal instanceof AccountPrincipal) {
            AccountPrincipal account = (AccountPrincipal) event.getAuthentication().getPrincipal();
            log.info("Success listener email [{}]", account.getUsername());
            authenticationAttemptService.evictAccountFromLoginAttemptCache(account.getUsername());
        }
    }
}
