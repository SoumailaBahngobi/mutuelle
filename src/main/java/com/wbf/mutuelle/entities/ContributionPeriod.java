package com.wbf.mutuelle.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity

@Table(name = "contribution_period")
public class ContributionPeriod {
    @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_contribution_period;
    private Date begin_date;
    private Date end_date;
    private BigDecimal fixed_amount;
    private Boolean active;

    public ContributionPeriod() {

    }

    public List<ContributionPeriod> getContributionPeriod() {
        ContributionPeriod contributionPeriodRepository = new ContributionPeriod();
        return List.of();
    }
}
