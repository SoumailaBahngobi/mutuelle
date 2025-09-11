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

    public List<Contribution> getAllContributions() {
        return contributionRepository.findAll();
    }

    public Optional<Contribution> getContributionById(long id) {
        return contributionRepository.findById(id);
    }

    public Contribution createContribution(Contribution contribution) {
        return contributionRepository.save(contribution);
    }

    public Contribution updateContribution(Long id, Contribution updatedContribution) {
        return contributionRepository.findById(id).map(existingContribution -> {
            existingContribution.setAmount(updatedContribution.getAmount());
            existingContribution.setPayment_date(updatedContribution.getPayment_date());
            existingContribution.setPayment_mode(updatedContribution.getPayment_mode());
            existingContribution.setPayment_proof(updatedContribution.getPayment_proof());
            existingContribution.setMembers(updatedContribution.getMembers());
            existingContribution.setContribution_periods(updatedContribution.getContribution_periods());
            return contributionRepository.save(existingContribution);
        }).orElseThrow(() -> new RuntimeException("Contribution not found with id " + id));
    }

    public void deleteContribution(long id) {
        contributionRepository.deleteById(id);
    }
}
