package com.wbf.mutuelle.repositories;

import com.wbf.mutuelle.entities.Loan;
import com.wbf.mutuelle.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository <Loan,Long> {
    boolean existsByMemberAndIsRepaidFalse(Member member);
}
