package com.wbf.mutuelle.repositories;

import com.wbf.mutuelle.entities.Contribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributionRepository extends JpaRepository<Contribution,Long> {
}
