package com.example.nurecareercenterua.domain.department.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "department")
@Getter
@Setter
@NoArgsConstructor
public class Department {
    @Id
    @Column(name = "short_name", length = 8)
    private String shortName;

    @Column(name = "full_name", length = 100)
    private String fullName;

    @Override
    public String toString() {
        return "Department{" +
                "shortName='" + shortName + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
