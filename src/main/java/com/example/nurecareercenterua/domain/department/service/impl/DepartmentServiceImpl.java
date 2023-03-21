package com.example.nurecareercenterua.domain.department.service.impl;

import com.example.nurecareercenterua.domain.department.model.Department;
import com.example.nurecareercenterua.domain.department.repository.DepartmentRepository;
import com.example.nurecareercenterua.domain.department.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Override
    public void save(Department department) {
        departmentRepository.save(department);
    }

    @Override
    public void delete(String shortname) {
        departmentRepository.deleteByShortName(shortname);
    }

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }
}
