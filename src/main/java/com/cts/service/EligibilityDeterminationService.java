package com.cts.service;

	import java.time.LocalDate;
	import java.time.Period;
	import java.util.List;
	import java.util.Optional;

	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;
	import org.springframework.beans.BeanUtils;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;

import com.cts.bindings.CitizenAppRegistrationInputs;
import com.cts.bindings.EligibilityDetailsOutput;
import com.cts.bindings.PlanEligibilityDetermination;
import com.cts.entity.CitizenAppRegistrationEntity;
	import com.cts.entity.DcCaseEntity;
	import com.cts.entity.DcChildrenEntity;
	import com.cts.entity.DcEducationEntity;
	import com.cts.entity.DcIncomeEntity;
	import com.cts.entity.EligibilityDetailsEntity;
import com.cts.entity.PlanEligibilityDeterminationEntity;
import com.cts.entity.PlanEntity;
import com.cts.repository.ApplicationRegistrationRepository;
import com.cts.repository.EligibilityDeterminationRepository;
import com.cts.repository.PlanEligibilityDeterminationRepository;

	@Service
	public class EligibilityDeterminationService{
		private static final Logger log = LoggerFactory.getLogger(EligibilityDeterminationService.class);
		
		
		@Autowired
		private ApplicationRegistrationRepository applicationRepo;
		
		@Autowired
		private PlanEligibilityDeterminationRepository planEligibilityRepo;
		
		@Autowired
		private EligibilityDeterminationRepository eligibilityRepo;
	
		
		
		//CREATE B.logic for registering planEligibility details persistence in Table
		public String registerPlanEligibilityDetails(PlanEligibilityDetermination planEligibilityDetails) {
			//convert planEligibilityDetails binding obj to planEligibility entity
			
			PlanEligibilityDeterminationEntity planEligibilityEntity = new PlanEligibilityDeterminationEntity();
			System.out.println(planEligibilityDetails.getCitizenName());
			System.out.println(planEligibilityDetails.getCitizenSSN());
//			planEligibilityEntity.setAppid(planEligibilityDetails.getAppid());
//			planEligibilityEntity.setCitizenName(planEligibilityDetails.getCitizenName());
//			planEligibilityEntity.setCitizenSSN(planEligibilityDetails.getCitizenSSN());
//			planEligibilityEntity.setDob(planEligibilityDetails.getDob());
//			planEligibilityEntity.setIncome(planEligibilityDetails.getIncome());
//			planEligibilityEntity.setPassingOutYear(planEligibilityDetails.getPassingOutYear());
//			planEligibilityEntity.setSelectedPlan(planEligibilityDetails.getSelectedPlan());
//			planEligibilityEntity.setKidAge(planEligibilityDetails.getKidAge());
//			planEligibilityEntity.setKidSSN(planEligibilityDetails.getKidSSN());
			
			BeanUtils.copyProperties(planEligibilityDetails, planEligibilityEntity);
			
			//save the object
//			PlanEntity savedEntity = planRepo.save(planEntity);
//			CitizenAppRegistrationEntity savedEntity =  applicationRegRepo.save(citizenEntity);
			PlanEligibilityDeterminationEntity savedEntity = planEligibilityRepo.save(planEligibilityEntity);
			return "SAVE_SUCCESS";
			
		}
		
		

     	public EligibilityDetailsOutput determineEligibility(Integer appId) {
     		
     	Optional<PlanEligibilityDeterminationEntity> optPlanEligibilityEntity =	planEligibilityRepo.findByAppid(appId);
     	//get all data like plan name, income, kids details if applicable
    	String planName = null;
    	Integer income = null;
    	Integer citizenAge = null;
    	Integer kidAge = null;
    	Integer kidSSN = null;
    	Integer passingOutYear = null;
    	String citizenName = null;
    	Long citizenSSN = null;
     	if(optPlanEligibilityEntity.isPresent()) {
     		PlanEligibilityDeterminationEntity planEligibilityEntity = optPlanEligibilityEntity.get();
//     		System.out.println(planEligibilityEntity.getCitizenName());
//     		System.out.println(planEligibilityEntity.getCitizenSSN());
     		planName = planEligibilityEntity.getSelectedPlan();
     		System.out.println(planName);
     		income = planEligibilityEntity.getIncome();
     		LocalDate dob = planEligibilityEntity.getDob();
     		kidAge = planEligibilityEntity.getKidAge();
     		kidSSN = planEligibilityEntity.getKidSSN();
     		passingOutYear = planEligibilityEntity.getPassingOutYear();
     		citizenName = planEligibilityEntity.getCitizenName();
     		citizenSSN = planEligibilityEntity.getCitizenSSN();
     		
     		LocalDate sysDate = LocalDate.now();
			citizenAge = Period.between(dob, sysDate).getYears();
     	}
     	
     	//call helper methods where all the logics will be there to determine the citizen eligibility of the applied plan 
     	EligibilityDetailsOutput eligibilityDetailsOutput =	applyPlanConditions(planName, citizenAge, income, kidAge, appId, passingOutYear);
     	eligibilityDetailsOutput.setHolderSSN(citizenSSN);
     	eligibilityDetailsOutput.setHolderName(citizenName);
     	//convert eligibility binding to entity
     	EligibilityDetailsEntity eligibilityEntity = new EligibilityDetailsEntity();
     	BeanUtils.copyProperties(eligibilityDetailsOutput, eligibilityEntity);
     	//save  eligibility entity
     	EligibilityDetailsEntity savedEntity =  eligibilityRepo.save(eligibilityEntity);
     	System.out.println(eligibilityDetailsOutput.toString());
     	return eligibilityDetailsOutput;
     	}
     	
    	//helper method
    	private EligibilityDetailsOutput applyPlanConditions(String planName, Integer citizenAge, Integer income, Integer kidAge, Integer appId, Integer passingOutYear) {
    		EligibilityDetailsOutput eligibilityOutput = new EligibilityDetailsOutput();
    		eligibilityOutput.setEdId(appId);
    		eligibilityOutput.setPlanName(planName);
    		
    		//Logic for SNAP eligibility
    		if(planName.equalsIgnoreCase("SNAP")) {
    			if(income<=200) {
    				eligibilityOutput.setPlanStatus("Approved");
    				eligibilityOutput.setBenifitAmt(300.0);
    				eligibilityOutput.setPlanStartDate(LocalDate.now());
    	    		eligibilityOutput.setPlanEndDate(LocalDate.now().plusYears(2));
    				
    			}
    			else {
    				eligibilityOutput.setPlanStatus("Denied");
    				eligibilityOutput.setDenialReason("High Income");
    		
    				
    			}
    		}
    		
//    		//Logic for CCAP
    		else if(planName.equalsIgnoreCase("CCAP")) {
  			
    			if(income<= 300 && kidAge <= 16) {
    				eligibilityOutput.setPlanStatus("Approved");
    				eligibilityOutput.setBenifitAmt(300.0);
    				eligibilityOutput.setPlanStartDate(LocalDate.now());
    	    		eligibilityOutput.setPlanEndDate(LocalDate.now().plusYears(2));
    	    	
    				
    			}
    			else {
    				eligibilityOutput.setPlanStatus("Denied");
    				eligibilityOutput.setDenialReason("CCAP rules are not satisfied");
    			
    				
    			}
    		}
    		
    		//Logic for MEDCARE
    		//Here we need Citizen Age
    		else if(planName.equalsIgnoreCase("MEDCARE")) {
    			Integer eligibleAge = 65;
    			if(citizenAge >=  eligibleAge) {
    				eligibilityOutput.setPlanStatus("Approved");
    				eligibilityOutput.setBenifitAmt(350.0);
    				eligibilityOutput.setPlanStartDate(LocalDate.now());
    	    		eligibilityOutput.setPlanEndDate(LocalDate.now().plusYears(2));
    	   
    				
    			}
    			else {
    				eligibilityOutput.setPlanStatus("Denied");
    				eligibilityOutput.setDenialReason("MEDCARE rules are not satisfied");
    				
    			}
    			
    		}
    		
    		//Logic for MEDAID
    		else if(planName.equalsIgnoreCase("MEDAID")) {
    			if(income<=400) {
    				eligibilityOutput.setPlanStatus("Approved");
    				eligibilityOutput.setBenifitAmt(200.0);
    				eligibilityOutput.setPlanStartDate(LocalDate.now());
    	    		eligibilityOutput.setPlanEndDate(LocalDate.now().plusYears(2));
    	    		
    				
    			}
    			else {
    				eligibilityOutput.setPlanStatus("Denied");
    				eligibilityOutput.setDenialReason("MEDAID rules are not satisfied");
    			
    			}
    		}
    		
    		//Logic for CAJW
    		else if(planName.equalsIgnoreCase("CAJW")) {
    			 if(income==0 && passingOutYear < LocalDate.now().getYear()) {
    				 eligibilityOutput.setPlanStatus("Approved");
    				 eligibilityOutput.setBenifitAmt(300.0);
    				 eligibilityOutput.setPlanStartDate(LocalDate.now());
    		    		eligibilityOutput.setPlanEndDate(LocalDate.now().plusYears(2));
    		    	
    				
    			 }
    			 else {
    				 eligibilityOutput.setPlanStatus("Denied");
    				 eligibilityOutput.setDenialReason("CAJW rules are not satisfied");
    				
    			 }
    		}
    		else if(planName.equalsIgnoreCase("QHP")) {
    			if(citizenAge >= 1) {
    				eligibilityOutput.setPlanStatus("Approved");
    				eligibilityOutput.setPlanStartDate(LocalDate.now());
    	    		eligibilityOutput.setPlanEndDate(LocalDate.now().plusYears(2));
    	   
    				
    			}
    			else {
    				eligibilityOutput.setPlanStatus("Denied");
    				eligibilityOutput.setDenialReason("QHP rules are not satisfied");
    				
    			}
    			}
    			
    		
    		return eligibilityOutput;
    	}
			

}
