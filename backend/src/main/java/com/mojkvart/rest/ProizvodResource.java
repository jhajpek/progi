package com.mojkvart.rest;

import com.mojkvart.model.ProizvodDTO;
import com.mojkvart.service.ProizvodService;
import com.mojkvart.util.ReferencedException;
import com.mojkvart.util.ReferencedWarning;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("https://kvart-frontend-qin5.vercel.app")
@RestController
@RequestMapping(value = "/api/proizvods", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProizvodResource {

    private final ProizvodService proizvodService;

    public ProizvodResource(final ProizvodService proizvodService) {
        this.proizvodService = proizvodService;
    }

    @GetMapping
    public ResponseEntity<List<ProizvodDTO>> getAllProizvods() {
        return ResponseEntity.ok(proizvodService.findAll());
    }

    @GetMapping("/{proizvodId}")
    public ResponseEntity<ProizvodDTO> getProizvod(
            @PathVariable(name = "proizvodId") final Integer proizvodId) {
        return ResponseEntity.ok(proizvodService.get(proizvodId));
    }

    // za dohvacanje svih proizvoda potvrdenih od strane moderatora, koristi se kada kupac nije izabrao nikakve filtre
    @GetMapping("/approved")
    public ResponseEntity<List<ProizvodDTO>> getApprovedProizvods() {
        return ResponseEntity.ok(proizvodService.findAllApproved());
    }

    // za dohvacanje svih proizvoda koji jos nisu potvrdeni od strane moderatora
    @GetMapping("/notApproved")
    public ResponseEntity<List<ProizvodDTO>> getNotApprovedProizvods() {
        return ResponseEntity.ok(proizvodService.findAllNotApproved());
    }

    // za dohvacanje svih proizvoda jedne trgovine
    @GetMapping("/fromTrgovina/{trgovinaId}")
    public ResponseEntity<List<ProizvodDTO>> getTrgovinaProizvods(
            @PathVariable(name = "trgovinaId") Integer trgovinaId) {
        return ResponseEntity.ok(proizvodService.findByTrgovina(trgovinaId));
    }

    // UC11, koristite api/proizvods te pošaljite JSON objekt za kriranje novog
    // proizvoda od strane trgovine
    @PostMapping
    public ResponseEntity<Integer> createProizvod(
            @RequestBody @Valid final ProizvodDTO proizvodDTO) {
        final Integer createdProizvodId = proizvodService.create(proizvodDTO);
        return new ResponseEntity<>(createdProizvodId, HttpStatus.CREATED);
    }

    // UC7, koristite api/proizvods/{proizvodId} za mijenjanje atributa iz F u T ako
    // je odobren
    @PutMapping("/{proizvodId}")
    public ResponseEntity<Integer> updateProizvod(
            @PathVariable(name = "proizvodId") final Integer proizvodId,
            @RequestBody @Valid final ProizvodDTO proizvodDTO) {
        proizvodService.update(proizvodId, proizvodDTO);
        return ResponseEntity.ok(proizvodId);
    }

    @DeleteMapping("/{proizvodId}")
    public ResponseEntity<Void> deleteProizvod(
            @PathVariable(name = "proizvodId") final Integer proizvodId) {
        final ReferencedWarning referencedWarning = proizvodService.getReferencedWarning(proizvodId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        proizvodService.delete(proizvodId);
        return ResponseEntity.noContent().build();
    }

}
