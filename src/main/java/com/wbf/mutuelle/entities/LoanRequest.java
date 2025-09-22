package com.wbf.mutuelle.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "loan_request")
public class LoanRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_loan_request")
    private Long id;

    @Column(name = "request_amount", nullable = false)
    private BigDecimal request_amount;

    public Boolean getRepaid() {
        return is_repaid;
    }

    public void setRepaid(Boolean repaid) {
        is_repaid = repaid;
    }

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private String status = "PENDING";

    public Boolean getIs_repaid() {
        return is_repaid;
    }

    public void setIs_repaid(Boolean is_repaid) {
        this.is_repaid = is_repaid;
    }

    @Column(name = "is_repaid")
    private Boolean is_repaid = false;

    @Temporal(TemporalType.DATE)
    @Column(name = "request_date", nullable = false)
    private Date request_date = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LoanRequest(BigDecimal request_amount, Integer duration, String reason, String status, Boolean is_repaid, Date request_date, Member member) {

        this.request_amount = request_amount;
        this.duration = duration;
        this.reason = reason;
        this.status = status;
        this.is_repaid = is_repaid;
        this.request_date = request_date;
        this.member = member;
    }

}