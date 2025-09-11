package com.wbf.mutuelle.services;

import com.wbf.mutuelle.entities.Loan;
import com.wbf.mutuelle.repositories.LoanRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Scanner;

@Service
public class LoanService {

private final LoanRepository loanRepository;

public LoanService(LoanRepository loanRepository) {
    this.loanRepository = loanRepository;
}
    public List<Loan> getLoan() {
        return loanRepository.findAll();
    }

    public Loan createLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    public Loan updateLoan(@PathVariable Long id, @RequestBody Loan loan) {

            Loan loanu = loanRepository.findById(id).orElse(null) ;
            assert loanu != null;
            loanu.setMembers(loan.getMembers());
            loanu.setAmount(loan.getAmount());
            loanu.setDuration(loan.getDuration());
            loanu.setBegin_date(loan.getBegin_date());
            loanu.setRepayment_amount(loan.getRepayment_amount());
            return loanRepository.save(loanu);
    }

    public void deleteLoan(Long id) {
    loanRepository.deleteById(id);
    }

    public Object getLoanById(Long id) {
    return loanRepository.findById(id);
    }
}
