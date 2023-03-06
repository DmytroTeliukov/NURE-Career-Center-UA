package com.example.nurecareercenterua.domain.account.service.impl;

import com.example.nurecareercenterua.domain.account.exception.EmailAlreadyRegisteredException;
import com.example.nurecareercenterua.domain.account.exception.IllegalPasswordArgumentException;
import com.example.nurecareercenterua.domain.account.exception.PhoneAlreadyRegisteredException;
import com.example.nurecareercenterua.domain.account.model.entity.Account;
import com.example.nurecareercenterua.domain.account.model.enums.AccountRole;
import com.example.nurecareercenterua.domain.account.model.request.RegistrationAccount;
import com.example.nurecareercenterua.domain.account.model.response.CreatedAccount;
import com.example.nurecareercenterua.domain.account.repository.AccountRepository;
import com.example.nurecareercenterua.domain.account.service.AccountService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public CreatedAccount register(RegistrationAccount registrationAccount) {
        if (accountRepository.existsAccountByEmail(registrationAccount.getEmail())) {
            throw new EmailAlreadyRegisteredException();
        } else if (accountRepository.existsAccountByPhone(registrationAccount.getPhone())) {
            throw new PhoneAlreadyRegisteredException();
        } else if (!registrationAccount.getConfirmedPassword().equals(registrationAccount.getPassword())) {
            throw new IllegalPasswordArgumentException();
        }

        Account account = Account.builder()
                .id(UUID.randomUUID())
                .lastname(registrationAccount.getLastname())
                .firstname(registrationAccount.getFirstname())
                .patronymic(registrationAccount.getPatronymic())
                .phone(registrationAccount.getPhone())
                .email(registrationAccount.getEmail())
                .password(registrationAccount.getPassword())
                .role(registrationAccount.getRole())
                .joined(new Date())
                .lastUpdated(new Date())
                .lastLogin(new Date())
                .isActive(true)
                .build();

        Account createdAccount = accountRepository.save(account);

        return new CreatedAccount(createdAccount.getId(),
                createdAccount.getLastname(),
                createdAccount.getFirstname(),
                createdAccount.getPatronymic(),
                createdAccount.getEmail(),
                createdAccount.getPhone(),
                createdAccount.getRole(),
                createdAccount.getJoined());
    }
}
