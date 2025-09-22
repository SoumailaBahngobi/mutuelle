package com.wbf.mutuelle.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "contribution_period")
public class ContributionPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_contribution_period")
    private Long id;

    private Date beginDate;
    private Date endDate;
    private BigDecimal fixedAmount;
    private Boolean active;

    public ContributionPeriod() { }
}
