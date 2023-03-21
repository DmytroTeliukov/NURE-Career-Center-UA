package com.example.nurecareercenterua.domain.specialty.service;

import com.example.nurecareercenterua.domain.specialty.model.Specialty;

import java.util.List;

public interface SpecialtyService {
    void save(Specialty specialty);
    void delete(Integer number);
    List<Specialty> findAll();
}
