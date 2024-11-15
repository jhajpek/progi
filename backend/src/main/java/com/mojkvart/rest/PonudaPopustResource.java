package com.mojkvart.rest;

import com.mojkvart.model.PonudaPopustDTO;
import com.mojkvart.service.PonudaPopustService;
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
@RequestMapping(value = "/api/ponudaPopusts", produces = MediaType.APPLICATION_JSON_VALUE)
public class PonudaPopustResource {

    private final PonudaPopustService ponudaPopustService;

    public PonudaPopustResource(final PonudaPopustService ponudaPopustService) {
        this.ponudaPopustService = ponudaPopustService;
    }

    @GetMapping
    public ResponseEntity<List<PonudaPopustDTO>> getAllPonudaPopusts() {
        return ResponseEntity.ok(ponudaPopustService.findAll());
    }

    @GetMapping("/{ponudaPopustId}")
    public ResponseEntity<PonudaPopustDTO> getPonudaPopust(
            @PathVariable(name = "ponudaPopustId") final Integer ponudaPopustId) {
        return ResponseEntity.ok(ponudaPopustService.get(ponudaPopustId));
    }

    //UC13, koristite api/ponudaPopusts i posaljite JSON objekt za kreiranje nove ponudePopusta
    @PostMapping
    public ResponseEntity<Integer> createPonudaPopust(
            @RequestBody @Valid final PonudaPopustDTO ponudaPopustDTO) {
        final Integer createdPonudaPopustId = ponudaPopustService.create(ponudaPopustDTO);
        return new ResponseEntity<>(createdPonudaPopustId, HttpStatus.CREATED);
    }

    //UC8, koristite api/ponudaPopusts/{ponudaPopustId} za mijenjanje atributa iz F u T ako su odobreni
    @PutMapping("/{ponudaPopustId}")
    public ResponseEntity<Integer> updatePonudaPopust(
            @PathVariable(name = "ponudaPopustId") final Integer ponudaPopustId,
            @RequestBody @Valid final PonudaPopustDTO ponudaPopustDTO) {
        ponudaPopustService.update(ponudaPopustId, ponudaPopustDTO);
        return ResponseEntity.ok(ponudaPopustId);
    }

    @DeleteMapping("/{ponudaPopustId}")
    public ResponseEntity<Void> deletePonudaPopust(
            @PathVariable(name = "ponudaPopustId") final Integer ponudaPopustId) {
        final ReferencedWarning referencedWarning = ponudaPopustService.getReferencedWarning(ponudaPopustId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        ponudaPopustService.delete(ponudaPopustId);
        return ResponseEntity.noContent().build();
    }

}
