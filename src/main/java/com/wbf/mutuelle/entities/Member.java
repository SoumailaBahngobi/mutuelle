package com.wbf.mutuelle.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String firstName;
    private String email;
    private String password;
    private String npi;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Member() {
    }

    public Member(Long id, String name, String firstName, String email,
                  String password, String npi, String phone, Role role) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.npi = npi;
        this.phone = phone;
        this.role = role;
    }
}
