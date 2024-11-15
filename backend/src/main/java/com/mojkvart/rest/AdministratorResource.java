package com.mojkvart.rest;

import com.mojkvart.model.AdministratorDTO;
import com.mojkvart.service.AdministratorService;
import jakarta.validation.Valid;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("https://kvart-frontend-qin5.vercel.app")
@RestController
@RequestMapping(value = "/api/administrators", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdministratorResource {

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<List<AdministratorDTO>> getAllAdministrators() {
        return ResponseEntity.ok(administratorService.findAll());
    }

    @GetMapping("/{administratorId}")
    public ResponseEntity<AdministratorDTO> getAdministrator(
            @PathVariable(name = "administratorId") final Integer administratorId) {
        return ResponseEntity.ok(administratorService.get(administratorId));
    }

    @PostMapping
    public ResponseEntity<Integer> createAdministrator(
            @RequestBody @Valid final AdministratorDTO administratorDTO) {
        administratorDTO.setAdministratorSifra(passwordEncoder.encode(administratorDTO.getAdministratorSifra()));
        final Integer createdAdministratorId = administratorService.create(administratorDTO);
        return new ResponseEntity<>(createdAdministratorId, HttpStatus.CREATED);
    }

    @PutMapping("/{administratorId}")
    public ResponseEntity<Integer> updateAdministrator(
            @PathVariable(name = "administratorId") final Integer administratorId,
            @RequestBody @Valid final AdministratorDTO administratorDTO) {
        administratorService.update(administratorId, administratorDTO);
        return ResponseEntity.ok(administratorId);
    }

    @DeleteMapping("/{administratorId}")
    public ResponseEntity<Void> deleteAdministrator(
            @PathVariable(name = "administratorId") final Integer administratorId) {
        administratorService.delete(administratorId);
        return ResponseEntity.noContent().build();
    }

}