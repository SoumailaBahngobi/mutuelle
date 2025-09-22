package com.wbf.mutuelle.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "contribution")
@Getter
@Setter
public class Contribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "contribution_type")
    private ContributionType contributionType;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date paymentDate;

    private BigDecimal amount;

    private String paymentMode;

    private String paymentProof;

    // Cas INDIVIDUELLE
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // Cas GROUPEE
    @ManyToMany
    @JoinTable(
            name = "contribution_members",
            joinColumns = @JoinColumn(name = "contribution_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private List<Member> members;

    // Une cotisation est toujours rattachée à une période
    @ManyToOne
    @JoinColumn(name = "contribution_period_id")
    private ContributionPeriod contributionPeriod;

    // Constructeurs
    public Contribution() {
    }

    public Contribution(ContributionType contributionType, BigDecimal amount, Date paymentDate) {
        this.contributionType = contributionType;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }
}