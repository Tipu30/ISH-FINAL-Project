package com.cts.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import com.cts.bindings.EligibilityDetailsOutput;
import com.cts.bindings.PlanEligibilityDetermination;
import com.cts.entity.EligibilityDetailsEntity;
import com.cts.entity.PlanEligibilityDeterminationEntity;
import com.cts.repository.ApplicationRegistrationRepository;
import com.cts.repository.EligibilityDeterminationRepository;
import com.cts.repository.PlanEligibilityDeterminationRepository;

@ExtendWith(MockitoExtension.class)
class EligibilityDeterminationServiceTest {

    @Mock
    private ApplicationRegistrationRepository applicationRepo;

    @Mock
    private PlanEligibilityDeterminationRepository planEligibilityRepo;

    @Mock
    private EligibilityDeterminationRepository eligibilityRepo;

    @InjectMocks
    private EligibilityDeterminationService eligibilityService;

    private PlanEligibilityDetermination testPlanEligibility;
    private PlanEligibilityDeterminationEntity testPlanEntity;

    @BeforeEach
    void setUp() {
        testPlanEligibility = new PlanEligibilityDetermination();
        testPlanEligibility.setAppid(1001);
        testPlanEligibility.setCitizenName("John Doe");
        testPlanEligibility.setCitizenSSN(123456789L);
        testPlanEligibility.setDob(LocalDate.of(1980, 1, 1));
        testPlanEligibility.setSelectedPlan("SNAP");
        testPlanEligibility.setIncome(150);
        testPlanEligibility.setKidAge(10);
        testPlanEligibility.setPassingOutYear(2015);

        testPlanEntity = new PlanEligibilityDeterminationEntity();
        BeanUtils.copyProperties(testPlanEligibility, testPlanEntity);
    }

    @Test
    void testRegisterPlanEligibilityDetails() {
        when(planEligibilityRepo.save(any(PlanEligibilityDeterminationEntity.class))).thenReturn(testPlanEntity);

        String result = eligibilityService.registerPlanEligibilityDetails(testPlanEligibility);
        assertEquals("SAVE_SUCCESS", result);

        verify(planEligibilityRepo, times(1)).save(any(PlanEligibilityDeterminationEntity.class));
    }

    @Test
    void testDetermineEligibility_SNAP_Approved() {
        when(planEligibilityRepo.findByAppid(1001)).thenReturn(Optional.of(testPlanEntity));
        when(eligibilityRepo.save(any(EligibilityDetailsEntity.class))).thenAnswer(i -> i.getArgument(0));

        EligibilityDetailsOutput output = eligibilityService.determineEligibility(1001);

        assertNotNull(output);
        assertEquals("SNAP", output.getPlanName());
        assertEquals("Approved", output.getPlanStatus());
        assertEquals(300.0, output.getBenifitAmt());
    }

    @Test
    void testDetermineEligibility_SNAP_Denied_HighIncome() {
        testPlanEligibility.setIncome(500);
        BeanUtils.copyProperties(testPlanEligibility, testPlanEntity);

        when(planEligibilityRepo.findByAppid(1001)).thenReturn(Optional.of(testPlanEntity));
        when(eligibilityRepo.save(any(EligibilityDetailsEntity.class))).thenAnswer(i -> i.getArgument(0));

        EligibilityDetailsOutput output = eligibilityService.determineEligibility(1001);

        assertEquals("Denied", output.getPlanStatus());
        assertEquals("High Income", output.getDenialReason());
    }

    @Test
    void testDetermineEligibility_CCAP_Approved() {
        testPlanEligibility.setSelectedPlan("CCAP");
        testPlanEligibility.setIncome(200);
        testPlanEligibility.setKidAge(10);
        BeanUtils.copyProperties(testPlanEligibility, testPlanEntity);

        when(planEligibilityRepo.findByAppid(1001)).thenReturn(Optional.of(testPlanEntity));
        when(eligibilityRepo.save(any(EligibilityDetailsEntity.class))).thenAnswer(i -> i.getArgument(0));

        EligibilityDetailsOutput output = eligibilityService.determineEligibility(1001);

        assertEquals("CCAP", output.getPlanName());
        assertEquals("Approved", output.getPlanStatus());
    }

    @Test
    void testDetermineEligibility_CCAP_Denied_NoKids() {
        testPlanEligibility.setSelectedPlan("CCAP");
        testPlanEligibility.setKidAge(18); // Kid is older than 16
        BeanUtils.copyProperties(testPlanEligibility, testPlanEntity);

        when(planEligibilityRepo.findByAppid(1001)).thenReturn(Optional.of(testPlanEntity));
        when(eligibilityRepo.save(any(EligibilityDetailsEntity.class))).thenAnswer(i -> i.getArgument(0));

        EligibilityDetailsOutput output = eligibilityService.determineEligibility(1001);

        assertEquals("Denied", output.getPlanStatus());
        assertEquals("CCAP rules are not satisfied", output.getDenialReason());
    }

    @Test
    void testDetermineEligibility_MEDCARE_Approved() {
        testPlanEligibility.setSelectedPlan("MEDCARE");
        testPlanEligibility.setDob(LocalDate.of(1950, 1, 1)); // 74 years old
        BeanUtils.copyProperties(testPlanEligibility, testPlanEntity);

        when(planEligibilityRepo.findByAppid(1001)).thenReturn(Optional.of(testPlanEntity));
        when(eligibilityRepo.save(any(EligibilityDetailsEntity.class))).thenAnswer(i -> i.getArgument(0));

        EligibilityDetailsOutput output = eligibilityService.determineEligibility(1001);

        assertEquals("MEDCARE", output.getPlanName());
        assertEquals("Approved", output.getPlanStatus());
    }

    @Test
    void testDetermineEligibility_MEDCARE_Denied_AgeTooLow() {
        testPlanEligibility.setSelectedPlan("MEDCARE");
        testPlanEligibility.setDob(LocalDate.of(1990, 1, 1)); // 34 years old
        BeanUtils.copyProperties(testPlanEligibility, testPlanEntity);

        when(planEligibilityRepo.findByAppid(1001)).thenReturn(Optional.of(testPlanEntity));
        when(eligibilityRepo.save(any(EligibilityDetailsEntity.class))).thenAnswer(i -> i.getArgument(0));

        EligibilityDetailsOutput output = eligibilityService.determineEligibility(1001);

        assertEquals("Denied", output.getPlanStatus());
        assertEquals("MEDCARE rules are not satisfied", output.getDenialReason());
    }

    @Test
    void testDetermineEligibility_MEDAID_Approved() {
        testPlanEligibility.setSelectedPlan("MEDAID");
        testPlanEligibility.setIncome(350);
        BeanUtils.copyProperties(testPlanEligibility, testPlanEntity);

        when(planEligibilityRepo.findByAppid(1001)).thenReturn(Optional.of(testPlanEntity));
        when(eligibilityRepo.save(any(EligibilityDetailsEntity.class))).thenAnswer(i -> i.getArgument(0));

        EligibilityDetailsOutput output = eligibilityService.determineEligibility(1001);

        assertEquals("MEDAID", output.getPlanName());
        assertEquals("Approved", output.getPlanStatus());
    }

    @Test
    void testDetermineEligibility_CA_JW_Approved() {
        testPlanEligibility.setSelectedPlan("CAJW");
        testPlanEligibility.setIncome(0);
        testPlanEligibility.setPassingOutYear(2010); // Passed out before current year
        BeanUtils.copyProperties(testPlanEligibility, testPlanEntity);

        when(planEligibilityRepo.findByAppid(1001)).thenReturn(Optional.of(testPlanEntity));
        when(eligibilityRepo.save(any(EligibilityDetailsEntity.class))).thenAnswer(i -> i.getArgument(0));

        EligibilityDetailsOutput output = eligibilityService.determineEligibility(1001);

        assertEquals("CAJW", output.getPlanName());
        assertEquals("Approved", output.getPlanStatus());
    }
}
