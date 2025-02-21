package com.cts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cts.bindings.EligibilityDetailsOutput;
import com.cts.bindings.PlanData;
import com.cts.entity.UserEntity;
import com.cts.service.AdminService;
import com.cts.service.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
public class AdminController {
	
	@Autowired
	private AdminService service;
	@Autowired
	private UserService userService;
//	@GetMapping("/dashboard")
//	public String showAdminDashboard(HttpSession session) {
//	   Object loggedInUser = session.getAttribute("loggedInUser");
//
//	      if (loggedInUser == null || !"ADMIN".equals(((com.cts.entity.UserEntity) loggedInUser).getRole())) {
//	            return "access-denied";
//	    }
//
//	        return "admin_dashboard";
//	    }
//	
//	
	   
    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        UserEntity loggedInUser = (UserEntity) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "access-denied";
        }

        String role = loggedInUser.getRole().toUpperCase();
        if ("ADMIN".equals(role)) {
        	int approvdedPlans = service.countApprovedPlansForCitizen();
        	int approvedCitizen = service.countApprovedCitizen();
        	int deniedCitizen = service.countDeniedCitizen();
        	double totalBenefit = service.getTotalBenefitsProvided();
        	model.addAttribute("approvedPlansCount", approvdedPlans);
        	model.addAttribute("approvedCitizenCount", approvedCitizen);
        	model.addAttribute("deniedCitizenCount", deniedCitizen);
        	model.addAttribute("totalBenefitsAmount", totalBenefit);
        	
            return "admin_dashboard";
        } else if ("CITIZEN".equals(role)) {
        	String citizenName = loggedInUser.getFullName();
        	int approvedPlans = 0;
        	int deniedPlans = 0;
        	 List<EligibilityDetailsOutput> eligibilityDetails = userService.showMyBenefits(citizenName);
        	 Double citizenBenefit = userService.getTotalBenefitsByHolderName(citizenName);

             for (EligibilityDetailsOutput details : eligibilityDetails) {
            	if(citizenName.equalsIgnoreCase(details.getHolderName())){
            		if(details.getPlanStatus().equalsIgnoreCase("approved")) {
            			approvedPlans++;
            		}
            		else if(details.getPlanStatus().equalsIgnoreCase("denied")) {
            			deniedPlans++;
            		}
            	}
            	model.addAttribute("approvedCitizenPlansCount", approvedPlans);
            	model.addAttribute("deniedCitizenPlansCount", deniedPlans);
            	model.addAttribute("citizenTotalBenefits", citizenBenefit);
            	 
             }
                 
             
      	     
        	
            return "user_dashboard";
        } else {
            return "access-denied";
        }
    }
	
	

    // Show all plans
    @GetMapping("/plan/all")
    public String getAllPlans(Model model, HttpSession session) {
    	
    	 Object loggedInUser = session.getAttribute("loggedInUser");

 	     if (loggedInUser == null || !"ADMIN".equals(((com.cts.entity.UserEntity) loggedInUser).getRole())) {
 	            return "access-denied";
 	    }
    	
        List<PlanData> plansList = service.showAllPlans();
        
        model.addAttribute("plans", plansList);
        return "admin"; // Returns a view page
    }
    
    
    @GetMapping("/show/benefit")
    public String showBenefitIssuance(Model model, HttpSession session) {
    	
    	 Object loggedInUser = session.getAttribute("loggedInUser");

 	     if (loggedInUser == null || !"ADMIN".equals(((com.cts.entity.UserEntity) loggedInUser).getRole())) {
 	            return "access-denied";
 	    }
 	     
 	     List<EligibilityDetailsOutput> eligibilityDetails = service.showAllBenefits();
 	     model.addAttribute("benefits", eligibilityDetails);
 	     
        return "benefit"; // Returns a view page
    }
    
    
    
    

//    // Show plan by ID
//    @GetMapping("/find")
//    public String getPlanById(@RequestParam("planId") Integer planId, Model model) {
//        PlanData planData = planService.showPlanById(planId);
//        model.addAttribute("plan", planData);
//        return "planDetails";
//    }

   //Show Plan registration form
    @GetMapping("/registerplan")
    public String showRegistrationForm(Model model) {
        model.addAttribute("planData", new PlanData());
        return "add_plan";
    }

    // Save a new plan
    @PostMapping("/registerplan")
    public String showPlanForm(@ModelAttribute PlanData planData, RedirectAttributes redirectAttributes) {
        String message = service.registerPlan(planData);
        redirectAttributes.addFlashAttribute("addMessage", message);
        return "redirect:/plan/all";
    }
  
    // Show update form
    @GetMapping("/update")
    public String showUpdateForm(@RequestParam("planId") Integer planId, Model model) {
        PlanData planData = service.showPlanById(planId);
        model.addAttribute("planData", planData);
        return "update_plan";
    }

    // Update a plan
    @PostMapping("/update")
    public String updatePlan(@ModelAttribute PlanData planData, RedirectAttributes redirectAttributes) {
        String message = service.updatePlan(planData);
        redirectAttributes.addAttribute("updateMessage", message);
        return "redirect:/plan/all";
    }

    // Delete a plan
    @GetMapping("/delete")
    public String removePlanByPlanId(@RequestParam("planId") Integer planId, RedirectAttributes redirectAttributes) {
        String message = service.deletePlan(planId);
        redirectAttributes.addFlashAttribute("deleteMessage", message);
//        System.out.println(message);
        return "redirect:/plan/all";
    }

    // Change plan status
//    @GetMapping("/status-change")
//    public String changeStatusByPlanId(@RequestParam("planId") Integer planId,
//                                       @RequestParam("status") String status,
//                                       Model model) {
//        String message = service.changePlanStatus(planId, status);
//        model.addAttribute("message", message);
//        return "redirect:/plan/all";
//    }
}
