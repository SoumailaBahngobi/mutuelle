package com.wbf.mutuelle.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "repayment")
public class Repayment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal amount;
    private Integer duration;
    private String reason;
    private String status;

    @Temporal(TemporalType.DATE)
    private Date repayment_date;

    @OneToMany
    @JoinColumn(name = "id")
    private List<Loan> loans;


}
