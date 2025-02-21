package com.cts.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cts.entity.CitizenAppRegistrationEntity;
import com.cts.entity.EligibilityDetailsEntity;
@Repository
public interface ApplicationRegistrationRepository extends JpaRepository<CitizenAppRegistrationEntity, Integer> {
	
	@Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM CitizenAppRegistrationEntity e WHERE e.fullName = :fullName")
    boolean existsByFullName(@Param("fullName") String fullName);
	
	@Query("SELECT e FROM CitizenAppRegistrationEntity e WHERE e.ssn = :ssn")
    CitizenAppRegistrationEntity findBySSN(@Param("ssn") Long ssn);
	
	@Query("SELECT e FROM CitizenAppRegistrationEntity e WHERE e.fullName = :fullName")
    Optional<CitizenAppRegistrationEntity> findByFullName(@Param("fullName") String fullName);
}
