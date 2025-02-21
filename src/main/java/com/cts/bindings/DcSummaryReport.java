package com.cts.bindings;

import java.util.List;

import lombok.Data;

@Data
public class DcSummaryReport {
	private PlanSelectionInputs planDetails;
	private EducationInputs educationDetails;
	private List<ChildInputs> childDetails;
	private IncomeInputs incomeDetails;
	//for getting citizen personal info also
	private CitizenAppRegistrationInputs citizenDetails;
	private String planName;
}
