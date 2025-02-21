package com.cts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.entity.PlanEntity;

@Repository
public interface AdminRepository extends JpaRepository<PlanEntity, Integer> {
	int countByActiveSw(String activeSw);
}
