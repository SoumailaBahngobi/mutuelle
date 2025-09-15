package com.wbf.mutuelle.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                        .requestMatchers("/mutuelle").permitAll()
                        .requestMatchers("/mutuelle/president").hasRole("PRESIDENT")
                        .requestMatchers("/mutuelle/secretary").hasRole("SECRETARY")
                        .requestMatchers("/mutuelle/treasurer").hasRole("TREASURER")
                        .requestMatchers("/mutuelle/member").hasRole("MEMBER")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails president = User.withUsername("president")
                .password(encoder.encode("prs"))
                .roles("PRESIDENT")
                .build();

        UserDetails secretary = User.withUsername("secretary")
                .password(encoder.encode("sct"))
                .roles("SECRETARY")
                .build();

        UserDetails treasurer = User.withUsername("treasurer")
                .password(encoder.encode("trs"))
                .roles("TREASURER")
                .build();

        UserDetails member = User.withUsername("member")
                .password(encoder.encode("mem"))
                .roles("MEMBER")
                .build();

        return new InMemoryUserDetailsManager(president, secretary, treasurer, member);
    }
}
