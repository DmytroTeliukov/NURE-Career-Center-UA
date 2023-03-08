package com.example.nurecareercenterua.domain.account.model.request;

import com.example.nurecareercenterua.domain.account.model.enums.AccountOperation;


public record AccountOperationRequest(String email, AccountOperation operation) {
}
