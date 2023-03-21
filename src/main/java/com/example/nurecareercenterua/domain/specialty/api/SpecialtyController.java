package com.example.nurecareercenterua.domain.specialty.api;

import com.example.nurecareercenterua.domain.specialty.model.Specialty;
import com.example.nurecareercenterua.domain.specialty.service.SpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specialties")
@RequiredArgsConstructor
public class SpecialtyController {
    private final SpecialtyService specialtyService;

    @GetMapping
    public List<Specialty> getListSpecialties() {
        return specialtyService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createNewSpecialty(@RequestBody Specialty specialty) {
        specialtyService.save(specialty);
        String message = "Created successfully specialty with number " + specialty.getNumber();
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @DeleteMapping("/{number}")
    public ResponseEntity<?> deleteSpecialty(@PathVariable int number) {
        specialtyService.delete(number);
        String message = "Deleted successfully specialty with number " + number;
        return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
    }


}
