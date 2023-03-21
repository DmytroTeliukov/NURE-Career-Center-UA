package com.example.nurecareercenterua.domain.specialty.repository;

import com.example.nurecareercenterua.domain.specialty.model.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Integer> {
    @Transactional
    @Modifying
    @Query("delete from Specialty s where s.number = ?1")
    void deleteByNumber(Integer number);
}
