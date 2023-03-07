package com.example.nurecareercenterua.domain.account.service;

import com.example.nurecareercenterua.domain.account.model.dto.AccountDto;
import com.example.nurecareercenterua.domain.account.model.entity.Account;
import com.example.nurecareercenterua.domain.account.model.enums.AccountRole;
import com.example.nurecareercenterua.domain.account.model.request.RegistrationAccount;
import com.example.nurecareercenterua.domain.account.model.response.CreatedAccount;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountService {
    CreatedAccount register(RegistrationAccount registrationAccount);
    List<AccountDto> findAllByRole(AccountRole role, int page, int size);
}
