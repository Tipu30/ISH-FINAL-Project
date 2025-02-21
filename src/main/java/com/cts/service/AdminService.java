package com.cts.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.bindings.EligibilityDetailsOutput;
import com.cts.bindings.PlanData;
import com.cts.config.AppConfigProperties;
import com.cts.constants.PlanConstants;
import com.cts.entity.EligibilityDetailsEntity;
import com.cts.entity.PlanEntity;
import com.cts.repository.AdminRepository;
import com.cts.repository.EligibilityDeterminationRepository;

	@Service
	public class AdminService {
		private static final Logger log = LoggerFactory.getLogger(AdminService.class);
		
		
		@Autowired
		private AdminRepository planRepo;
		
		@Autowired
		private EligibilityDeterminationRepository eligibilityRepo;
		
		
		
		private Map<String,String> messages;
		
		@Autowired
		public AdminService(AppConfigProperties props) {
			messages = props.getMessages();
		}
		

		//Save operation
//		CREATE B.logic for plan
		public String registerPlan(PlanData plan) {
			//convert PlanData binding obj to PlanEntity
			PlanEntity planEntity = new PlanEntity();
			BeanUtils.copyProperties(plan, planEntity);
			
			//save the object
			PlanEntity savedEntity = planRepo.save(planEntity);
			
			return savedEntity.getPlanId()!=null?messages.get(PlanConstants.SAVE_SUCCESS)+savedEntity.getPlanId():messages.get(PlanConstants.SAVE_FAILURE);
			
		}
		
		//Get all plans
//		GET/READ/VIEW B.Logic for plan
		
		public List<PlanData> showAllPlans() {	
			List<PlanEntity> listEntities = planRepo.findAll();
			List<PlanData> listPlanData = new ArrayList<>();
			listEntities.forEach(entity->{
				PlanData data = new PlanData();
				BeanUtils.copyProperties(entity, data);
				listPlanData.add(data);
			});
			System.out.println(listPlanData);
			return listPlanData;
		}
		
		
		//Get all Benefit issuance
//		GET/READ/VIEW B.Logic for benefit inssuance
		
		public List<EligibilityDetailsOutput> showAllBenefits() {	
			List<EligibilityDetailsEntity> listEntities = eligibilityRepo.findAll();
			List<EligibilityDetailsOutput> listEligibilityData = new ArrayList<>();
			listEntities.forEach(entity->{
				EligibilityDetailsOutput data = new EligibilityDetailsOutput();
				BeanUtils.copyProperties(entity, data);
				listEligibilityData.add(data);
			});
//			System.out.println(listEligibilityData);
			return listEligibilityData;
		}
		

		
//		GET/READ/VIEW B.Logic for plan
		
		public PlanData showPlanById(Integer planId) {
			PlanEntity planEntity = planRepo.findById(planId).orElseThrow(()->new IllegalArgumentException(messages.get(PlanConstants.FIND_BY_ID_FAILURE)));
			//convert Entity obj to binding obj
			PlanData planData = new PlanData();
			BeanUtils.copyProperties(planEntity, planData);
			return planData;
		}

		//UPDATION B.Logic for plan
		
		public String updatePlan(PlanData plan) {
			Optional<PlanEntity> optPlanEntity = planRepo.findById(plan.getPlanId());
			if(optPlanEntity.isPresent()) {
				//update the object
				PlanEntity planEntity = optPlanEntity.get();
				BeanUtils.copyProperties(plan, planEntity);
				planRepo.save(planEntity);
				return plan.getPlanId()+" "+messages.get(PlanConstants.UPDATE_SUCCESS);
			}else {
				return plan.getPlanId()+" "+messages.get(PlanConstants.UPDATE_FAILURE);
			}		
		}

	
		//DELETION B.Logic for plan
		public String deletePlan(Integer planId) {
			Optional<PlanEntity> optPlanEntity = planRepo.findById(planId);
			if(optPlanEntity.isPresent()) {
				//delete the object
				planRepo.deleteById(planId);
				return planId+" "+messages.get(PlanConstants.DELETE_SUCCESS);
			}
			else {
				return planId+" "+messages.get(PlanConstants.DELETE_FAILURE);
			}
			
		}
		
		//UPDATION B.Logic for plan

		public String changePlanStatus(Integer planId, String status) {
			Optional<PlanEntity> optPlanEntity = planRepo.findById(planId);
			if(optPlanEntity.isPresent()) {
				PlanEntity planEntity = optPlanEntity.get();
				planEntity.setActiveSw(status);
				planRepo.save(planEntity);
				return planId+messages.get(PlanConstants.STATUS_CHANGE_SUCCESS);
			}
			else {
				return planId+messages.get(PlanConstants.STATUS_CHANGE_FAILURE);
			}
			
		}
		
		
		//Other necessary methods to get data from db tables
		public int countApprovedPlansForCitizen() {
			System.out.println(planRepo.countByActiveSw("active"));
			return planRepo.countByActiveSw("active");
			
		}
		
		public int countApprovedCitizen() {
			return eligibilityRepo.countByplanStatus("approved");
		}

		public int countDeniedCitizen() {
			return eligibilityRepo.countByplanStatus("denied");
		}
		
		public Double getTotalBenefitsProvided() {
			return eligibilityRepo.getTotalBenefitsByStatus("approved");
		}
}

