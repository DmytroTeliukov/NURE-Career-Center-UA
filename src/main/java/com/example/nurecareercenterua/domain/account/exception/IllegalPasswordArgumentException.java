package com.example.nurecareercenterua.domain.account.exception;

public class IllegalPasswordArgumentException extends RuntimeException {
    public IllegalPasswordArgumentException () {
        super("Incorrect password");
    }
}
