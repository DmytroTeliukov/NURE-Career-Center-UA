package com.example.nurecareercenterua.controller;

import com.example.nurecareercenterua.domain.account.model.dto.AccountDto;
import com.example.nurecareercenterua.domain.account.model.entity.Account;
import com.example.nurecareercenterua.domain.account.model.request.LoginRequest;
import com.example.nurecareercenterua.domain.account.service.AccountService;
import com.example.nurecareercenterua.security.jwt.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final AccountService accountService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        var authToken = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());
        var authentication = authenticationManager.authenticate(authToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        AccountDto account = accountService.findAccountByEmail(loginRequest.email());
        HttpHeaders headers = jwtTokenProvider.generateJwtHeader(account);

        return new ResponseEntity<>("You are logged in successfully", headers, HttpStatus.OK);
    }


}
