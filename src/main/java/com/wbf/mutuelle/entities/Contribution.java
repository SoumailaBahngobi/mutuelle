package com.wbf.mutuelle.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "contribution")
public class Contribution {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    private ContributionType contributionType;

    @Setter
    @Temporal(TemporalType.DATE)
    private Date paymentDate;

    private BigDecimal amount;
    private String paymentMode;
    private String paymentProof;

    // Type de cotisation (individuelle ou groupée)
    @Enumerated(EnumType.STRING)
    private ContributionType type;

    //Cas INDIVIDUELLE
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = true)
    private Member member;

    //Cas GROUPEE
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

    // Remove this conflicting setter - DELETE THIS METHOD
    /*
    public void setMembers(Object o) {
    }
    */

    // Keep only this proper setter
    public void setMembers(List<Member> members) {
        this.members = members;
    }

    // Your other getters and setters remain the same...
    public ContributionType getContributionType() {
        return contributionType;
    }

    public void setContributionType(ContributionType contributionType) {
        this.contributionType = contributionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentProof() {
        return paymentProof;
    }

    public void setPaymentProof(String paymentProof) {
        this.paymentProof = paymentProof;
    }

    public ContributionType getType() {
        return type;
    }

    public void setType(ContributionType type) {
        this.type = type;
    }

    public Member getMember() {
        return member;
    }

    public List<Member> getMembers() {
        return members;
    }

    public ContributionPeriod getContributionPeriod() {
        return contributionPeriod;
    }

    public void setContributionPeriod(ContributionPeriod contributionPeriod) {
        this.contributionPeriod = contributionPeriod;
    }

    public void setMember(Member connectedMember) {
        this.member = connectedMember;
    }
}