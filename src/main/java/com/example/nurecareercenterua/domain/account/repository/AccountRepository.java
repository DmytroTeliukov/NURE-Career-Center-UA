package com.example.nurecareercenterua.domain.account.repository;

import com.example.nurecareercenterua.domain.account.model.entity.Account;
import com.example.nurecareercenterua.domain.account.model.enums.AccountRole;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    @Query("select case when count(a) > 0 then true else false end " +
            "from Account a where a.email = ?1")
    boolean existsAccountByEmail(String email);

    @Query("select case when count(a) > 0 then true else false end " +
            "from Account a where a.phone = ?1")
    boolean existsAccountByPhone(String phone);

    @Query("SELECT a FROM Account a WHERE a.role = :#{#role}")
    List<Account> findAllByAccountRole(@Param("role") AccountRole role, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Account a set a.isNonLocked = ?2 where a.email = ?1")
    void changeAccessStatus(String email, boolean hasAccess);

}
