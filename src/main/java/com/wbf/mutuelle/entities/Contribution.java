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

    @Temporal(TemporalType.DATE)
    private Date payment_date;

    private BigDecimal amount;
    private String payment_mode;
    private String payment_proof;

    @ManyToMany
    @JoinTable(
            name = "contribution_member",
            joinColumns = @JoinColumn(name = "contribution_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private List<Member> members;

    @ManyToMany
    @JoinTable(
            name = "contribution_contribution_period",
            joinColumns = @JoinColumn(name = "contribution_id"),
            inverseJoinColumns = @JoinColumn(name = "contribution_period_id")
    )
    private List<ContributionPeriod> contribution_periods;

    // --- Getters & Setters ---

    public Long getId_contribution() {
        return id_contribution;
    }

    public void setId_contribution(Long id_contribution) {
        this.id_contribution = id_contribution;
    }

    public Date getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(Date payment_date) {
        this.payment_date = payment_date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getPayment_proof() {
        return payment_proof;
    }

    public void setPayment_proof(String payment_proof) {
        this.payment_proof = payment_proof;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public List<ContributionPeriod> getContribution_periods() {
        return contribution_periods;
    }

    public void setContribution_periods(List<ContributionPeriod> contribution_periods) {
        this.contribution_periods = contribution_periods;
    }
}
