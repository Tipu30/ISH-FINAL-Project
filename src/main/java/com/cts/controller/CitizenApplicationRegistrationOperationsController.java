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
import com.cts.entity.UserEntity;
import com.cts.service.AdminService;
import com.cts.service.ApplicationRegistrationService;
import com.cts.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CitizenApplicationRegistrationOperationsController {

    @Autowired
    private ApplicationRegistrationService citizenService;
    
    @Autowired
    private AdminService service; 
    @Autowired
    private UserService userService;
    

    //show application registration form
    @GetMapping("/register")
    public String showApplicationForm(Model model) {
        model.addAttribute("citizen", new CitizenAppRegistrationInputs());
        return "application_registration";
    }

    @PostMapping("/register")
    public String processForm(@ModelAttribute("citizen") CitizenAppRegistrationInputs citizen, Model model, RedirectAttributes redirectAttributes) {
    	//use service
           
    	    String targetStateName = "california";
//    	    Integer appid = userService.getAppId(citizen.getSsn());
            String stateName = citizenService.getStateBySSN(citizen.getSsn());
            
            if (stateName.equalsIgnoreCase(targetStateName)) {
            	 String saveSuccessMsg = userService.registerCitizenDetails(citizen);
            	Integer appid = userService.getAppId(citizen.getSsn());
                redirectAttributes.addFlashAttribute("stateName", stateName);
                redirectAttributes.addFlashAttribute("welcome", "Welcome " + citizen.getFullName());
                redirectAttributes.addFlashAttribute("citizenName", citizen.getFullName());
                redirectAttributes.addFlashAttribute("citizenSSN", citizen.getSsn());
                redirectAttributes.addFlashAttribute("dob", citizen.getDob());
                redirectAttributes.addFlashAttribute("appid", appid);
                return "redirect:/eligibility";
            }
            else {
            	model.addAttribute("notEligible", "You are not a Californian citizen !");
            return "application_registration";
            }    
    }
    
   
    
    @GetMapping("/show/mybenefit")
    public String showBenefitIssuance(Model model, HttpSession session) {
    	
    	 Object loggedInUser = session.getAttribute("loggedInUser");
    	UserEntity userEntity =  ((com.cts.entity.UserEntity) loggedInUser);
 	     if (loggedInUser == null || !"CITIZEN".equals(userEntity.getRole())) {
 	            return "access-denied";
 	    }
 	     
 	     List<EligibilityDetailsOutput> eligibilityDetails = userService.showMyBenefits(userEntity.getFullName());
 	     model.addAttribute("Mybenefits", eligibilityDetails);
 	     
        return "mybenefit"; // Returns a view page
    }
    
    
}
