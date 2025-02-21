package com.cts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Auto-incremented Primary Key
    
    @Column(unique = true, nullable = false)
    private String fullName; 
    
    @Column(unique = true, nullable = false)
    private String email; 

    @Column(nullable = false)
    private String password; 

    @Column(nullable = false)
    private String role; // "CITIZEN", "ADMIN"

    
    public UserEntity() {
    	
    	
    }
    
    public UserEntity(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
   
}
