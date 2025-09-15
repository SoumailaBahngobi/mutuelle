package com.wbf.mutuelle.controllers;

import com.wbf.mutuelle.entities.Contribution;
import com.wbf.mutuelle.services.ContributionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("contribution")
@RequiredArgsConstructor
public class ContributionController {

    private final ContributionService contributionService;

    @GetMapping
    public List<Contribution> getContributions() {
        return contributionService.getAllContributions();
    }

    @GetMapping("/{id}")
    public Contribution getContributionById(@PathVariable Long id) {
        return contributionService.getContributionById(id)
                .orElseThrow(() -> new RuntimeException("Contribution not found with id " + id));
    }

    @PostMapping
    public Contribution createContribution(@RequestBody Contribution contribution) {
        return contributionService.createContribution(contribution);
    }

    @PutMapping("/{id}")
    public Contribution updateContribution(@PathVariable Long id, @RequestBody Contribution contribution) {
        return contributionService.updateContribution(id, contribution);
    }

    @DeleteMapping("/{id}")
    public void deleteContribution(@PathVariable Long id) {
        contributionService.deleteContribution(id);
    }
}
