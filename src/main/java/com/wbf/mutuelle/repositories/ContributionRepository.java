package com.wbf.mutuelle.repositories;

import com.wbf.mutuelle.entities.Contribution;
import com.wbf.mutuelle.entities.ContributionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ContributionRepository extends JpaRepository<Contribution, Long> {

    // Méthode de base pour trouver par type
    List<Contribution> findByContributionType(ContributionType contributionType);

    // Contributions individuelles d'un membre - version très simple
    List<Contribution> findByContributionTypeAndMemberId(ContributionType contributionType, Long memberId);

    // Contributions groupées - on va utiliser une approche différente
    @Query("SELECT DISTINCT c FROM Contribution c WHERE c.contributionType = :contributionType AND EXISTS (SELECT m FROM c.members m WHERE m.id = :memberId)")
    List<Contribution> findGroupContributionsByMemberId(@Param("contributionType") ContributionType contributionType, @Param("memberId") Long memberId);
    List<Contribution> findByMemberId(Long memberId);

    // Requête pour calculer la balance totale d'un membre
    @Query("SELECT COALESCE(SUM(c.amount), 0) FROM Contribution c WHERE c.member.id = :memberId")
    BigDecimal calculateBalanceByMemberId(@Param("memberId") Long memberId);

    // Requête pour la balance totale de tous les membres
    @Query("SELECT COALESCE(SUM(c.amount), 0) FROM Contribution c")
    BigDecimal calculateTotalBalance();
}