package com.example.nurecareercenterua.domain.account.api;

import com.example.nurecareercenterua.domain.account.model.dto.AccountDto;
import com.example.nurecareercenterua.domain.account.model.enums.AccountRole;
import com.example.nurecareercenterua.domain.account.model.dto.request.RegistrationAccount;
import com.example.nurecareercenterua.domain.account.model.dto.response.CreatedAccount;
import com.example.nurecareercenterua.domain.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedAccount register(@RequestPart("registration_account") @Validated RegistrationAccount registrationAccount,
                                   @RequestPart(name = "image", required = false) MultipartFile image) {

        return accountService.register(registrationAccount, image);
    }

    @GetMapping
    public List<AccountDto> getListAdmins(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return accountService.findAllByRole(AccountRole.ADMIN, page, size);
    }

}
