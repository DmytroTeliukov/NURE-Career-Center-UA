package com.example.nurecareercenterua.domain.account.repository;

import com.example.nurecareercenterua.domain.account.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    @Query("select case when count(a) > 0 then true else false end " +
            "from Account a where a.email = ?1")
    boolean existsAccountByEmail(String email);

    @Query("select case when count(a) > 0 then true else false end " +
            "from Account a where a.phone = ?1")
    boolean existsAccountByPhone(String phone);


}
