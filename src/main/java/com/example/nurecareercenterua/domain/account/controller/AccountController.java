package com.example.nurecareercenterua.domain.account.controller;

import com.example.nurecareercenterua.domain.account.exception.EmailAlreadyRegisteredException;
import com.example.nurecareercenterua.domain.account.exception.IllegalPasswordArgumentException;
import com.example.nurecareercenterua.domain.account.exception.PhoneAlreadyRegisteredException;
import com.example.nurecareercenterua.domain.account.model.dto.AccountDto;
import com.example.nurecareercenterua.domain.account.model.enums.AccountRole;
import com.example.nurecareercenterua.domain.account.model.request.AccountOperationRequest;
import com.example.nurecareercenterua.domain.account.model.request.RegistrationAccount;
import com.example.nurecareercenterua.domain.account.model.response.CreatedAccount;
import com.example.nurecareercenterua.domain.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public CreatedAccount register(@RequestBody @Validated RegistrationAccount registrationAccount) {
        return accountService.register(registrationAccount);
    }

    @GetMapping("/admins")
    public List<AccountDto> getListAdmins(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return accountService.findAllByRole(AccountRole.ADMIN, page, size);
    }

    @PostMapping("/access")
    public ResponseEntity<?> changeAccountAccess(@RequestBody AccountOperationRequest accountOperationRequest) {
        accountService.changeAccountAccess(accountOperationRequest);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({EmailAlreadyRegisteredException.class,
            PhoneAlreadyRegisteredException.class,
            IllegalPasswordArgumentException.class})
    public ResponseEntity<?> handlerAccountException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
