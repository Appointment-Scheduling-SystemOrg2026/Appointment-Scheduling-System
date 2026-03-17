package com.scheduling.domain.type;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Full coverage tests for Appointment Type classes.
 */
class TypeFullCoverageTest {

  

    @Test
    @DisplayName("UrgentAppointment - should have correct description")
    void testUrgentDescription() {
        UrgentAppointment apt = new UrgentAppointment();
        assertEquals("Emergency appointment requiring immediate attention", apt.getDescription());
    }

    @Test
    @DisplayName("UrgentAppointment - should have correct max duration")
    void testUrgentMaxDuration() {
        UrgentAppointment apt = new UrgentAppointment();
        assertEquals(30, apt.getMaxDuration());
    }

    @Test
    @DisplayName("UrgentAppointment - should have correct max participants")
    void testUrgentMaxParticipants() {
        UrgentAppointment apt = new UrgentAppointment();
        assertEquals(1, apt.getMaxParticipants());
    }

    @Test
    @DisplayName("UrgentAppointment - should be urgent")
    void testUrgentIsUrgent() {
        UrgentAppointment apt = new UrgentAppointment();
        assertTrue(apt.isUrgent());
    }

    @Test
    @DisplayName("UrgentAppointment - should not be virtual")
    void testUrgentIsVirtual() {
        UrgentAppointment apt = new UrgentAppointment();
        assertFalse(apt.isVirtual());
    }

    
    @Test
    @DisplayName("FollowUpAppointment - should have correct description")
    void testFollowUpDescription() {
        FollowUpAppointment apt = new FollowUpAppointment();
        assertEquals("Post-treatment follow-up and progress review", apt.getDescription());
    }

    @Test
    @DisplayName("FollowUpAppointment - should have correct max duration")
    void testFollowUpMaxDuration() {
        FollowUpAppointment apt = new FollowUpAppointment();
        assertEquals(45, apt.getMaxDuration());
    }

    @Test
    @DisplayName("FollowUpAppointment - should have correct max participants")
    void testFollowUpMaxParticipants() {
        FollowUpAppointment apt = new FollowUpAppointment();
        assertEquals(2, apt.getMaxParticipants());
    }

    @Test
    @DisplayName("FollowUpAppointment - should not be urgent")
    void testFollowUpIsUrgent() {
        FollowUpAppointment apt = new FollowUpAppointment();
        assertFalse(apt.isUrgent());
    }

    @Test
    @DisplayName("FollowUpAppointment - should not be virtual")
    void testFollowUpIsVirtual() {
        FollowUpAppointment apt = new FollowUpAppointment();
        assertFalse(apt.isVirtual());
    }

   

    @Test
    @DisplayName("AssessmentAppointment - should have correct description")
    void testAssessmentDescription() {
        AssessmentAppointment apt = new AssessmentAppointment();
        assertEquals("Comprehensive assessment and evaluation session", apt.getDescription());
    }

    @Test
    @DisplayName("AssessmentAppointment - should have correct max duration")
    void testAssessmentMaxDuration() {
        AssessmentAppointment apt = new AssessmentAppointment();
        assertEquals(90, apt.getMaxDuration());
    }

    @Test
    @DisplayName("AssessmentAppointment - should have correct max participants")
    void testAssessmentMaxParticipants() {
        AssessmentAppointment apt = new AssessmentAppointment();
        assertEquals(3, apt.getMaxParticipants());
    }

    @Test
    @DisplayName("AssessmentAppointment - should not be urgent")
    void testAssessmentIsUrgent() {
        AssessmentAppointment apt = new AssessmentAppointment();
        assertFalse(apt.isUrgent());
    }

    @Test
    @DisplayName("AssessmentAppointment - should not be virtual")
    void testAssessmentIsVirtual() {
        AssessmentAppointment apt = new AssessmentAppointment();
        assertFalse(apt.isVirtual());
    }

    

    @Test
    @DisplayName("VirtualAppointment - should have correct description")
    void testVirtualDescription() {
        VirtualAppointment apt = new VirtualAppointment();
        assertEquals("Remote appointment via video conferencing", apt.getDescription());
    }

    @Test
    @DisplayName("VirtualAppointment - should have correct max duration")
    void testVirtualMaxDuration() {
        VirtualAppointment apt = new VirtualAppointment();
        assertEquals(45, apt.getMaxDuration());
    }

    @Test
    @DisplayName("VirtualAppointment - should have correct max participants")
    void testVirtualMaxParticipants() {
        VirtualAppointment apt = new VirtualAppointment();
        assertEquals(5, apt.getMaxParticipants());
    }

    @Test
    @DisplayName("VirtualAppointment - should be virtual")
    void testVirtualIsVirtual() {
        VirtualAppointment apt = new VirtualAppointment();
        assertTrue(apt.isVirtual());
    }

    @Test
    @DisplayName("VirtualAppointment - should not be urgent")
    void testVirtualIsUrgent() {
        VirtualAppointment apt = new VirtualAppointment();
        assertFalse(apt.isUrgent());
    }

   

    @Test
    @DisplayName("InPersonAppointment - should have correct description")
    void testInPersonDescription() {
        InPersonAppointment apt = new InPersonAppointment();
        assertEquals("Face-to-face appointment at the facility", apt.getDescription());
    }

    @Test
    @DisplayName("InPersonAppointment - should have correct max duration")
    void testInPersonMaxDuration() {
        InPersonAppointment apt = new InPersonAppointment();
        assertEquals(60, apt.getMaxDuration());
    }

    @Test
    @DisplayName("InPersonAppointment - should have correct max participants")
    void testInPersonMaxParticipants() {
        InPersonAppointment apt = new InPersonAppointment();
        assertEquals(4, apt.getMaxParticipants());
    }

    @Test
    @DisplayName("InPersonAppointment - should not be urgent")
    void testInPersonIsUrgent() {
        InPersonAppointment apt = new InPersonAppointment();
        assertFalse(apt.isUrgent());
    }

    @Test
    @DisplayName("InPersonAppointment - should not be virtual")
    void testInPersonIsVirtual() {
        InPersonAppointment apt = new InPersonAppointment();
        assertFalse(apt.isVirtual());
    }

    

    @Test
    @DisplayName("IndividualAppointment - should have correct description")
    void testIndividualDescription() {
        IndividualAppointment apt = new IndividualAppointment();
        assertEquals("One-on-one private appointment", apt.getDescription());
    }

    @Test
    @DisplayName("IndividualAppointment - should have correct max duration")
    void testIndividualMaxDuration() {
        IndividualAppointment apt = new IndividualAppointment();
        assertEquals(60, apt.getMaxDuration());
    }

    @Test
    @DisplayName("IndividualAppointment - should have correct max participants")
    void testIndividualMaxParticipants() {
        IndividualAppointment apt = new IndividualAppointment();
        assertEquals(1, apt.getMaxParticipants());
    }

    @Test
    @DisplayName("IndividualAppointment - should not be urgent")
    void testIndividualIsUrgent() {
        IndividualAppointment apt = new IndividualAppointment();
        assertFalse(apt.isUrgent());
    }

    @Test
    @DisplayName("IndividualAppointment - should not be virtual")
    void testIndividualIsVirtual() {
        IndividualAppointment apt = new IndividualAppointment();
        assertFalse(apt.isVirtual());
    }

    

    @Test
    @DisplayName("GroupAppointment - should have correct description")
    void testGroupDescription() {
        GroupAppointment apt = new GroupAppointment();
        assertEquals("Group session with multiple participants", apt.getDescription());
    }

    @Test
    @DisplayName("GroupAppointment - should have correct max duration")
    void testGroupMaxDuration() {
        GroupAppointment apt = new GroupAppointment();
        assertEquals(120, apt.getMaxDuration());
    }

    @Test
    @DisplayName("GroupAppointment - should have correct max participants")
    void testGroupMaxParticipants() {
        GroupAppointment apt = new GroupAppointment();
        assertEquals(10, apt.getMaxParticipants());
    }

    @Test
    @DisplayName("GroupAppointment - should not be urgent")
    void testGroupIsUrgent() {
        GroupAppointment apt = new GroupAppointment();
        assertFalse(apt.isUrgent());
    }

    @Test
    @DisplayName("GroupAppointment - should not be virtual")
    void testGroupIsVirtual() {
        GroupAppointment apt = new GroupAppointment();
        assertFalse(apt.isVirtual());
    }

   

    @Test
    @DisplayName("AppointmentType - polymorphism test for getDescription")
    void testPolymorphismDescription() {
        AppointmentType[] types = {
                new UrgentAppointment(),
                new FollowUpAppointment(),
                new AssessmentAppointment(),
                new VirtualAppointment(),
                new InPersonAppointment(),
                new IndividualAppointment(),
                new GroupAppointment()
        };

        for (AppointmentType type : types) {
            assertNotNull(type.getDescription());
            assertFalse(type.getDescription().isEmpty());
        }
    }

    @Test
    @DisplayName("AppointmentType - polymorphism test for getMaxDuration")
    void testPolymorphismMaxDuration() {
        AppointmentType[] types = {
                new UrgentAppointment(),
                new FollowUpAppointment(),
                new AssessmentAppointment(),
                new VirtualAppointment(),
                new InPersonAppointment(),
                new IndividualAppointment(),
                new GroupAppointment()
        };

        for (AppointmentType type : types) {
            assertTrue(type.getMaxDuration() > 0);
        }
    }

    @Test
    @DisplayName("AppointmentType - polymorphism test for getMaxParticipants")
    void testPolymorphismMaxParticipants() {
        AppointmentType[] types = {
                new UrgentAppointment(),
                new FollowUpAppointment(),
                new AssessmentAppointment(),
                new VirtualAppointment(),
                new InPersonAppointment(),
                new IndividualAppointment(),
                new GroupAppointment()
        };

        for (AppointmentType type : types) {
            assertTrue(type.getMaxParticipants() > 0);
        }
    }

    @Test
    @DisplayName("Should test isUrgent for all types")
    void testIsUrgentForAllTypes() {
        assertTrue(new UrgentAppointment().isUrgent());
        assertFalse(new FollowUpAppointment().isUrgent());
        assertFalse(new AssessmentAppointment().isUrgent());
        assertFalse(new VirtualAppointment().isUrgent());
        assertFalse(new InPersonAppointment().isUrgent());
        assertFalse(new IndividualAppointment().isUrgent());
        assertFalse(new GroupAppointment().isUrgent());
    }

    @Test
    @DisplayName("Should test isVirtual for all types")
    void testIsVirtualForAllTypes() {
        assertFalse(new UrgentAppointment().isVirtual());
        assertFalse(new FollowUpAppointment().isVirtual());
        assertFalse(new AssessmentAppointment().isVirtual());
        assertTrue(new VirtualAppointment().isVirtual());
        assertFalse(new InPersonAppointment().isVirtual());
        assertFalse(new IndividualAppointment().isVirtual());
        assertFalse(new GroupAppointment().isVirtual());
    }

    @Test
    @DisplayName("Should verify all types implement AppointmentType")
    void testAllImplementInterface() {
        AppointmentType[] types = {
                new UrgentAppointment(),
                new FollowUpAppointment(),
                new AssessmentAppointment(),
                new VirtualAppointment(),
                new InPersonAppointment(),
                new IndividualAppointment(),
                new GroupAppointment()
        };

        for (AppointmentType type : types) {
            assertTrue(type instanceof AppointmentType);
        }
    }
}