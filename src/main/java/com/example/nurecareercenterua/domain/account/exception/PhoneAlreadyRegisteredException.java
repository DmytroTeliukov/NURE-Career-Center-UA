package com.example.nurecareercenterua.domain.account.exception;

public class PhoneAlreadyRegisteredException extends RuntimeException {
    public PhoneAlreadyRegisteredException() {
        super("Phone is already taken");
    }
}
