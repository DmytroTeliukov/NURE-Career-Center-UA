package com.example.nurecareercenterua.domain.account.service.impl;

import com.example.nurecareercenterua.domain.account.exception.EmailAlreadyRegisteredException;
import com.example.nurecareercenterua.domain.account.exception.IllegalPasswordArgumentException;
import com.example.nurecareercenterua.domain.account.exception.PhoneAlreadyRegisteredException;
import com.example.nurecareercenterua.domain.account.mapper.AccountMapper;
import com.example.nurecareercenterua.domain.account.model.dto.AccountDto;
import com.example.nurecareercenterua.domain.account.model.entity.Account;
import com.example.nurecareercenterua.domain.account.model.enums.AccountRole;
import com.example.nurecareercenterua.domain.account.model.request.AccountOperationRequest;
import com.example.nurecareercenterua.domain.account.model.request.RegistrationAccount;
import com.example.nurecareercenterua.domain.account.model.response.CreatedAccount;
import com.example.nurecareercenterua.domain.account.repository.AccountRepository;
import com.example.nurecareercenterua.domain.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public CreatedAccount register(RegistrationAccount registrationAccount) {
        validateRegistrationData(registrationAccount);

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

    @Override
    public List<AccountDto> findAllByRole(AccountRole role, int page, int size) {
        return accountRepository.findAllByAccountRole(role, PageRequest.of(page, size))
                .stream()
                .map(accountMapper::fromAccount)
                .collect(Collectors.toList());
    }

    @Override
    public void changeAccountAccess(AccountOperationRequest accountOperationRequest) {
        accountRepository.changeAccessStatus(accountOperationRequest.email(),
                accountOperationRequest.operation().getContent());
    }

    private void validateRegistrationData(RegistrationAccount registrationAccount) {
        if (accountRepository.existsAccountByEmail(registrationAccount.getEmail())) {
            throw new EmailAlreadyRegisteredException();
        } else if (accountRepository.existsAccountByPhone(registrationAccount.getPhone())) {
            throw new PhoneAlreadyRegisteredException();
        } else if (!registrationAccount.getConfirmedPassword().equals(registrationAccount.getPassword())) {
            throw new IllegalPasswordArgumentException();
        }
    }
}
