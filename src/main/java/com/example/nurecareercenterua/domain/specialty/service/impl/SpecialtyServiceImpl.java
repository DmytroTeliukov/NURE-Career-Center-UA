package com.example.nurecareercenterua.domain.specialty.service.impl;

import com.example.nurecareercenterua.domain.specialty.model.Specialty;
import com.example.nurecareercenterua.domain.specialty.repository.SpecialtyRepository;
import com.example.nurecareercenterua.domain.specialty.service.SpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialtyServiceImpl implements SpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    @Override
    public void save(Specialty specialty) {
        specialtyRepository.save(specialty);
    }

    @Override
    public void delete(Integer number) {
        specialtyRepository.deleteByNumber(number);
    }

    @Override
    public List<Specialty> findAll() {
        return specialtyRepository.findAll();
    }
}
