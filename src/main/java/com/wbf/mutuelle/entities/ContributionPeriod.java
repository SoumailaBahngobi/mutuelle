package com.wbf.mutuelle.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "contribution_period")
@Getter
@Setter
public class ContributionPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    // Montant de la cotisation individuelle pour cette période
    private BigDecimal individualAmount;

    // Dates de début et fin de la période
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private boolean active;

    // Constructeurs
    public ContributionPeriod() {
    }

    public ContributionPeriod(String name, BigDecimal individualAmount, Date startDate, Date endDate) {
        this.name = name;
        this.individualAmount = individualAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = true;
    }
}