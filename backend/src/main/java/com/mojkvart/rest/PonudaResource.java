package com.mojkvart.rest;

import com.mojkvart.model.PonudaDTO;
import com.mojkvart.service.PonudaService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("https://kvart-frontend-qin5.vercel.app")
@RestController
@RequestMapping(value = "/api/ponudas", produces = MediaType.APPLICATION_JSON_VALUE)
public class PonudaResource {

    private final PonudaService ponudaService;

    public PonudaResource(final PonudaService ponudaService) {
        this.ponudaService = ponudaService;
    }

    @GetMapping
    public ResponseEntity<List<PonudaDTO>> getAllPonudas() {
        return ResponseEntity.ok(ponudaService.findAll());
    }

    @GetMapping("/{ponudaId}")
    public ResponseEntity<PonudaDTO> getPonuda(
            @PathVariable(name = "ponudaId") final Integer ponudaId) {
        return ResponseEntity.ok(ponudaService.get(ponudaId));
    }

    @PostMapping
    public ResponseEntity<Integer> createPonuda(@RequestBody @Valid final PonudaDTO ponudaDTO) {
        final Integer createdPonudaId = ponudaService.create(ponudaDTO);
        return new ResponseEntity<>(createdPonudaId, HttpStatus.CREATED);
    }

    @PutMapping("/{ponudaId}")
    public ResponseEntity<Integer> updatePonuda(
            @PathVariable(name = "ponudaId") final Integer ponudaId,
            @RequestBody @Valid final PonudaDTO ponudaDTO) {
        ponudaService.update(ponudaId, ponudaDTO);
        return ResponseEntity.ok(ponudaId);
    }

    @DeleteMapping("/{ponudaId}")
    public ResponseEntity<Void> deletePonuda(
            @PathVariable(name = "ponudaId") final Integer ponudaId) {
        ponudaService.delete(ponudaId);
        return ResponseEntity.noContent().build();
    }

}
