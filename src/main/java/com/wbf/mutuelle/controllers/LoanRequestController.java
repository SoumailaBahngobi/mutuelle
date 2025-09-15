package com.wbf.mutuelle.controllers;

import com.wbf.mutuelle.entities.LoanRequest;
import com.wbf.mutuelle.services.LoanRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("loan_request")

@RequiredArgsConstructor
public class LoanRequestController {

    private final LoanRequestService loanRequestService;

    @GetMapping
    public List<LoanRequest> getAllLoanRequests() {
        return loanRequestService.getAllLoanRequests();
    }

    @GetMapping("/{id}")
    public LoanRequest getLoanRequestById(@PathVariable Long id) {
        return loanRequestService.getLoanRequestById(id)
                .orElseThrow(() -> new RuntimeException("LoanRequest not found with id " + id));
    }

    @PostMapping
    public LoanRequest createLoanRequest(@RequestBody LoanRequest loanRequest) {
        return loanRequestService.createLoanRequest(loanRequest);
    }

    @PutMapping("/{id}")
    public LoanRequest updateLoanRequest(@PathVariable Long id, @RequestBody LoanRequest loanRequest) {
        return loanRequestService.updateLoanRequest(id, loanRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteLoanRequest(@PathVariable Long id) {
        loanRequestService.deleteLoanRequest(id);
    }
}
