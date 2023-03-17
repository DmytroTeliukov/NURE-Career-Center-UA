package com.example.nurecareercenterua.security.authentication.service;

import com.example.nurecareercenterua.domain.account.model.entity.Account;
import com.example.nurecareercenterua.domain.account.repository.AccountRepository;
import com.example.nurecareercenterua.security.authentication.model.AccountPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;


@Slf4j
@Service
@RequiredArgsConstructor
public class AccountDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final AuthenticationAttemptService authenticationAttemptService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findAccountByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Account not found by email [" + email + "]"));

        validateLoginAttempt(account);
        account.setLastLogin(new Date());
        accountRepository.save(account);
        log.info("Account: {}", account);
        return new AccountPrincipal(account);
    }

    private void validateLoginAttempt(Account account) {
        if (account.getIsNonLocked()) {
            account.setIsNonLocked(!authenticationAttemptService.hasExceededMaxAttempts(account.getEmail()));
        } else {
            authenticationAttemptService.evictAccountFromLoginAttemptCache(account.getEmail());
        }
    }
}
