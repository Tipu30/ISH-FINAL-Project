package com.cts.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.cts.bindings.UserData;
import com.cts.entity.UserEntity;
import com.cts.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LogoutController {
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/account")
	public String showAccount(HttpSession session, Model model) {
	
            UserEntity loggedInUser = (UserEntity) session.getAttribute("loggedInUser");

		    if (loggedInUser == null) {
		        return "access-denied";
		    }

		    model.addAttribute("role",loggedInUser.getRole()); 
		    model.addAttribute("name", loggedInUser.getFullName());
		    model.addAttribute("email", loggedInUser.getEmail());
		
		return "account";	
		
	}
	
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (session == null || session.getAttribute("loggedInUser") == null) {  
            System.out.println("No active session found.");
            return "redirect:/";
        } else {
            System.out.println("Session invalidated for user");
            session.invalidate();  
            return "redirect:/"; 
        }
         
    }

}
