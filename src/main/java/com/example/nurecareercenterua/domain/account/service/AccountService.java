package com.example.nurecareercenterua.domain.account.service;

import com.example.nurecareercenterua.domain.account.model.dto.AccountDto;
import com.example.nurecareercenterua.domain.account.model.dto.request.ChangeAccountData;
import com.example.nurecareercenterua.domain.account.model.enums.AccountRole;
import com.example.nurecareercenterua.domain.account.model.dto.request.AccountOperationRequest;
import com.example.nurecareercenterua.domain.account.model.dto.request.ChangePasswordRequest;
import com.example.nurecareercenterua.domain.account.model.dto.request.RegistrationAccount;
import com.example.nurecareercenterua.domain.account.model.dto.response.CreatedAccount;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface AccountService {
    CreatedAccount register(RegistrationAccount registrationAccount, MultipartFile image);
    List<AccountDto> findAllByRole(AccountRole role, int page, int size);
    AccountDto findAccountByEmail(String email);
    void changeAccountData(String email, ChangeAccountData data);
    void changeAvatarByEmail(String email, MultipartFile image);
    void deleteAvatarByEmail(String email);
    void changeAccountAccess(AccountOperationRequest accountOperationRequest);
    void changePassword(ChangePasswordRequest changePasswordRequest);
}
