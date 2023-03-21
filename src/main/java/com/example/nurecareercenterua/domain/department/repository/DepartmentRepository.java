package com.example.nurecareercenterua.domain.department.repository;

import com.example.nurecareercenterua.domain.department.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {

    @Transactional
    @Modifying
    @Query("delete from Department d where d.shortName = ?1")
    void deleteByShortName(String shortname);
}
