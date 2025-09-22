package com.wbf.mutuelle.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wbf.mutuelle.entities.LoanRequest;
import com.wbf.mutuelle.services.LoanRequestService;

@RestController
@RequestMapping("/mut/loan_request")
public class LoanRequestController {

    private final LoanRequestService loanRequestService;

    public LoanRequestController(LoanRequestService loanRequestService) {
        this.loanRequestService = loanRequestService;
    }

    @GetMapping
    public List<LoanRequest> getAllLoanRequests() {
        return loanRequestService.getAllLoanRequests();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanRequest> getLoanRequestById(@PathVariable Long id) {
        return loanRequestService.getLoanRequestById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/my_requests")
    public List<LoanRequest> getMyLoanRequests(@AuthenticationPrincipal UserDetails userDetails) {
        // Cette méthode nécessiterait une relation entre UserDetails et Member
        // Pour simplifier, nous allons créer un endpoint pour récupérer par email
        // Dans une implémentation réelle, vous devriez avoir un service pour récupérer le membre par email
        return List.of(); // Placeholder
    }

    @PostMapping
    public LoanRequest createLoanRequest(@RequestBody LoanRequest loanRequest,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new RuntimeException("Utilisateur non authentifié !");
        }

        return loanRequestService.createLoanRequest(loanRequest, userDetails.getUsername());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanRequest> updateLoanRequest(@PathVariable Long id, @RequestBody LoanRequest loanRequest) {
        try {
            return ResponseEntity.ok(loanRequestService.updateLoanRequest(id, loanRequest));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoanRequest(@PathVariable Long id) {
        try {
            loanRequestService.deleteLoanRequest(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<LoanRequest> approveLoanRequest(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(loanRequestService.approveLoanRequest(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<LoanRequest> rejectLoanRequest(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(loanRequestService.rejectLoanRequest(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}