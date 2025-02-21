package com.cts.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.entity.PlanEntity;
import com.cts.entity.UserEntity;

@Repository
public interface IPlanRepository extends JpaRepository<PlanEntity, Integer> {
}
