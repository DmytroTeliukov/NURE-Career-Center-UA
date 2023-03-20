package com.example.nurecareercenterua.domain.account.model.dto.response;

import com.example.nurecareercenterua.domain.account.model.enums.AccountRole;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.UUID;

public record CreatedAccount(
        @JsonProperty("id_account") UUID id,
        @JsonProperty("lastname") String lastname,
        @JsonProperty("firstname") String firstname,
        @JsonProperty("patronymic") String patronymic,
        @JsonProperty("avatar") String photoUrl,
        @JsonProperty("email") String email,
        @JsonProperty("phone") String phone,
        @JsonProperty("role") AccountRole role,
        @JsonProperty("account_created") Date createdAt) {}
