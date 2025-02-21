package com.cts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cts.entity.PlanEligibilityDeterminationEntity;
@Repository
public interface PlanEligibilityDeterminationRepository extends JpaRepository<PlanEligibilityDeterminationEntity, Integer> {
	

	    @Query("SELECT e FROM PlanEligibilityDeterminationEntity e WHERE e.appid = :appid")
	    Optional<PlanEligibilityDeterminationEntity> findByAppid(@Param("appid") Integer appid);
}
