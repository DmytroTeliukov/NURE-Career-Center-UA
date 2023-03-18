package com.example.nurecareercenterua.domain.account.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChangePasswordRequest(String email,
                                    @JsonProperty("current_password") String currentPassword,
                                    @JsonProperty("new_password") String newPassword,
                                    @JsonProperty("confirmed_new_password") String confirmedNewPassword) {
}
