package com.example.nurecareercenterua.domain.account.model.request;

import com.example.nurecareercenterua.domain.account.model.enums.AccountRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationAccount {
    @NotNull(message = "Lastname should not be empty")
    @NotBlank(message = "Lastname should not be blank")
    private String lastname;
    @NotNull(message = "Firstname should not be empty")
    @NotBlank(message = "Firstname should not be blank")
    private String firstname;
    @NotNull(message = "Patronymic should not be empty")
    @NotBlank(message = "Patronymic should not be blank")
    private String patronymic;
    @NotNull(message = "Email should not be empty")
    @NotBlank(message = "Email should not be blank")
    @Email(message = "Email should be valid")
    private String email;
    @NotNull(message = "Phone should not be empty")
    @NotBlank(message = "Phone should not be blank")
    private String phone;
    private AccountRole role;
    @NotNull(message = "Password should not be empty")
    @NotBlank(message = "Password should not be blank")
    private String password;
    @NotNull(message = "Confirmed password should not be empty")
    @NotBlank(message = "Confirmed password should not be blank")
    @JsonProperty("confirmed_password")
    private String confirmedPassword;
}
