package com.cts.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cts.bindings.UserData;
import com.cts.entity.UserEntity;
import com.cts.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/user-register")
    public String showCitizenRegisterForm(Model model) {
        model.addAttribute("userCitizen", new UserData());
        return "signup";
    }
	
	@PostMapping("/user-register")
    public String registerCitizen(@ModelAttribute("userCitizen") UserData userCitizen, Model model) {
		userCitizen.setRole("CITIZEN");
        boolean success = userService.registerCitizen(userCitizen);

        if (!success) {
//           redirectAttributes.addFlashAttribute("errorMessage", "email already registered!");
           model.addAttribute("errorMessage", "email already registered!");
            return "signup";
        }

      
        //redirectAttributes.addFlashAttribute("successMessage", "Registration successful!");
          model.addAttribute("successMessage", "Registration successful!");
        return "signup";
    }
	
	 @GetMapping("/user-login")
	 public String showCitizenLoginForm(Model model) {
	     model.addAttribute("user", new UserEntity()); 
	     return "signin";
	}	
	 
	 @PostMapping("/user-login")
	 public String citizenLogin(@ModelAttribute("user") UserEntity user, Model model, HttpSession session) {
	     Optional<UserEntity> userOptional = userService.validateLogin(user.getEmail(), user.getPassword());

	     if (userOptional.isPresent() && userOptional.get().getRole().equals("CITIZEN")) {
	        session.setAttribute("loggedInUser", userOptional.get());
	            return "redirect:/dashboard";  
	        }

	        model.addAttribute("errorMessage", "Invalid username or password!");
	        return "signin";
	    }
	
	 @GetMapping("/admin-login")
	    public String showAdminLoginForm(Model model) {
	        model.addAttribute("user", new UserEntity());
	        return "admin_signin";
	    }

	    @PostMapping("/admin-login")
	    public String adminLogin(@ModelAttribute("user") UserEntity user, Model model, HttpSession session) {
	        Optional<UserEntity> userOptional = userService.validateLogin(user.getEmail(), user.getPassword());
	        if (userOptional.isPresent() && userOptional.get().getRole().equals("ADMIN")) {
	            session.setAttribute("loggedInUser", userOptional.get());
	           
	            System.out.println("Session created for admin");
	            return "redirect:/dashboard";
	           
	        }

	        model.addAttribute("errorMessage", "Invalid admin credentials!");
//	        String role = userOptional.get().getRole();
//	        model.addAttribute("role", role);
//	        System.out.println(role);
	        return "admin_signin";
	    }
	 

	    
	    


	 
}
