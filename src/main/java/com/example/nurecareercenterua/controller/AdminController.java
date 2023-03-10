package com.example.nurecareercenterua.controller;

import com.example.nurecareercenterua.domain.account.model.dto.AccountDto;
import com.example.nurecareercenterua.domain.account.model.enums.AccountRole;
import com.example.nurecareercenterua.domain.account.model.request.RegistrationAccount;
import com.example.nurecareercenterua.domain.account.model.response.CreatedAccount;
import com.example.nurecareercenterua.domain.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private final AccountService accountService;

    @Autowired
    public AdminController(AccountService accountService) {
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


}
