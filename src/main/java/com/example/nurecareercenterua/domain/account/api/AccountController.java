package com.example.nurecareercenterua.domain.account.api;

import com.example.nurecareercenterua.domain.account.model.dto.AccountDto;
import com.example.nurecareercenterua.domain.account.model.dto.request.AccountOperationRequest;
import com.example.nurecareercenterua.domain.account.model.dto.request.ChangeAccountData;
import com.example.nurecareercenterua.domain.account.model.dto.request.ChangePasswordRequest;
import com.example.nurecareercenterua.domain.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

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

    @PatchMapping
    public ResponseEntity<?> changeAccountData(@RequestBody ChangeAccountData data) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        accountService.changeAccountData(email, data);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/avatar")
    public ResponseEntity<?> changeAccountAvatar(@RequestPart("image") MultipartFile image) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        accountService.changeAvatarByEmail(email, image);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/avatar")
    public ResponseEntity<?> deleteAccountAvatar() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        accountService.deleteAvatarByEmail(email);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/profile")
    public AccountDto getProfile() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return accountService.findAccountByEmail(email);
    }

}
