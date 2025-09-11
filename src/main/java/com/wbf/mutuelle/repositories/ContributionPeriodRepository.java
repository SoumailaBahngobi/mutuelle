package com.wbf.mutuelle.repositories;

import com.wbf.mutuelle.entities.ContributionPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributionPeriodRepository extends JpaRepository<ContributionPeriod,Long> {

}
