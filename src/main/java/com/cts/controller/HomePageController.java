package com.cts.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

public class HomePageController {

	@Controller
	public class HomepageController {
		
		@GetMapping("/")
		public String getHomePage() {
			return "index";
		}

		@GetMapping("/index")
		public String getBackToHome() {
			return "index";
		}
		
			
		
	}

}
