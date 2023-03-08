package com.example.nurecareercenterua.domain.account.model.enums;

public enum AccountOperation {
    RESTORE(true), BAN(false);
    private final boolean content;

    AccountOperation(boolean content) {
        this.content = content;
    }


    public boolean getContent() {
        return content;
    }
}
