package com.example.nurecareercenterua.domain.account.mapper;

import com.example.nurecareercenterua.domain.account.model.dto.AccountDto;
import com.example.nurecareercenterua.domain.account.model.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public AccountDto toAccountDto(Account account) {
        return new AccountDto(account.getId(),
                account.getLastname(),
                account.getFirstname(),
                account.getPatronymic(),
                account.getPhotoUrl(),
                account.getEmail(),
                account.getPhone(),
                account.getRole(),
                account.getIsNonLocked(),
                account.getJoined(),
                account.getLastUpdated(),
                account.getLastLogin());
    }
}
