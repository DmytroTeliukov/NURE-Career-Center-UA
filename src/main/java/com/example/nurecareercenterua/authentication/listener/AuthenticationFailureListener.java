package com.example.nurecareercenterua.authentication.listener;

import com.example.nurecareercenterua.authentication.service.AuthenticationAttemptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFailureListener implements
        ApplicationListener<AuthenticationFailureBadCredentialsEvent> {


    private final AuthenticationAttemptService authenticationAttemptService;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        if (principal instanceof String) {
            String email = (String) event.getAuthentication().getPrincipal();
            log.info("Failure listener email [{}]", email);
            authenticationAttemptService.addAccountToLoginAttemptCache(email);
        }
    }
}
