package com.cts.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ApplicationRegistrationServiceTest {

    private ApplicationRegistrationService applicationRegistrationService;
    private static final Logger logger = LoggerFactory.getLogger(ApplicationRegistrationServiceTest.class);

    @BeforeEach
    void setUp() {
        applicationRegistrationService = new ApplicationRegistrationService();
    }

    @Test
    void testGetStateBySSN_ValidSSN_WashingtonDC() {
        Long ssn = 123456701L;
        assertEquals("Washington DC", applicationRegistrationService.getStateBySSN(ssn));
    }

    @Test
    void testGetStateBySSN_ValidSSN_Ohio() {
        Long ssn = 987654302L;
        assertEquals("Ohio", applicationRegistrationService.getStateBySSN(ssn));
    }

    @Test
    void testGetStateBySSN_ValidSSN_Texas() {
        Long ssn = 456123903L;
        assertEquals("Texas", applicationRegistrationService.getStateBySSN(ssn));
    }

    @Test
    void testGetStateBySSN_ValidSSN_California() {
        Long ssn = 321789904L;
        assertEquals("California", applicationRegistrationService.getStateBySSN(ssn));
    }

    @Test
    void testGetStateBySSN_ValidSSN_Florida() {
        Long ssn = 654987905L;
        assertEquals("Florida", applicationRegistrationService.getStateBySSN(ssn));
    }

    @Test
    void testGetStateBySSN_InvalidSSN_LengthCheck() {
        Long ssn = 1234567L; // Too short
        assertEquals("Invalid SSN", applicationRegistrationService.getStateBySSN(ssn));
    }

    @Test
    void testGetStateBySSN_InvalidSSN_WrongCode() {
        Long ssn = 987654399L; // Unknown state code
        assertEquals("Invalid SSN", applicationRegistrationService.getStateBySSN(ssn));
    }

    @Test
    void testGetStateBySSN_NullValue() {
        assertEquals("Invalid SSN", applicationRegistrationService.getStateBySSN(null));
    }
}























//package com.cts.service;
//
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//class ApplicationRegistrationServiceTest {
//
//    private ApplicationRegistrationService applicationRegistrationService;
//    private static final Logger logger = LoggerFactory.getLogger(ApplicationRegistrationServiceTest.class);
//
//    @BeforeEach
//    void setUp() {
//        applicationRegistrationService = new ApplicationRegistrationService();
//    }
//
//    @Test
//    void testGetStateBySSN_ValidSSN_WashingtonDC() {
//        Long ssn = 123456701L; // Ends with 01
//        String state = applicationRegistrationService.getStateBySSN(ssn);
//        assertEquals("Washington DC", state);
//        logger.info("Test passed: SSN {} -> Washington DC", ssn);
//    }
//
//    @Test
//    void testGetStateBySSN_ValidSSN_Ohio() {
//        Long ssn = 987654302L; // Ends with 02
//        String state = applicationRegistrationService.getStateBySSN(ssn);
//        assertEquals("Ohio", state);
//        logger.info("Test passed: SSN {} -> Ohio", ssn);
//    }
//
//    @Test
//    void testGetStateBySSN_ValidSSN_Texas() {
//        Long ssn = 456123903L; // Ends with 03
//        String state = applicationRegistrationService.getStateBySSN(ssn);
//        assertEquals("Texas", state);
//        logger.info("Test passed: SSN {} -> Texas", ssn);
//    }
//
//    @Test
//    void testGetStateBySSN_ValidSSN_California() {
//        Long ssn = 321789904L; // Ends with 04
//        String state = applicationRegistrationService.getStateBySSN(ssn);
//        assertEquals("California", state);
//        logger.info("Test passed: SSN {} -> California", ssn);
//    }
//
//    @Test
//    void testGetStateBySSN_ValidSSN_Florida() {
//        Long ssn = 654987905L; // Ends with 05
//        String state = applicationRegistrationService.getStateBySSN(ssn);
//        assertEquals("Florida", state);
//        logger.info("Test passed: SSN {} -> Florida", ssn);
//    }
//
//    @Test
//    void testGetStateBySSN_InvalidSSN_LengthCheck() {
//        Long ssn = 1234567L; // Less than 9 digits
//        String state = applicationRegistrationService.getStateBySSN(ssn);
//        assertEquals("Invalid SSN", state);
//        logger.info("Test passed: SSN {} (invalid length) -> Invalid SSN", ssn);
//    }
//
//    @Test
//    void testGetStateBySSN_InvalidSSN_WrongCode() {
//        Long ssn = 987654399L; // Ends with 99 (not in switch case)
//        String state = applicationRegistrationService.getStateBySSN(ssn);
//        assertEquals("Invalid SSN", state);
//        logger.info("Test passed: SSN {} (unknown code) -> Invalid SSN", ssn);
//    }
//
//    @Test
//    void testGetStateBySSN_InvalidSSN_ZeroValue() {
//        Long ssn = 0L; // Zero input
//        String state = applicationRegistrationService.getStateBySSN(ssn);
//        assertEquals("Invalid SSN", state);
//        logger.info("Test passed: SSN {} -> Invalid SSN", ssn);
//    }
//
//    @Test
//    void testGetStateBySSN_NullValue() {
//        Long ssn = null; // Null input
//        String state = applicationRegistrationService.getStateBySSN(ssn);
//        assertEquals("Invalid SSN", state);
//        logger.info("Test passed: Null SSN -> Invalid SSN");
//    }
//}
