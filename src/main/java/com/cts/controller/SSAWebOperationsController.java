package com.cts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ssa-web")
public class SSAWebOperationsController {

    @GetMapping("/find")
    public String getStateBySSN(@RequestParam("ssn") String ssn, Model model) {
        if (ssn.length() != 9 || !ssn.matches("\\d+")) {
            model.addAttribute("stateName", "Invalid SSN");
            return "application_registration"; // Returns a view
        }

        int stateCode = Integer.parseInt(ssn) % 100;
        String stateName;

        switch (stateCode) {
            case 1: stateName = "Washington DC"; break;
            case 2: stateName = "Ohio"; break;
            case 3: stateName = "Texas"; break;
            case 4: stateName = "California"; break;
            case 5: stateName = "Florida"; break;
            default: stateName = "Invalid SSN";
        }

        model.addAttribute("stateName", stateName);
        return "ssaResult"; // Returns a Thymeleaf view
    }
}
