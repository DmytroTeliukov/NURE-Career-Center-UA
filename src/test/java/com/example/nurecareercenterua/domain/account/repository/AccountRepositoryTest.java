package com.example.nurecareercenterua.domain.account.repository;

import com.example.nurecareercenterua.domain.account.model.entity.Account;
import com.example.nurecareercenterua.domain.account.model.enums.AccountRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql({"/db/data.sql"})
class AccountRepositoryTest {

    private static final String email = "admin@nure.ua";
    private static final String notOccupiedEmail = "student@nure.ua";
    private static final String newPassword = "123456";
    private static final boolean banAccount = false;
    private static final int totalAdmins = 3;
    @Autowired
    private AccountRepository accountRepository;


    @Test
    @DisplayName("Find account by email successfully")
    void findAccountByEmail() {
        Optional<Account> account = accountRepository.findAccountByEmail(email);

        assertTrue(account.isPresent());

    }

    @Test
    @DisplayName("Get exists account by email")
    void existsAccountByEmail() {
        boolean exists = accountRepository.existsAccountByEmail(email);

        assertTrue(exists);
    }

    @Test
    @DisplayName("Get fail result of existing account by email")
    void notExistsAccountByEmail() {
        boolean exists = accountRepository.existsAccountByEmail(notOccupiedEmail);

        assertFalse(exists);
    }


    @Test
    @DisplayName("Find 3 admins")
    void findAllByAccountRole() {
        AccountRole adminRole = AccountRole.ADMIN;

        var admins = accountRepository.findAllByAccountRole(adminRole, Pageable.unpaged());

        assertEquals(totalAdmins, admins.size());
    }

    @Test
    @DisplayName("Ban account")
    void changeAccessStatus() {
        accountRepository.changeAccessStatus(email, banAccount);

        Account account = accountRepository.findAccountByEmail(email).orElseThrow();

        assertFalse(account.getIsNonLocked());
    }

    @Test
    @DisplayName("Change password")
    void changePassword() {
        accountRepository.changePassword(email, newPassword);

        Account accountWithNewPassword = accountRepository.findAccountByEmail(email).orElseThrow();

        assertEquals(newPassword, accountWithNewPassword.getPassword());

    }
}