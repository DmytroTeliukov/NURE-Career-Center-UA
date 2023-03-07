package com.example.nurecareercenterua.domain.account.model.dto;

import com.example.nurecareercenterua.domain.account.model.enums.AccountRole;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.UUID;

public record AccountDto(@JsonProperty("id_account") UUID id,
                         @JsonProperty("lastname") String lastname,
                         @JsonProperty("firstname") String firstname,
                         @JsonProperty("patronymic") String patronymic,
                         @JsonProperty("email") String email,
                         @JsonProperty("phone") String phone,
                         @JsonProperty("joined") Date createdAt,
                         @JsonProperty("last_updated") Date lastUpdated,
                         @JsonProperty("last_login") Date lastLogin) {
}
