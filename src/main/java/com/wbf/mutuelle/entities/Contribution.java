package com.wbf.mutuelle.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity

@Table(name = "contribution")
public class Contribution {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_contribution;
    private Date payment_date;
    private BigDecimal amount;
    private String payment_mode;
    private String payment_proof;

    @ManyToMany(targetEntity = Contribution.class)
    private List<Member> members;

    @ManyToMany(targetEntity = Contribution.class)
    private List<ContributionPeriod> contribution_periods;

}