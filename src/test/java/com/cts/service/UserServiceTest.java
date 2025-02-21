package com.cts.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import com.cts.bindings.CitizenAppRegistrationInputs;
import com.cts.bindings.EligibilityDetailsOutput;
import com.cts.bindings.UserData;
import com.cts.entity.CitizenAppRegistrationEntity;
import com.cts.entity.EligibilityDetailsEntity;
import com.cts.entity.UserEntity;
import com.cts.repository.ApplicationRegistrationRepository;
import com.cts.repository.EligibilityDeterminationRepository;
import com.cts.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private EligibilityDeterminationRepository eligibilityRepo;
    
    @Mock
    private ApplicationRegistrationRepository applicationRegRepo;
    
    @InjectMocks
    private UserService userService;

    private UserEntity mockUser;
    private CitizenAppRegistrationEntity mockCitizen;
    private EligibilityDetailsEntity mockEligibility;

    @BeforeEach
    void setUp() {
        mockUser = new UserEntity("test@example.com", "password123", "USER");
        mockCitizen = new CitizenAppRegistrationEntity();
        mockCitizen.setAppid(101);
        mockCitizen.setSsn(123456789L);
        
        mockEligibility = new EligibilityDetailsEntity();
        mockEligibility.setHolderName("John Doe");
        mockEligibility.setBenifitAmt(500.0);
    }

    @Test
    void testIsUsernameTaken() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);
        assertTrue(userService.isUsernameTaken("test@example.com"));
        verify(userRepository, times(1)).existsByEmail("test@example.com");
    }

    @Test
    void testRegisterCitizen_Success() {
        UserData userData = new UserData();
        userData.setEmail("newuser@example.com");
        userData.setPassword("newpass");
        userData.setRole("USER");
        
        when(userRepository.existsByEmail("newuser@example.com")).thenReturn(false);
        when(userRepository.save(any(UserEntity.class))).thenReturn(mockUser);
        
        assertTrue(userService.registerCitizen(userData));
    }

    @Test
    void testValidateLogin_Success() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));
        Optional<UserEntity> result = userService.validateLogin("test@example.com", "password123");
        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get().getEmail());
    }

    @Test
    void testValidateLogin_Fail_WrongPassword() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));
        Optional<UserEntity> result = userService.validateLogin("test@example.com", "wrongpass");
        assertFalse(result.isPresent());
    }

    @Test
    void testRegisterCitizenDetails() {
        CitizenAppRegistrationInputs input = new CitizenAppRegistrationInputs();
        BeanUtils.copyProperties(mockCitizen, input);
        when(applicationRegRepo.save(any(CitizenAppRegistrationEntity.class))).thenReturn(mockCitizen);
        String result = userService.registerCitizenDetails(input);
        assertEquals("SAVE_SUCCESS", result);
    }

    @Test
    void testGetAppId_Success() {
        when(applicationRegRepo.findBySSN(123456789L)).thenReturn(mockCitizen);
        Integer appId = userService.getAppId(123456789L);
        assertNotNull(appId);
        assertEquals(101, appId);
    }

    @Test
    void testShowMyBenefits() {
        when(eligibilityRepo.findByHolderName("John Doe")).thenReturn(Collections.singletonList(mockEligibility));
        List<EligibilityDetailsOutput> benefits = userService.showMyBenefits("John Doe");
        assertFalse(benefits.isEmpty());
        assertEquals(1, benefits.size());
        assertEquals("John Doe", benefits.get(0).getHolderName());
    }

    @Test
    void testGetTotalBenefitsByHolderName() {
        when(eligibilityRepo.getTotalBenefitsByHolderName("John Doe")).thenReturn(500.0);
        Double totalBenefits = userService.getTotalBenefitsByHolderName("John Doe");
        assertEquals(500.0, totalBenefits);
    }

    @Test
    void testGetEligibileCitizenDetails_Success() {
        when(applicationRegRepo.findByFullName("John Doe")).thenReturn(Optional.of(mockCitizen));
        CitizenAppRegistrationInputs details = userService.getEligibileCitizenDetails("John Doe");
        assertNotNull(details);
    }
}
