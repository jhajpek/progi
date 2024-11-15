package com.mojkvart.rest;

import com.mojkvart.model.TokenDTO;
import com.mojkvart.service.JwtService;
import com.mojkvart.util.TokenResponse;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("https://kvart-frontend-qin5.vercel.app")
@RestController
@RequestMapping("/api/tokens")
@RequiredArgsConstructor
public class TokenResource {

    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<TokenResponse> extractInfoFromToken(@RequestBody @Valid TokenDTO tokenDTO) {
        String email = jwtService.getEmailFromToken(tokenDTO.getToken());
        Claims claims = jwtService.getAllClaims(tokenDTO.getToken());
        String role = (String) claims.get("role");
        TokenResponse tokenResponse = new TokenResponse(email, role);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/expiration")
    public ResponseEntity<Boolean> checkTokenExpiration(@RequestBody @Valid TokenDTO tokenDTO) {
        return ResponseEntity.ok(jwtService.isTokenExpired(tokenDTO.getToken()));
    }
}