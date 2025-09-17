package com.wbf.mutuelle.controllers;

import com.wbf.mutuelle.configuration.JwtUtil;
import com.wbf.mutuelle.entities.Member;
import com.wbf.mutuelle.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        // Vérifie si email déjà utilisé
        if (memberRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email déjà utilisé !");
        }

        // Créer utilisateur
        Member user = new Member();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setFirstName(request.getFirstName());
        user.setNpi(request.getNpi());
        user.setPhone(request.getPhone());
        user.setRole(request.getRole());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        memberRepository.save(user);

        // Générer un token après enregistrement
        String token = jwtUtil.generateToken(user.getEmail());

        // Renvoyer token dans la réponse
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            // Vérifie les identifiants
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants incorrects !");
        }

        // Génère token si login ok
        String token = jwtUtil.generateToken(request.getEmail());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
