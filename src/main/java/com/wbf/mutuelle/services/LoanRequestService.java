package com.wbf.mutuelle.services;

import com.wbf.mutuelle.entities.LoanRequest;
import com.wbf.mutuelle.repositories.LoanRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanRequestService {

    private final LoanRequestRepository loanRequestRepository;

    public LoanRequestService(LoanRequestRepository loanRequestRepository) {
        this.loanRequestRepository = loanRequestRepository;
    }

    public List<LoanRequest> getAllLoanRequests() {
        return loanRequestRepository.findAll();
    }

    public Optional<LoanRequest> getLoanRequestById(Long id) {
        return loanRequestRepository.findById(id);
    }

    public LoanRequest createLoanRequest(LoanRequest loanRequest) {
        return loanRequestRepository.save(loanRequest);
    }

    public LoanRequest updateLoanRequest(Long id, LoanRequest updatedRequest) {
        return loanRequestRepository.findById(id).map(existingRequest -> {
            existingRequest.setRequest_amount(updatedRequest.getRequest_amount());
            existingRequest.setDuration(updatedRequest.getDuration());
            existingRequest.setReason(updatedRequest.getReason());
            existingRequest.setStatus(updatedRequest.getStatus());
            existingRequest.setRequest_date(updatedRequest.getRequest_date());
            existingRequest.setMember(updatedRequest.getMember());
            return loanRequestRepository.save(existingRequest);
        }).orElseThrow(() -> new RuntimeException("LoanRequest not found with id " + id));
    }

    public void deleteLoanRequest(Long id) {
        loanRequestRepository.deleteById(id);
    }
}
