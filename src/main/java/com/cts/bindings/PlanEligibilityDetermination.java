package com.cts.bindings;

import java.time.LocalDate;

import lombok.Data;
@Data
public class PlanEligibilityDetermination {
	
	private Integer appid;
	private String citizenName;
	private Long citizenSSN;
	private LocalDate dob;
	private String selectedPlan;
	private Integer income;
	private Integer passingOutYear;
	//Kids info if applicable
	private Integer kidAge;
	private Integer kidSSN;
}
