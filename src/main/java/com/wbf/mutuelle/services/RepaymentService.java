package com.wbf.mutuelle.services;

import com.wbf.mutuelle.entities.Repayment;
import com.wbf.mutuelle.repositories.RepaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RepaymentService {

    private final RepaymentRepository repaymentRepository;

    public RepaymentService(RepaymentRepository repaymentRepository) {
        this.repaymentRepository = repaymentRepository;
    }

    public List<Repayment> getAllRepayments() {
        return repaymentRepository.findAll();
    }

    public Optional<Repayment> getRepaymentById(Long id) {
        return repaymentRepository.findById(id);
    }

    public Repayment createRepayment(Repayment repayment) {
        return repaymentRepository.save(repayment);
    }

    public Repayment updateRepayment(Long id, Repayment updatedRepayment) {
        return repaymentRepository.findById(id).map(existing -> {
            existing.setAmount(updatedRepayment.getAmount());
            existing.setDuration(updatedRepayment.getDuration());
            existing.setReason(updatedRepayment.getReason());
            existing.setStatus(updatedRepayment.getStatus());
            existing.setRepayment_date(updatedRepayment.getRepayment_date());
            existing.setLoans(updatedRepayment.getLoans());
            return repaymentRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Repayment not found with id " + id));
    }

    public void deleteRepayment(Long id) {
        repaymentRepository.deleteById(id);
    }
}
