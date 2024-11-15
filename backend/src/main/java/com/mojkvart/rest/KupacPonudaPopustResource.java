package com.mojkvart.rest;

import com.mojkvart.model.KupacPonudaPopustDTO;
import com.mojkvart.service.KupacPonudaPopustService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("https://kvart-frontend-qin5.vercel.app")
@RestController
@RequestMapping(value = "/api/kupacPonudaPopusts", produces = MediaType.APPLICATION_JSON_VALUE)
public class KupacPonudaPopustResource {

    private final KupacPonudaPopustService kupacPonudaPopustService;

    public KupacPonudaPopustResource(
            final KupacPonudaPopustService kupacPonudaPopustService) {
        this.kupacPonudaPopustService = kupacPonudaPopustService;
    }

    @GetMapping
    public ResponseEntity<List<KupacPonudaPopustDTO>> getAllkupacPonudaPopusts() {
        return ResponseEntity.ok(kupacPonudaPopustService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<KupacPonudaPopustDTO> getkupacPonudaPopust(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(kupacPonudaPopustService.get(id));
    }

    //UC19, koristite api/kupacPonudaPopusts za spremanje ponude i popusta
    @PostMapping
    public ResponseEntity<Long> createkupacPonudaPopust(
            @RequestBody @Valid final KupacPonudaPopustDTO kupacPonudaPopustDTO) {
        final Long createdId = kupacPonudaPopustService.create(kupacPonudaPopustDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatekupacPonudaPopust(
            @PathVariable(name = "id") final Long id,
            @RequestBody @Valid final KupacPonudaPopustDTO kupacPonudaPopustDTO) {
        kupacPonudaPopustService.update(id, kupacPonudaPopustDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletekupacPonudaPopust(
            @PathVariable(name = "id") final Long id) {
        kupacPonudaPopustService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
