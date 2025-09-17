package com.wbf.mutuelle.services;

import com.wbf.mutuelle.entities.Member;
import com.wbf.mutuelle.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MemberDetailsService implements UserDetailsService {
    @Autowired
    private MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + email));

        return new User(
                member.getEmail(),
                member.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + member.getRole()))
        );
    }

}
