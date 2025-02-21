package com.cts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cts.bindings.CitizenAppRegistrationInputs;
import com.cts.bindings.EligibilityDetailsOutput;
import com.cts.bindings.PlanData;
import com.cts.bindings.PlanEligibilityDetermination;
import com.cts.entity.UserEntity;
import com.cts.repository.ApplicationRegistrationRepository;
import com.cts.service.AdminService;
import com.cts.service.EligibilityDeterminationService;
import com.cts.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class EligibilityDeterminationController {
	@Autowired
	private EligibilityDeterminationService edService;
	@Autowired
	private AdminService service;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ApplicationRegistrationRepository applicationRegistrationRepo;

//	@Autowired
//	private UserService userService;
	
	@GetMapping("/eligibility")
	public String showPlanEligibilityForm(Model model){

		List<PlanData> plansList = service.showAllPlans();
		System.out.println(plansList);
		model.addAttribute("planLists", plansList);
//	    model.addAttribute("plans", plansList);
		model.addAttribute("planEligibilityDetails", new PlanEligibilityDetermination());
		return "eligibility";
	}
	
	@PostMapping("/eligibility")
	public String checkPlanEligibility(@ModelAttribute("planEligibilityDetails") PlanEligibilityDetermination planEligibilityDetails, Model model) {
//		System.out.println(planEligibilityDetails.getAppid());
//		System.out.println(planEligibilityDetails.getCitizenName());
//		System.out.println(planEligibilityDetails.getCitizenSSN());
		System.out.println(planEligibilityDetails.getSelectedPlan());
		String saveSuccessMsg = edService.registerPlanEligibilityDetails(planEligibilityDetails);
		EligibilityDetailsOutput eligibilityDetailsOutput = edService.determineEligibility(planEligibilityDetails.getAppid());
		String finalPlanStatus = eligibilityDetailsOutput.getPlanStatus();
		if(finalPlanStatus.equalsIgnoreCase("approved")) {
			return "approved_for_plan";
		}
		else {
			return "redirect:/eligibility";
		}
	}
//	
//	@GetMapping("/check/eligibility")
//	public String showEligibilityFormIfEligibile(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
//		 UserEntity loggedInUser = (UserEntity) session.getAttribute("loggedInUser");
//		 boolean eligibileCitizenExist =  applicationRegistrationRepo.existsByFullName(loggedInUser.getFullName());
//		 System.out.println(eligibileCitizenExist);
//		 System.out.println(loggedInUser.getFullName());
//		 if(eligibileCitizenExist) {
//			 CitizenAppRegistrationInputs data = userService.getEligibileCitizenDetails(loggedInUser.getFullName());
//			 redirectAttributes.addFlashAttribute("appid", data.getAapid());
//			 redirectAttributes.addFlashAttribute("citizenName", data.getFullName());
//			 redirectAttributes.addFlashAttribute("citizenSSN", data.getSsn());
//			 redirectAttributes.addFlashAttribute("dob",data.getDob());
//		
//			 return "redirect:/eligibility";
//		 }
//		 else {
//			 return "register-first";
//		 }
//	}
	
}
