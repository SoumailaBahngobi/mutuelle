package com.wbf.mutuelle.services;

import com.wbf.mutuelle.entities.Member;
import com.wbf.mutuelle.repositories.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class PasswordResetService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void resetAllPasswordsToBCrypt() {
        List<Member> members = memberRepository.findAll();

        for (Member member : members) {
            // Réencoder le mot de passe en BCrypt
            String rawPassword = "password123"; // Mot de passe par défaut
            String encodedPassword = passwordEncoder.encode(rawPassword);
            member.setPassword(encodedPassword);
            memberRepository.save(member);

            System.out.println("Mot de passe réinitialisé pour: " + member.getEmail());
        }
    }

    @Transactional
    public void resetMemberPassword(String email, String newRawPassword) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Membre non trouvé"));

        String encodedPassword = passwordEncoder.encode(newRawPassword);
        member.setPassword(encodedPassword);
        memberRepository.save(member);

        System.out.println("Mot de passe réinitialisé pour: " + email);
    }
}
