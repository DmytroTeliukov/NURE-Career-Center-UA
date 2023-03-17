package com.example.nurecareercenterua.controller;

import com.example.nurecareercenterua.domain.account.model.request.AccountOperationRequest;
import com.example.nurecareercenterua.domain.account.model.request.ChangePasswordRequest;
import com.example.nurecareercenterua.domain.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

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
