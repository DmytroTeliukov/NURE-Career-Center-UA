package com.example.nurecareercenterua.domain.account.service;

import com.example.nurecareercenterua.domain.account.model.dto.AccountDto;
import com.example.nurecareercenterua.domain.account.model.enums.AccountRole;
import com.example.nurecareercenterua.domain.account.model.dto.request.AccountOperationRequest;
import com.example.nurecareercenterua.domain.account.model.dto.request.ChangePasswordRequest;
import com.example.nurecareercenterua.domain.account.model.dto.request.RegistrationAccount;
import com.example.nurecareercenterua.domain.account.model.dto.request.response.CreatedAccount;

import java.util.List;

public interface AccountService {
    CreatedAccount register(RegistrationAccount registrationAccount);
    List<AccountDto> findAllByRole(AccountRole role, int page, int size);
    AccountDto findAccountByEmail(String email);
    void changeAccountAccess(AccountOperationRequest accountOperationRequest);
    void changePassword(ChangePasswordRequest changePasswordRequest);
}
