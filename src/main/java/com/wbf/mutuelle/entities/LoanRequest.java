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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_loan_request;

    private BigDecimal request_amount;
    private Integer duration;
    private String reason;
    private String status;

    @Temporal(TemporalType.DATE)
    private Date request_date;

    @OneToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;
}
