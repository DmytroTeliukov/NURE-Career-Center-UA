package com.example.nurecareercenterua.domain.department.api;

import com.example.nurecareercenterua.domain.department.model.Department;
import com.example.nurecareercenterua.domain.department.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<?> createDepartment(@RequestBody Department department) {
        departmentService.save(department);
        String message = "Created successfully department " + department.getShortName();
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @DeleteMapping("/{shortname}")
    public ResponseEntity<?> deleteDepartment(@PathVariable("shortname") String shortName) {
        departmentService.delete(shortName);
        String message = "Deleted successfully department " + shortName;
        return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public List<Department> getListOfDepartments() {
        return departmentService.findAll();
    }
}
