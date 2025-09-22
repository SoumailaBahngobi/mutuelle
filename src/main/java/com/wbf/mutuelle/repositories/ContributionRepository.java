package com.wbf.mutuelle.repositories;

import com.wbf.mutuelle.entities.Contribution;
import com.wbf.mutuelle.entities.ContributionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContributionRepository extends JpaRepository<Contribution, Long> {

    List<Contribution> findByType(ContributionType type);

    List<Contribution> findByContributionPeriodId(Long periodId);

    @Query("SELECT c FROM Contribution c LEFT JOIN c.member m LEFT JOIN c.members mem WHERE m.id = :memberId OR mem.id = :memberId")
    List<Contribution> findByMemberIdOrMembersId(@Param("memberId") Long memberId, @Param("memberId") Long memberId2);
}