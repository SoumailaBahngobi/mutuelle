package com.wbf.mutuelle.controllers;

import com.wbf.mutuelle.entities.Contribution;
import com.wbf.mutuelle.entities.ContributionType;
import com.wbf.mutuelle.entities.Member;
import com.wbf.mutuelle.repositories.MemberRepository;
import com.wbf.mutuelle.services.ContributionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mut/contribution")
@RequiredArgsConstructor
public class ContributionController {

    private final ContributionService contributionService;
    private final MemberRepository memberRepository;

    @GetMapping
    public List<Contribution> getContributions() {
        return contributionService.getAllContributions();
    }

    @GetMapping("/{id}")
    public Contribution getContributionById(@PathVariable Long id) {
        return contributionService.getContributionById(id);
    }

    //  Créer une cotisation pour le membre connecté
    @PostMapping
    public Contribution createContribution(@RequestBody Contribution contribution,
                                           @AuthenticationPrincipal UserDetails userDetails) {

        Member connectedMember = memberRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Membre non trouvé !"));

        if (contribution.getContributionType() == null) {
            throw new RuntimeException("Le type de contribution doit être spécifié !");
        }

        if (contribution.getContributionType() == ContributionType.INDIVIDUAL) {
            // Associer seulement le membre connecté
            contribution.setMember(connectedMember);
            contribution.setMembers(null);
        } else if (contribution.getContributionType() == ContributionType.GROUP) {
            // Si c’est groupé, on laisse la liste reçue en JSON
            contribution.setMember(null);
            // 💡 Optionnel : tu pourrais ajouter le membre connecté si nécessaire
            // contribution.getMembers().add(connectedMember);
        }

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
