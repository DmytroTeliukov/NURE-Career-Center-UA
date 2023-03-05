package com.example.nurecareercenterua.domain.account.service;

import com.example.nurecareercenterua.domain.account.model.request.RegistrationAccount;
import com.example.nurecareercenterua.domain.account.model.response.CreatedAccount;

public interface AccountService {
    CreatedAccount register(RegistrationAccount registrationAccount);
}
