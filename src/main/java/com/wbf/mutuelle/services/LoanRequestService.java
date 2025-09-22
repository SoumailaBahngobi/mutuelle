package com.wbf.mutuelle.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wbf.mutuelle.entities.LoanRequest;
import com.wbf.mutuelle.entities.Member;
import com.wbf.mutuelle.repositories.LoanRequestRepository;
import com.wbf.mutuelle.repositories.MemberRepository;

@Service
public class LoanRequestService {

    private final LoanRequestRepository loanRequestRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public LoanRequestService(LoanRequestRepository loanRequestRepository, MemberRepository memberRepository) {
        this.loanRequestRepository = loanRequestRepository;
        this.memberRepository = memberRepository;
    }

    public List<LoanRequest> getAllLoanRequests() {
        return loanRequestRepository.findAll();
    }

    public Optional<LoanRequest> getLoanRequestById(Long id) {
        return loanRequestRepository.findById(id);
    }

    public List<LoanRequest> getLoanRequestsByMemberId(Long memberId) {
        return loanRequestRepository.findByMemberId(memberId);
    }

    public LoanRequest createLoanRequest(LoanRequest loanRequest, String userEmail) {
        Member member = memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Membre non trouvé !"));

        // Assurez-vous que l'ID est null pour que la base de données le génère automatiquement
       // loanRequest.setId(null);

        System.out.println("ID avant set null: " + loanRequest.getId()); // Debug
        loanRequest.setId(null);
        System.out.println("ID après set null: " + loanRequest.getId());

        loanRequest.setMember(member);

        if (loanRequest.getRequest_date() == null) {
            loanRequest.setRequest_date(new java.util.Date());
        }
        if (loanRequest.getStatus() == null) {
            loanRequest.setStatus("PENDING");
        }
      /*  if (loanRequest.getIsRepaid() == null) {
            loanRequest.setIsRepaid(false);
        }*/
if (loanRequest.getIs_repaid() == null )  {
    loanRequest.setIs_repaid(false);

}

        return loanRequestRepository.save(loanRequest);
    }

    public LoanRequest updateLoanRequest(Long id, LoanRequest updatedRequest) {
        return loanRequestRepository.findById(id).map(existingRequest -> {
            existingRequest.setRequest_amount(updatedRequest.getRequest_amount());
            existingRequest.setDuration(updatedRequest.getDuration());
            existingRequest.setReason(updatedRequest.getReason());
            existingRequest.setStatus(updatedRequest.getStatus());
            existingRequest.setIs_repaid(updatedRequest.getIs_repaid());
           // existingRequest.setIsRepaid(updatedRequest.getIsRepaid());
            return loanRequestRepository.save(existingRequest);
        }).orElseThrow(() -> new RuntimeException("Demande de prêt non trouvée avec l'id " + id));
    }

    public void deleteLoanRequest(Long id) {
        loanRequestRepository.deleteById(id);
    }

    public LoanRequest approveLoanRequest(Long id) {
        LoanRequest request = loanRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande de prêt non trouvée avec l'id " + id));

        request.setStatus("APPROVED");
        return loanRequestRepository.save(request);
    }

    public LoanRequest rejectLoanRequest(Long id) {
        LoanRequest request = loanRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande de prêt non trouvée avec l'id " + id));

        request.setStatus("REJECTED");
        return loanRequestRepository.save(request);
    }
}