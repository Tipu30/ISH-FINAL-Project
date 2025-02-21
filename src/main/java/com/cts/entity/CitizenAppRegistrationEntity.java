package com.cts.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CITIZEN_APPLICATION")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitizenAppRegistrationEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer appid;
	@Column(length=30)
	private String fullName;
	@Column(length=30)
	private String email;
	@Column(length=30)
	private String gender;
	private Long phoneNumber;
	private Long ssn;
	@Column(length=30)
	private String stateName;
	private LocalDate dob;
}
