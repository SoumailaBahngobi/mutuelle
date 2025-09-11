package com.wbf.mutuelle.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "loan_request")
public class LoanRequest {

    public LoanRequest(Long id_loan_request, BigDecimal request_amount, Integer duration, String reason, String status, Date request_date, Member member) {
        this.id_loan_request = id_loan_request;
        this.request_amount = request_amount;
        this.duration = duration;
        this.reason = reason;
        this.status = status;
        this.request_date = request_date;
        this.member = member;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_loan_request;
    private BigDecimal request_amount;
    private Integer duration;
    private String reason;
    private String status;
    private Date request_date;

    @OneToOne(targetEntity = LoanRequest.class)

    private Member member;
}
