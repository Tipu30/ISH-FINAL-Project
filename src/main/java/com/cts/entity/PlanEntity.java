package com.cts.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="PLAN_MASTER")
@Data
public class PlanEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer planId;
	@Column(length = 30)
	private String planName;
	private LocalDate startDate;
	private LocalDate endDate;
	@Column(length = 60)
	private String description;
	@Column(length = 30)
	private String activeSw;
	

}
