package com.wbf.mutuelle.entities;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "loan")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Loan(Long id, BigDecimal amount, Integer duration, Date begin_date, BigDecimal repayment_amount, List<Member> members) {
        this.id =id;
        this.amount = amount;
        this.duration = duration;
        this.begin_date = begin_date;
        this.repayment_amount = repayment_amount;
        this.members = (Member) members;
    }

    private BigDecimal amount;
    private Integer duration;
    private Date begin_date;
    private BigDecimal repayment_amount;
    @OneToOne(targetEntity = Member.class)
    private Member members;


    public Loan() {

    }
}
