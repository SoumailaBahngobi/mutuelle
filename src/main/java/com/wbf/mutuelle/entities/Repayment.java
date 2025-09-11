package com.wbf.mutuelle.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Entity
@Table(name = "repayment")
public class Repayment {
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    @Setter
    private BigDecimal amount;
    @Setter
    private Integer duration;
    private String reason;

    public Repayment(Long id, BigDecimal amount, Integer duration, String reason, String status, Date repayment_date, List<Loan> loans) {
        this.id = id;
        this.amount = amount;
        this.duration = duration;
        this.reason = reason;
        this.status = status;
        this.repayment_date = repayment_date;
        this.loans = loans;
    }

    public void setPattern(String reason) {
        this.reason = reason;
    }

    @Setter
    private String status;
    @Setter
    private Date repayment_date;
    @Setter
    @OneToMany(targetEntity = Repayment.class)
    private List<Loan>loans;

}
