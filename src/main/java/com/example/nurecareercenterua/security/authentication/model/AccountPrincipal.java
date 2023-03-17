package com.example.nurecareercenterua.security.authentication.model;

import com.example.nurecareercenterua.domain.account.model.entity.Account;
import com.example.nurecareercenterua.domain.account.model.enums.AccountRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class AccountPrincipal implements UserDetails {

    private final String email;
    private final String password;
    private final AccountRole role;
    private final boolean isActive;
    private final boolean isNonLocked;

    public AccountPrincipal(Account account) {
        this.email = account.getEmail();
        this.password = account.getPassword();
        this.role = account.getRole();
        this.isNonLocked = account.getIsNonLocked();
        this.isActive = account.getIsActive();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
