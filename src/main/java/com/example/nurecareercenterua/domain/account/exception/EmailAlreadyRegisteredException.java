package com.example.nurecareercenterua.domain.account.exception;

public class EmailAlreadyRegisteredException extends RuntimeException {
    public EmailAlreadyRegisteredException() {
        super("Email is already taken");
    }
}
