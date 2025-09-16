package com.wbf.mutuelle.controllers;

import com.wbf.mutuelle.configuration.JwtUtil;
import com.wbf.mutuelle.entities.Member;
import com.wbf.mutuelle.repositories.MemberRepository;
import com.wbf.mutuelle.services.CustomUserDetails;
import lombok.RequiredArgsConstructor;
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

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (memberRepository.findAll().stream().anyMatch(m -> m.getEmail().equalsIgnoreCase(req.getEmail()))) {
            return ResponseEntity.badRequest().body("Email already in use");
        }

        Member member = new Member();
        member.setName(req.getName());
        member.setFirstName(req.getFirstName());
        member.setEmail(req.getEmail());
        member.setPassword(passwordEncoder.encode(req.getPassword()));
        member.setPhone(req.getPhone());
        member.setRole(req.getRole() == null ? "MEMBER" : req.getRole().toUpperCase());

        memberRepository.save(member);

        CustomUserDetails userDetails = new CustomUserDetails(member);
        String token = jwtUtil.generateToken(String.valueOf(userDetails));

        return ResponseEntity.ok(new AuthResponse(token));
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(String.valueOf(userDetails));
            //CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            //String token = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Email ou mot de passe invalide.");
        }
    }
}
