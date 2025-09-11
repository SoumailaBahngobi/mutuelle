package com.wbf.mutuelle.services;

import com.wbf.mutuelle.entities.ContributionPeriod;
import com.wbf.mutuelle.repositories.ContributionPeriodRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ContributionPeriodService {
    private final ContributionPeriodRepository contribution_period_repository;

    public ContributionPeriodService(ContributionPeriodRepository contribution_period_repository)
    {
        this.contribution_period_repository = contribution_period_repository;
    }
    public List<ContributionPeriod> getContribution_periods()
    {
        return contribution_period_repository.findAll();
    }
    public ContributionPeriod getContribution_periodById(Long id)
    {
        return contribution_period_repository.findById(id).orElse(null);
    }

    public ContributionPeriod createContribution_period(ContributionPeriod contribution_period)
    {
        return contribution_period_repository.save(contribution_period);
    }

    public ContributionPeriod updateContributionPeriod(ContributionPeriod contribution_period)
    {
        return contribution_period_repository.save(contribution_period);
    }
    public void deleteContribution_period(Long id)
    {
        contribution_period_repository.deleteById(id);
    }
}
