package com.example.nurecareercenterua.domain.account.model.entity;

import com.example.nurecareercenterua.domain.account.model.enums.AccountRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "id_account", length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @NotNull(message = "Lastname should not be null")
    @NotBlank(message = "Lastname should not be blank")
    @Column(name = "lastname", length = 30, nullable = false)
    private String lastname;

    @NotNull(message = "Firstname should not be null")
    @NotBlank(message = "Firstname should not be blank")
    @Column(name = "firstname", length = 30, nullable = false)
    private String firstname;

    @NotNull(message = "Patronymic should not be null")
    @NotBlank(message = "Patronymic should not be blank")
    @Column(name = "patronymic", length = 30, nullable = false)
    private String patronymic;

    @NotNull(message = "Email should not be null")
    @NotBlank(message = "Email should not be blank")
    @Email(message = "Email should be valid")
    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @NotNull(message = "Phone should not be null")
    @NotBlank(message = "Phone should not be blank")
    @Size(min = 13, message = "Phone size should be 13")
    @Column(name = "phone", length = 13, nullable = false, unique = true)
    private String phone;

    @NotNull(message = "Role should not be null")
    @Column(name = "role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AccountRole role;

    @NotNull(message = "Password should not be null")
    @NotBlank(message = "Password should not be blank")
    @Column(name = "password", length = 256, nullable = false)
    private String password;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "is_non_locked", nullable = false)
    private Boolean isNonLocked;

    @Column(name = "joined", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date joined;

    @Column(name = "last_updated", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date lastUpdated;

    @Column(name = "last_login", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date lastLogin;



}
