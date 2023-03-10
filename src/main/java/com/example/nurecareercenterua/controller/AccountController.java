package com.example.nurecareercenterua.controller;

import com.example.nurecareercenterua.domain.account.exception.AccountNotFoundException;
import com.example.nurecareercenterua.domain.account.exception.EmailAlreadyRegisteredException;
import com.example.nurecareercenterua.domain.account.exception.IllegalPasswordArgumentException;
import com.example.nurecareercenterua.domain.account.exception.PhoneAlreadyRegisteredException;
import com.example.nurecareercenterua.domain.account.model.dto.AccountDto;
import com.example.nurecareercenterua.domain.account.model.enums.AccountRole;
import com.example.nurecareercenterua.domain.account.model.request.AccountOperationRequest;
import com.example.nurecareercenterua.domain.account.model.request.ChangePasswordRequest;
import com.example.nurecareercenterua.domain.account.model.request.RegistrationAccount;
import com.example.nurecareercenterua.domain.account.model.response.CreatedAccount;
import com.example.nurecareercenterua.domain.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/access")
    public ResponseEntity<?> changeAccountAccess(@RequestBody AccountOperationRequest accountOperationRequest) {
        accountService.changeAccountAccess(accountOperationRequest);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        accountService.changePassword(changePasswordRequest);
        return ResponseEntity.noContent().build();
    }

}
