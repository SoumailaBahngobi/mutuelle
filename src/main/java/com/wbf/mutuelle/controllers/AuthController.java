package com.wbf.mutuelle.controllers;

import com.wbf.mutuelle.configuration.JwtUtil;
import com.wbf.mutuelle.entities.Member;
import com.wbf.mutuelle.repositories.MemberRepository;
import com.wbf.mutuelle.services.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        return jwtUtil.generateToken(request.getEmail());
    }

    @PostMapping("/register")
    public Member register(@RequestBody RegisterRequest request) {
        if (memberRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email déjà utilisé !");
        }

        Member member = new Member();
        member.setName(request.getName());
        member.setFirstName(request.getFirstName());
        member.setEmail(request.getEmail());
        member.setPassword(passwordEncoder.encode(request.getPassword())); // mot de passe hashé
        member.setNpi(request.getNpi());
        member.setPhone(request.getPhone());
        member.setRole(request.getRole());

        return memberRepository.save(member);
    }

}
