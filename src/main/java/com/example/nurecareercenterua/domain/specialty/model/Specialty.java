package com.example.nurecareercenterua.domain.specialty.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "specialty")
@Getter
@Setter
@NoArgsConstructor
public class Specialty {
    @Id
    @Column(name = "sp_number")
    private Integer number;
    @Column(name = "full_name")
    private String fullName;
}
