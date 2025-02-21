package com.cts.service;

import org.springframework.stereotype.Service;

@Service
public class ApplicationRegistrationService{

    public String getStateBySSN(Long ssn) {
        if (String.valueOf(ssn).length() != 9) {
            return "Invalid SSN";
        }
        int stateCode = (int) (ssn % 100);
        return switch (stateCode) {
            case 1 -> "Washington DC";
            case 2 -> "Ohio";
            case 3 -> "Texas";
            case 4 -> "California";
            case 5 -> "Florida";
            default -> "Invalid SSN";
        };
    }
}
