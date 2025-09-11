package com.wbf.mutuelle.services;

import com.wbf.mutuelle.entities.Contribution;
import com.wbf.mutuelle.repositories.ContributionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContributionService {
    private final ContributionRepository contributionRepository;

    public ContributionService(ContributionRepository contributionRepository) {
        this.contributionRepository = contributionRepository;
    }

    public List<Contribution> getContributions() {

        return contributionRepository.findAll();
    }

    public Contribution createContribution(Contribution contribution) {
        return contributionRepository.save(contribution);
    }

    public Contribution updateContribution(Contribution contribution) {
        Contribution updatedContribution = contributionRepository.save(contribution);
        return contributionRepository.save(contribution);
    }

    public void deleteContribution(Contribution contribution) {
        contributionRepository.delete(contribution);
    }

    public Optional<Contribution> getContribution(Long id) {
        return contributionRepository.findById(id);
    }

    public Object getContributionById(Long id) {
        return contributionRepository.findById(id);
    }
}
