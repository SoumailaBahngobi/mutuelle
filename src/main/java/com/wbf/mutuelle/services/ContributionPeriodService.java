package com.wbf.mutuelle.services;

import com.wbf.mutuelle.entities.ContributionPeriod;
import com.wbf.mutuelle.repositories.ContributionPeriodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContributionPeriodService {

    private final ContributionPeriodRepository repository;

    public List<ContributionPeriod> getAll() {
        return repository.findAll();
    }

    public ContributionPeriod getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Période non trouvée avec id " + id));
    }

    public ContributionPeriod create(ContributionPeriod period) {
        return repository.save(period);
    }

    public ContributionPeriod update(Long id, ContributionPeriod updated) {
        return repository.findById(id).map(existing -> {
            existing.setStartDate(updated.getStartDate());

            existing.setEndDate(updated.getEndDate());
            existing.setIndividualAmount(updated.getIndividualAmount());
            existing.setActive(updated.isActive());

            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Période non trouvée avec id " + id));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Impossible de supprimer : période avec id " + id + " inexistante.");
        }
        repository.deleteById(id);
    }
}
