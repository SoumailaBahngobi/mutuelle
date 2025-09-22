package com.wbf.mutuelle.services;

import com.wbf.mutuelle.entities.Contribution;
import com.wbf.mutuelle.entities.ContributionType;
import com.wbf.mutuelle.repositories.ContributionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContributionService {

    private final ContributionRepository contributionRepository;

    public List<Contribution> getAllContributions() {
        try {
            return contributionRepository.findAll();
        } catch (Exception e) {
            log.error("Erreur lors de la récupération de toutes les contributions", e);
            return Collections.emptyList();
        }
    }

    public Contribution getContributionById(Long id) {
        return contributionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contribution non trouvée avec l'ID : " + id));
    }

    public Contribution createContribution(Contribution contribution) {
        try {
            // Validation supplémentaire
            if (contribution.getAmount() == null || contribution.getAmount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("Le montant de la contribution doit être positif !");
            }

            if (contribution.getPaymentDate() == null) {
                contribution.setPaymentDate(new java.util.Date());
            }

            return contributionRepository.save(contribution);
        } catch (Exception e) {
            log.error("Erreur lors de la création de la contribution", e);
            throw new RuntimeException("Erreur lors de la création de la contribution : " + e.getMessage());
        }
    }

    public Contribution updateContribution(Long id, Contribution contributionDetails) {
        try {
            Contribution contribution = getContributionById(id);

            // Mettre à jour les champs modifiables
            if (contributionDetails.getAmount() != null) {
                contribution.setAmount(contributionDetails.getAmount());
            }
            if (contributionDetails.getPaymentDate() != null) {
                contribution.setPaymentDate(contributionDetails.getPaymentDate());
            }
            if (contributionDetails.getPaymentMode() != null) {
                contribution.setPaymentMode(contributionDetails.getPaymentMode());
            }
            if (contributionDetails.getPaymentProof() != null) {
                contribution.setPaymentProof(contributionDetails.getPaymentProof());
            }

            return contributionRepository.save(contribution);
        } catch (Exception e) {
            log.error("Erreur lors de la mise à jour de la contribution ID: " + id, e);
            throw new RuntimeException("Erreur lors de la mise à jour de la contribution : " + e.getMessage());
        }
    }

    public void deleteContribution(Long id) {
        try {
            Contribution contribution = getContributionById(id);
            contributionRepository.delete(contribution);
        } catch (Exception e) {
            log.error("Erreur lors de la suppression de la contribution ID: " + id, e);
            throw new RuntimeException("Erreur lors de la suppression de la contribution : " + e.getMessage());
        }
    }

    // Méthodes pour les types spécifiques
    public List<Contribution> getContributionsByType(ContributionType contributionType) {
        try {
            return contributionRepository.findByContributionType(contributionType);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des contributions par type: " + contributionType, e);
            return Collections.emptyList();
        }
    }

    public List<Contribution> getIndividualContributionsByMember(Long memberId) {
        try {
            return contributionRepository.findByContributionTypeAndMemberId(ContributionType.INDIVIDUAL, memberId);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des contributions individuelles du membre ID: " + memberId, e);
            return Collections.emptyList();
        }
    }

    public List<Contribution> getGroupContributionsByMember(Long memberId) {
        try {
            return contributionRepository.findGroupContributionsByMemberId(ContributionType.GROUP, memberId);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des contributions groupées du membre ID: " + memberId, e);
            return Collections.emptyList();
        }
    }
}