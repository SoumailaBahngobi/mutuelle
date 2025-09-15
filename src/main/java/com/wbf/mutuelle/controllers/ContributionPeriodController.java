package com.wbf.mutuelle.controllers;

import com.wbf.mutuelle.entities.ContributionPeriod;
import com.wbf.mutuelle.repositories.ContributionPeriodRepository;
import com.wbf.mutuelle.services.ContributionPeriodService;
import com.wbf.mutuelle.services.ContributionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("contribution_period")
@RequiredArgsConstructor

public class ContributionPeriodController {

    private final ContributionPeriodService contributionPeriodService;
    private final ContributionService contributionService;

    @GetMapping
public List<ContributionPeriod> getContributionPeriods() {
 return contributionPeriodService.getContribution_periods();
 }
 @GetMapping("/{id}")
    public ContributionPeriod getContributionPeriodById(@PathVariable Long id) {
        return contributionPeriodService.getContribution_periodById(id);
 }

 //@PostMapping("/contribution_period")
 @PostMapping("")
  public ContributionPeriod createContributionPeriod(@RequestBody ContributionPeriod contributionPeriod) {
        return contributionPeriodService.createContribution_period(contributionPeriod);
 }

 @PostMapping("/{id}")
    public ContributionPeriod updateContributionPeriod(@PathVariable Long id, @RequestBody ContributionPeriod contributionPeriod) {
        return contributionPeriodService.updateContributionPeriod(contributionPeriod);
 }

 @DeleteMapping("/{id}")
  public void deleteContributionPeriod(@PathVariable Long id) {
        contributionPeriodService.deleteContribution_period(id);
 }

}
