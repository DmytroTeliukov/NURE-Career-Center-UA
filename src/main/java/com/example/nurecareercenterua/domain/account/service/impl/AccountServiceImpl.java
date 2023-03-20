package com.example.nurecareercenterua.domain.account.service.impl;

import com.example.nurecareercenterua.domain.account.exception.AccountNotFoundException;
import com.example.nurecareercenterua.domain.account.exception.EmailAlreadyRegisteredException;
import com.example.nurecareercenterua.domain.account.exception.IllegalPasswordArgumentException;
import com.example.nurecareercenterua.domain.account.exception.PhoneAlreadyRegisteredException;
import com.example.nurecareercenterua.domain.account.mapper.AccountMapper;
import com.example.nurecareercenterua.domain.account.model.dto.AccountDto;
import com.example.nurecareercenterua.domain.account.model.dto.request.ChangeAccountData;
import com.example.nurecareercenterua.domain.account.model.entity.Account;
import com.example.nurecareercenterua.domain.account.model.enums.AccountOperation;
import com.example.nurecareercenterua.domain.account.model.enums.AccountRole;
import com.example.nurecareercenterua.domain.account.model.dto.request.AccountOperationRequest;
import com.example.nurecareercenterua.domain.account.model.dto.request.ChangePasswordRequest;
import com.example.nurecareercenterua.domain.account.model.dto.request.RegistrationAccount;
import com.example.nurecareercenterua.domain.account.model.dto.response.CreatedAccount;
import com.example.nurecareercenterua.domain.account.repository.AccountRepository;
import com.example.nurecareercenterua.domain.account.service.AccountService;
import com.example.nurecareercenterua.s3.constant.Bucket;
import com.example.nurecareercenterua.s3.service.FileStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;


import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;
    private final FileStoreService fileStoreService;

    @Transactional
    @Override
    public CreatedAccount register(RegistrationAccount registrationAccount, MultipartFile image) {
        validateRegistrationData(registrationAccount);
        String photoUrl = uploadImage(image);

        Account account = Account.builder()
                .id(UUID.randomUUID())
                .photoUrl(photoUrl)
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
                createdAccount.getPhotoUrl(),
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

    @Transactional
    @Override
    public void changeAccountData(String email, ChangeAccountData data) {
        if (data == null) {
            return;
        }

        Account account = getAccountEntityByEmail(email);
        boolean hasUpdated = false;

        log.info("Old account data: {}", account);
        log.info("Change data: {}", data);

        if (StringUtils.hasText(data.phone()) &&
                !account.getPhone().equals(data.phone()) &&
                !accountRepository.existsAccountByPhone(data.phone())) {
            account.setPhone(data.phone());
            hasUpdated = true;
        }

        if (StringUtils.hasText(data.firstname()) && !account.getFirstname().equals(data.firstname())) {
            account.setFirstname(data.firstname());
            hasUpdated = true;
        }

        if (StringUtils.hasText(data.lastname()) && !account.getLastname().equals(data.lastname())) {
            account.setLastname(data.lastname());
            hasUpdated = true;
        }

        if (StringUtils.hasText(data.patronymic()) && !account.getPatronymic().equals(data.patronymic())) {
            account.setPatronymic(data.patronymic());
            hasUpdated = true;
        }

        if (hasUpdated) {
            account.setLastUpdated(new Date());
        }

        log.info("New account data: {}", account);

        accountRepository.save(account);
    }

    @Transactional
    @Override
    public void changeAvatarByEmail(String email, MultipartFile image) {
        Account account = getAccountEntityByEmail(email);
        String newAvatar =  uploadImage(image);
        account.setPhotoUrl(newAvatar);
    }

    @Transactional
    @Override
    public void deleteAvatarByEmail(String email) {
        Account account = getAccountEntityByEmail(email);
        account.setPhotoUrl(Bucket.ACCOUNT_LINK + Bucket.ACCOUNT_DEFAULT_IMAGE);
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

    private String uploadImage(MultipartFile image) {
        if (image == null) {
            return Bucket.ACCOUNT_LINK + Bucket.ACCOUNT_DEFAULT_IMAGE;
        }
        fileStoreService.uploadImage(image, Bucket.ACCOUNT_PATH);
        return Bucket.ACCOUNT_LINK + "/" + image.getOriginalFilename();
    }
}
