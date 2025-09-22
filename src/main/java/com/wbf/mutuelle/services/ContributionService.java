package com.wbf.mutuelle.services;

import com.wbf.mutuelle.entities.Contribution;
import com.wbf.mutuelle.repositories.ContributionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContributionService {

    private final ContributionRepository contributionRepository;

    //  Récupérer toutes les contributions
    public List<Contribution> getAllContributions() {
        return contributionRepository.findAll();
    }

    //  Récupérer une contribution par ID
    public Contribution getContributionById(Long id) {
        return contributionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contribution non trouvée avec l'id " + id));
    }


    // Créer une nouvelle contribution
    public Contribution createContribution(Contribution contribution) {
        return contributionRepository.save(contribution);
    }

    //  Mettre à jour une contribution
    public Contribution updateContribution(Long id, Contribution updatedContribution) {
        return contributionRepository.findById(id).map(existingContribution -> {
            existingContribution.setAmount(updatedContribution.getAmount());
            existingContribution.setPaymentDate(updatedContribution.getPaymentDate());
            existingContribution.setPaymentMode(updatedContribution.getPaymentMode());
            existingContribution.setPaymentProof(updatedContribution.getPaymentProof());
            existingContribution.setContributionPeriod(updatedContribution.getContributionPeriod());
            existingContribution.setMembers(updatedContribution.getMembers());
            existingContribution.setType(updatedContribution.getType()); // ⚡ ajouter le type
            existingContribution.setMember(updatedContribution.getMember()); // ⚡ utile pour INDIVIDUELLE
            return contributionRepository.save(existingContribution);
        }).orElseThrow(() -> new RuntimeException("Contribution non trouvée avec l'id " + id));
    }

    //  Supprimer une contribution
    public void deleteContribution(Long id) {
        if (!contributionRepository.existsById(id)) {
            throw new RuntimeException("Impossible de supprimer : contribution avec id " + id + " inexistante.");
        }
        contributionRepository.deleteById(id);
    }


}
