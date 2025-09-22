package com.wbf.mutuelle.services;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wbf.mutuelle.entities.Member;
import com.wbf.mutuelle.repositories.MemberRepository;

@Service
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + email));

        if (member.getRole() == null) {
            throw new UsernameNotFoundException("Le membre " + email + " n'a pas de rôle défini !");
        }

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + member.getRole().name());

        return new User(
                member.getEmail(),
                member.getPassword(),
                Collections.singletonList(authority)
        );
    }
}