package com.example.nurecareercenterua.security.authentication.listener;

import com.example.nurecareercenterua.domain.account.model.entity.Account;
import com.example.nurecareercenterua.security.authentication.model.AccountPrincipal;
import com.example.nurecareercenterua.security.authentication.service.AuthenticationAttemptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
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
