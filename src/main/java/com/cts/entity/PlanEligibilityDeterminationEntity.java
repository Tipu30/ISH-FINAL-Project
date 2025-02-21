package com.cts.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CITIZEN_PLAN_ELIGIBILITY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanEligibilityDeterminationEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer appid;
	@Column(length = 30)
	private String citizenName;
	private Long citizenSSN;
	private LocalDate dob;
	@Column(length = 30)
	private String selectedPlan;
	private Integer income;
	
	private Integer passingOutYear;
	
	//Kids info if applicable
	private Integer kidAge;
	private Integer kidSSN;
}
