package com.cts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.bindings.CitizenAppRegistrationInputs;
import com.cts.bindings.EligibilityDetailsOutput;
import com.cts.bindings.UserData;
import com.cts.entity.CitizenAppRegistrationEntity;
import com.cts.entity.EligibilityDetailsEntity;
import com.cts.entity.UserEntity;
import com.cts.repository.ApplicationRegistrationRepository;
import com.cts.repository.EligibilityDeterminationRepository;
import com.cts.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EligibilityDeterminationRepository eligibilityRepo;
	
	@Autowired
	private ApplicationRegistrationRepository applicationRegRepo;
		
	
	@PostConstruct 
	public void createDefaultAdmin() {
	        boolean adminExists = userRepository.existsByRole("ADMIN");

	        if(!adminExists) { 
	            UserEntity admin = new UserEntity("admin@gmail.com", "admin123", "ADMIN");
	            userRepository.save(admin);
	            System.out.println("Default Admin User Created: Username - admin, Password - admin123");
	        } else {
	            System.out.println("Admin user already exists. No need to create a new one.");
	        }
	    }
	public boolean isUsernameTaken(String email) {
        return userRepository.existsByEmail(email);
    }

    
    public boolean registerCitizen(UserData userData) {
        if (isUsernameTaken(userData.getEmail())) {
            return false; // Registration failed
        }
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(userData, entity);

        userRepository.save(entity);
        return true; // Registration successful
    }
    
    public Optional<UserEntity> validateLogin(String email, String password) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        System.out.println( userOptional.get());
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            System.out.println( user);
           
            if (user.getPassword().equals(password)) { 
                return Optional.of(user);
            }
        }
        return Optional.empty(); 
    }
    
    
    
	//CREATE B.logic for citizenRegistration details persistence in Table
	public String registerCitizenDetails(CitizenAppRegistrationInputs citizenDetails) {
		//convert citizenData binding obj to citizenEntity
		CitizenAppRegistrationEntity citizenEntity = new CitizenAppRegistrationEntity();
		BeanUtils.copyProperties(citizenDetails, citizenEntity);
		
		//save the object
//		PlanEntity savedEntity = planRepo.save(planEntity);
		CitizenAppRegistrationEntity savedEntity =  applicationRegRepo.save(citizenEntity);
		return "SAVE_SUCCESS";
		
	}
	
	//get appid by name
	public Integer getAppId(Long ssn) {
	CitizenAppRegistrationEntity citizenEntity = applicationRegRepo.findBySSN(ssn);
	return citizenEntity.getAppid();
	}
    
    
    
	//Get User Specific Benefit issuance
//	GET/READ/VIEW B.Logic for benefit inssuance
//	
	public List<EligibilityDetailsOutput> showMyBenefits(String holderName) {	
		List<EligibilityDetailsEntity> listEntities = eligibilityRepo.findByHolderName(holderName);
		List<EligibilityDetailsOutput> listEligibilityData = new ArrayList<>();
		listEntities.forEach(entity->{
			EligibilityDetailsOutput data = new EligibilityDetailsOutput();
			BeanUtils.copyProperties(entity, data);
			listEligibilityData.add(data);
		});
//		System.out.println(listEligibilityData);
		return listEligibilityData;
	}
	
	//Other necessary methods to get data from db tables
//		
//			public int countCitizenApprovedPlans(String holderName) {
//				return eligibilityRepo.countByplanStatus("approved");
//			}
//
//			public int countDeniedCitizen() {
//				return eligibilityRepo.countByplanStatus("denied");
//			}
//			
			public Double getTotalBenefitsByHolderName(String holderName) {
				return eligibilityRepo.getTotalBenefitsByHolderName(holderName);
			}
	

	//Get citizenRegisteredApplication Details
			
		public CitizenAppRegistrationInputs	getEligibileCitizenDetails(String fullname){
			Optional<CitizenAppRegistrationEntity> optEntity = applicationRegRepo.findByFullName(fullname);
			CitizenAppRegistrationInputs data = new CitizenAppRegistrationInputs();
			if(optEntity.isPresent()) {
				CitizenAppRegistrationEntity entity = optEntity.get();
				BeanUtils.copyProperties(entity,data);
			}
			return data;
			}
	
	
}


