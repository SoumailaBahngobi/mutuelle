package com.wbf.mutuelle.controllers;

import com.wbf.mutuelle.entities.Loan;
import com.wbf.mutuelle.repositories.LoanRepository;
import com.wbf.mutuelle.services.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("mutuelle/loan")
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;

    @GetMapping
    public List<Loan> getLoans() {
        return loanService.getLoan();
    }

    @GetMapping("/{id}")
    public Loan getLoanById(@PathVariable Long id) {
        return (Loan) loanService.getLoanById(id);
    }
  @PostMapping
  public Loan createLoan(@RequestBody Loan loan) {
        return loanService.createLoan(loan);
  }

  @PostMapping("/{id}")
    public Loan updateLoan(@PathVariable Long id, @RequestBody Loan loan) {
      return loanService.updateLoan(id, loan);
  }

  @DeleteMapping("/{id}")
    public ResponseEntity<Loan> deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
  }
}
