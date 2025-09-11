package com.wbf.mutuelle.controllers;

import com.wbf.mutuelle.entities.Contribution;
import com.wbf.mutuelle.entities.ContributionPeriod;
import com.wbf.mutuelle.entities.Member;
import com.wbf.mutuelle.services.ContributionService;
import com.wbf.mutuelle.services.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("mutuelle/contribution")
@RequiredArgsConstructor
public class ContributionController {
    private final ContributionService contributionService;
    private final LoanService loanService;

    @GetMapping
public List<Contribution> getContributions() {
        return  contributionService.getContributions();
    }


    public List<ContributionPeriod> getContributionPeriods() {
        ContributionPeriod contributionPeriodRepository = new ContributionPeriod();
        return contributionPeriodRepository.getContributionPeriod();
    }
    @GetMapping("/{id}")
    public Contribution getContributionById(@PathVariable Long id) {
        return (Contribution) loanService.getLoanById(id);
    }

    @PostMapping
   public Contribution createContribution(@RequestBody Contribution contribution) {
       return contributionService.createContribution(contribution);
    }
    @PostMapping("/{id}")
    public Contribution updateContribution(@PathVariable Long id, @RequestBody Contribution contribution) {
        return contributionService.updateContribution(contribution);
    }

}
