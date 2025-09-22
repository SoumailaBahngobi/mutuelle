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
        return isRepaid;
    }

    public void setRepaid(Boolean repaid) {
        isRepaid = repaid;
    }

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private String status = "PENDING";

    @Column(name = "is_repaid")
    private Boolean isRepaid = false;

    @Temporal(TemporalType.DATE)
    @Column(name = "request_date", nullable = false)
    private Date request_date = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public LoanRequest( BigDecimal request_amount, Integer duration, String reason, String status, Boolean isRepaid, Date request_date, Member member) {

        this.request_amount = request_amount;
        this.duration = duration;
        this.reason = reason;
        this.status = status;
        this.isRepaid = isRepaid;
        this.request_date = request_date;
        this.member = member;
    }

}