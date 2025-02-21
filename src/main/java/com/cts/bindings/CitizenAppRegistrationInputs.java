package com.cts.bindings;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CitizenAppRegistrationInputs {
	private Integer aapid;
	private String fullName;
	private String email;
	private String gender;
	private Long phoneNumber;
	private Long ssn;
	private String stateName;
	private LocalDate dob;
	
	
}
