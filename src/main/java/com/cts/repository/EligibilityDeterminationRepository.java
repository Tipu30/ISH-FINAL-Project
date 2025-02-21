package com.cts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cts.entity.EligibilityDetailsEntity;

@Repository
public interface EligibilityDeterminationRepository extends JpaRepository<EligibilityDetailsEntity, Integer> {
	int countByplanStatus(String planStatus);
	
	@Query("SELECT COALESCE(SUM(e.benifitAmt), 0) FROM EligibilityDetailsEntity e WHERE e.planStatus = :status")
    Double getTotalBenefitsByStatus(@Param("status") String status);
	
	@Query("SELECT e FROM EligibilityDetailsEntity e WHERE e.holderName = :holderName")
    List<EligibilityDetailsEntity> findByHolderName(@Param("holderName") String holderName);
	

    @Query("SELECT COALESCE(SUM(e.benifitAmt), 0) FROM EligibilityDetailsEntity e WHERE e.holderName = :holderName AND e.planStatus = 'APPROVED'")
    Double getTotalBenefitsByHolderName(@Param("holderName") String holderName);
}
