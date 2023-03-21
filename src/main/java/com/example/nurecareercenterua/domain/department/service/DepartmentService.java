package com.example.nurecareercenterua.domain.department.service;

import com.example.nurecareercenterua.domain.department.model.Department;

import java.util.List;

public interface DepartmentService {
    void save(Department department);
    void delete(String shortname);
    List<Department> findAll();
}
