package com.wbf.mutuelle.controllers;

import com.wbf.mutuelle.entities.Contribution;
import com.wbf.mutuelle.entities.ContributionType;
import com.wbf.mutuelle.entities.Member;
import com.wbf.mutuelle.repositories.MemberRepository;
import com.wbf.mutuelle.services.ContributionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/mut/contribution")
@RequiredArgsConstructor
public class ContributionController {

    private final ContributionService contributionService;
    private final MemberRepository memberRepository;

    @GetMapping
    public ResponseEntity<List<Contribution>> getAllContributions() {
        try {
            List<Contribution> contributions = contributionService.getAllContributions();
            return ResponseEntity.ok(contributions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contribution> getContributionById(@PathVariable Long id) {
        try {
            Contribution contribution = contributionService.getContributionById(id);
            return ResponseEntity.ok(contribution);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Créer une cotisation INDIVIDUELLE
    @PostMapping("/individual")
    public ResponseEntity<?> createIndividualContribution(@RequestBody Contribution contribution,
                                                          @AuthenticationPrincipal UserDetails userDetails) {

        try {
            Member connectedMember = memberRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Membre non trouvé !"));

            // Forcer le type individuel
            contribution.setContributionType(ContributionType.INDIVIDUAL);

            Contribution createdContribution = createIndividualContribution(contribution, connectedMember);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdContribution);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur serveur");
        }
    }

    // Créer une cotisation GROUPÉE
    @PostMapping("/group")
    public ResponseEntity<?> createGroupContribution(@RequestBody Contribution contribution,
                                                     @AuthenticationPrincipal UserDetails userDetails) {

        try {
            Member connectedMember = memberRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Membre non trouvé !"));

            // Forcer le type groupé
            contribution.setContributionType(ContributionType.GROUP);

            Contribution createdContribution = createGroupContribution(contribution, connectedMember);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdContribution);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur serveur");
        }
    }

    /**
     * Crée une cotisation individuelle
     */
    private Contribution createIndividualContribution(Contribution contribution, Member connectedMember) {
        // Validation pour individuel
        if (contribution.getMembers() != null && !contribution.getMembers().isEmpty()) {
            throw new RuntimeException("Une cotisation individuelle ne peut pas avoir une liste de membres !");
        }

        // Configuration pour individuel
        contribution.setMember(connectedMember); // Le membre connecté est le contributeur
        contribution.setMembers(null); // S'assurer que la liste est null

        return contributionService.createContribution(contribution);
    }

    /**
     * Crée une cotisation groupée
     */
    private Contribution createGroupContribution(Contribution contribution, Member connectedMember) {
        // Validation pour groupé
        if (contribution.getMember() != null) {
            throw new RuntimeException("Une cotisation groupée ne peut pas avoir un membre individuel !");
        }

        // Initialiser la liste si elle est null
        if (contribution.getMembers() == null) {
            contribution.setMembers(new ArrayList<>());
        }

        // S'assurer que le membre connecté est inclus dans la liste
        boolean connectedMemberInList = contribution.getMembers().stream()
                .anyMatch(m -> m.getId() != null && m.getId().equals(connectedMember.getId()));

        if (!connectedMemberInList) {
            // Ajouter le membre connecté à la liste
            Member memberRef = new Member();
            memberRef.setId(connectedMember.getId());
            contribution.getMembers().add(memberRef);
        }

        // S'assurer qu'il y a au moins 2 membres pour une cotisation groupée
        if (contribution.getMembers().size() < 2) {
            throw new RuntimeException("Une cotisation groupée doit concerner au moins 2 membres !");
        }

        return contributionService.createContribution(contribution);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateContribution(@PathVariable Long id, @RequestBody Contribution contribution) {
        try {
            Contribution updatedContribution = contributionService.updateContribution(id, contribution);
            return ResponseEntity.ok(updatedContribution);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la mise à jour");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContribution(@PathVariable Long id) {
        try {
            contributionService.deleteContribution(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Obtenir toutes les contributions INDIVIDUELLES
    @GetMapping("/individual")
    public ResponseEntity<List<Contribution>> getIndividualContributions() {
        try {
            List<Contribution> contributions = contributionService.getContributionsByType(ContributionType.INDIVIDUAL);
            return ResponseEntity.ok(contributions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtenir toutes les contributions GROUPÉES
    @GetMapping("/group")
    public ResponseEntity<List<Contribution>> getGroupContributions() {
        try {
            List<Contribution> contributions = contributionService.getContributionsByType(ContributionType.GROUP);
            return ResponseEntity.ok(contributions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtenir les contributions individuelles du membre connecté
    @GetMapping("/individual/my-contributions")
    public ResponseEntity<List<Contribution>> getMyIndividualContributions(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            Member connectedMember = memberRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Membre non trouvé !"));

            List<Contribution> contributions = contributionService.getIndividualContributionsByMember(connectedMember.getId());
            return ResponseEntity.ok(contributions);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }

    }

    // Obtenir les contributions groupées du membre connecté
    @GetMapping("/group/my-contributions")
    public ResponseEntity<List<Contribution>> getMyGroupContributions(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            Member connectedMember = memberRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Membre non trouvé !"));

            List<Contribution> contributions = contributionService.getGroupContributionsByMember(connectedMember.getId());
            return ResponseEntity.ok(contributions);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }
}