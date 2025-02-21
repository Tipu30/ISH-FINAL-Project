package com.cts.bindings;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class UserData {

	    private int id; // Auto-incremented Primary Key
	    
	    
	    private String fullName; 
	    
	
	    private String email; 

	  
	    private String password; 

	    
	    private String role; // "CITIZEN", "ADMIN"
	
}
