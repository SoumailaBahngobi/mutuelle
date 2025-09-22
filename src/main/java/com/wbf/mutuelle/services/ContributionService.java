package com.wbf.mutuelle.services;

import com.wbf.mutuelle.entities.Contribution;
import com.wbf.mutuelle.entities.ContributionPeriod;
import com.wbf.mutuelle.entities.ContributionType;
import com.wbf.mutuelle.repositories.ContributionRepository;
import com.wbf.mutuelle.repositories.ContributionPeriodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContributionService {

    private final ContributionRepository contributionRepository;
    private final ContributionPeriodRepository contributionPeriodRepository;

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


    public Contribution updateContribution(Long id, Contribution contributionDetails) {
        try {
            Contribution contribution = getContributionById(id);

            // Mettre à jour les champs modifiables
            if (contributionDetails.getPaymentDate() != null) {
                contribution.setPaymentDate(contributionDetails.getPaymentDate());
            }
            if (contributionDetails.getPaymentMode() != null) {
                contribution.setPaymentMode(contributionDetails.getPaymentMode());
            }
            if (contributionDetails.getPaymentProof() != null) {
                contribution.setPaymentProof(contributionDetails.getPaymentProof());
            }

            // Si la période change, recalculer le montant
            if (contributionDetails.getContributionPeriod() != null &&
                    contributionDetails.getContributionPeriod().getId() != null &&
                    !contributionDetails.getContributionPeriod().getId().equals(contribution.getContributionPeriod().getId())) {

                ContributionPeriod newPeriod = contributionPeriodRepository.findById(contributionDetails.getContributionPeriod().getId())
                        .orElseThrow(() -> new RuntimeException("Nouvelle période non trouvée !"));

                BigDecimal newAmount = calculateContributionAmount(contribution, newPeriod);
                contribution.setAmount(newAmount);
                contribution.setContributionPeriod(newPeriod);
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
            // Implémentation simplifiée avec filtrage en mémoire
            List<Contribution> allGroupContributions = contributionRepository.findByContributionType(ContributionType.GROUP);
            return allGroupContributions.stream()
                    .filter(contribution -> contribution.getMembers() != null)
                    .filter(contribution -> contribution.getMembers().stream()
                            .anyMatch(member -> member.getId().equals(memberId)))
                    .toList();
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des contributions groupées du membre ID: " + memberId, e);
            return Collections.emptyList();
        }
    }

    /**
     * Calcule le montant total de toutes les contributions
     */
    public BigDecimal getTotalContributionsAmount() {
        try {
            List<Contribution> allContributions = contributionRepository.findAll();
            return allContributions.stream()
                    .map(Contribution::getAmount)
                    .filter(amount -> amount != null)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (Exception e) {
            log.error("Erreur lors du calcul du montant total des contributions", e);
            return BigDecimal.ZERO;
        }
    }

    /**
     * Calcule le montant total des contributions par membre
     */
    public BigDecimal getTotalContributionsAmountByMember(Long memberId) {
        try {
            // Contributions individuelles du membre
            List<Contribution> individualContributions = getIndividualContributionsByMember(memberId);
            BigDecimal individualTotal = individualContributions.stream()
                    .map(Contribution::getAmount)
                    .filter(amount -> amount != null)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Contributions groupées du membre
            List<Contribution> groupContributions = getGroupContributionsByMember(memberId);
            BigDecimal groupTotal = groupContributions.stream()
                    .map(Contribution::getAmount)
                    .filter(amount -> amount != null)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            return individualTotal.add(groupTotal);
        } catch (Exception e) {
            log.error("Erreur lors du calcul du montant total des contributions du membre ID: " + memberId, e);
            return BigDecimal.ZERO;
        }
    }

    /**
     * Calcule le montant total par type de contribution
     */
    public BigDecimal getTotalAmountByType(ContributionType contributionType) {
        try {
            List<Contribution> contributions = getContributionsByType(contributionType);
            return contributions.stream()
                    .map(Contribution::getAmount)
                    .filter(amount -> amount != null)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (Exception e) {
            log.error("Erreur lors du calcul du montant total par type: " + contributionType, e);
            return BigDecimal.ZERO;
        }
    }

    public Contribution createContribution(Contribution contribution) {
        try {
            // Validation de base
            if (contribution.getContributionPeriod() == null || contribution.getContributionPeriod().getId() == null) {
                throw new RuntimeException("La période de contribution doit être spécifiée !");
            }

            // Récupérer la période complète avec le montant individuel
            ContributionPeriod period = contributionPeriodRepository.findById(contribution.getContributionPeriod().getId())
                    .orElseThrow(() -> new RuntimeException("Période de contribution non trouvée !"));

            // Calculer le montant selon le type de contribution
            BigDecimal calculatedAmount = calculateContributionAmount(contribution, period);
            contribution.setAmount(calculatedAmount);

            // Date de paiement par défaut
            if (contribution.getPaymentDate() == null) {
                contribution.setPaymentDate(new java.util.Date());
            }

            return contributionRepository.save(contribution);

        } catch (Exception e) {
            log.error("Erreur lors de la création de la contribution", e);
            throw new RuntimeException("Erreur lors de la création de la contribution : " + e.getMessage());
        }
    }

    /**
     * Calcule le montant de la contribution selon le type
     */
    private BigDecimal calculateContributionAmount(Contribution contribution, ContributionPeriod period) {
        BigDecimal individualAmount = period.getIndividualAmount();

        if (individualAmount == null || individualAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Le montant individuel de la période n'est pas défini ou invalide !");
        }

        if (contribution.getContributionType() == ContributionType.INDIVIDUAL) {
            // Cotisation individuelle : montant individuel
            return individualAmount;

        } else if (contribution.getContributionType() == ContributionType.GROUP) {
            // Cotisation groupée : montant individuel × nombre de membres
            if (contribution.getMembers() == null || contribution.getMembers().isEmpty()) {
                throw new RuntimeException("Une cotisation groupée doit avoir au moins un membre !");
            }

            int numberOfMembers = contribution.getMembers().size();
            return individualAmount.multiply(BigDecimal.valueOf(numberOfMembers));

        } else {
            throw new RuntimeException("Type de contribution non supporté !");
        }
    }

    public BigDecimal calculateMemberBalance(Long memberId) {
        List<Contribution> contributions = contributionRepository.findByMemberId(memberId);
        return contributions.stream()
                .map(Contribution::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Calcule la balance totale de toutes les cotisations
    public BigDecimal calculateTotalBalance() {
        List<Contribution> allContributions = contributionRepository.findAll();
        return allContributions.stream()
                .map(Contribution::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Met à jour la balance d'une cotisation après sauvegarde
    public Contribution saveContributionWithBalance(Contribution contribution) {
        Contribution savedContribution = contributionRepository.save(contribution);

        // Calculer et définir la balance
        BigDecimal balance = calculateMemberBalance(savedContribution.getMember().getId());
        savedContribution.setBalance(balance);

        return savedContribution;
    }

}