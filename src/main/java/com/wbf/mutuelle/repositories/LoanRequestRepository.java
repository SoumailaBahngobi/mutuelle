package com.wbf.mutuelle.repositories;

import com.wbf.mutuelle.entities.LoanRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRequestRepository extends JpaRepository<LoanRequest,Long> {
}
