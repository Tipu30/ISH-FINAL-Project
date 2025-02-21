package com.cts.bindings;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
@Data
public class EligibilityDetailsOutput {
	private Integer edId;
	private String holderName;
	private Long holderSSN;
	private String planName;
	private String planStatus;
	private LocalDate planStartDate;
	private LocalDate planEndDate;
	private Double benifitAmt;
	private String denialReason; 
	
}
