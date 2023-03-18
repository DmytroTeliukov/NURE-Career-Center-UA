package com.example.nurecareercenterua.domain.account.service.impl;

import com.example.nurecareercenterua.domain.account.exception.AccountNotFoundException;
import com.example.nurecareercenterua.domain.account.exception.EmailAlreadyRegisteredException;
import com.example.nurecareercenterua.domain.account.exception.IllegalPasswordArgumentException;
import com.example.nurecareercenterua.domain.account.exception.PhoneAlreadyRegisteredException;
import com.example.nurecareercenterua.domain.account.mapper.AccountMapper;
import com.example.nurecareercenterua.domain.account.model.dto.AccountDto;
import com.example.nurecareercenterua.domain.account.model.entity.Account;
import com.example.nurecareercenterua.domain.account.model.enums.AccountOperation;
import com.example.nurecareercenterua.domain.account.model.enums.AccountRole;
import com.example.nurecareercenterua.domain.account.model.dto.request.AccountOperationRequest;
import com.example.nurecareercenterua.domain.account.model.dto.request.ChangePasswordRequest;
import com.example.nurecareercenterua.domain.account.model.dto.request.RegistrationAccount;
import com.example.nurecareercenterua.domain.account.model.dto.request.response.CreatedAccount;
import com.example.nurecareercenterua.domain.account.repository.AccountRepository;
import com.example.nurecareercenterua.domain.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository,
                              AccountMapper accountMapper,
                              PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
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
                .password(passwordEncoder.encode(registrationAccount.getPassword()))
                .role(registrationAccount.getRole())
                .joined(new Date())
                .lastUpdated(new Date())
                .lastLogin(new Date())
                .isActive(true)
                .isNonLocked(true)
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

    @Transactional(readOnly = true)
    @Override
    public List<AccountDto> findAllByRole(AccountRole role, int page, int size) {
        return accountRepository.findAllByAccountRole(role, PageRequest.of(page, size))
                .stream()
                .map(accountMapper::toAccountDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void changeAccountAccess(AccountOperationRequest accountOperationRequest) {
        boolean accountAccess = accountOperationRequest.operation().equals(AccountOperation.RESTORE);
        accountRepository.changeAccessStatus(accountOperationRequest.email(), accountAccess);
    }

    @Transactional
    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        String email = changePasswordRequest.email();
        Account foundAccount = getAccountEntityByEmail(email);

        validateChangePasswordData(foundAccount.getPassword(), changePasswordRequest);

        accountRepository.changePassword(email, changePasswordRequest.newPassword());
    }

    @Override
    public AccountDto findAccountByEmail(String email) {
        Account account = getAccountEntityByEmail(email);
        return accountMapper.toAccountDto(account);
    }

    private Account getAccountEntityByEmail(String email) {
        return accountRepository.findAccountByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException("Account with email [" + email + "] does not exists!"));
    }

    private void validateRegistrationData(RegistrationAccount registrationAccount) {
        if (accountRepository.existsAccountByEmail(registrationAccount.getEmail())) {
            throw new EmailAlreadyRegisteredException();
        } else if (accountRepository.existsAccountByPhone(registrationAccount.getPhone())) {
            throw new PhoneAlreadyRegisteredException();
        } else if (!registrationAccount.getConfirmedPassword().equals(registrationAccount.getPassword())) {
            throw new IllegalPasswordArgumentException("Password does not match to confirmed!");
        }
    }

    private void validateChangePasswordData(String password, ChangePasswordRequest request) {
        if (!password.equals(request.currentPassword())) {
            throw new IllegalPasswordArgumentException("Inputted password does not match to current");
        } else if (!request.newPassword().equals(request.confirmedNewPassword())) {
            throw new IllegalPasswordArgumentException("New password does not match to confirmed!");
        }
    }
}
