package com.scheduling.domain.type;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AllTypesCoverageTest {

    @Test
    void testUrgentAppointmentAllMethods() {
        UrgentAppointment type = new UrgentAppointment();
        assertEquals(30, type.getMaxDuration());
        assertEquals(1, type.getMaxParticipants());
        assertTrue(type.isUrgent());
        assertFalse(type.isVirtual());
        assertTrue(type.getDescription().contains("Emergency"));
    }

    @Test
    void testFollowUpAppointmentAllMethods() {
        FollowUpAppointment type = new FollowUpAppointment();
        assertEquals(45, type.getMaxDuration());
        assertEquals(2, type.getMaxParticipants());
        assertFalse(type.isUrgent());
        assertFalse(type.isVirtual());
        assertTrue(type.getDescription().contains("follow"));
    }

    @Test
    void testAssessmentAppointmentAllMethods() {
        AssessmentAppointment type = new AssessmentAppointment();
        assertEquals(90, type.getMaxDuration());
        assertEquals(3, type.getMaxParticipants());
        assertNotNull(type.getDescription());
    }

    @Test
    void testVirtualAppointmentAllMethods() {
        VirtualAppointment type = new VirtualAppointment();
        assertEquals(45, type.getMaxDuration());
        assertEquals(5, type.getMaxParticipants());
        assertTrue(type.isVirtual());
        assertFalse(type.isUrgent());
        assertNotNull(type.getDescription());
    }

    @Test
    void testInPersonAppointmentAllMethods() {
        InPersonAppointment type = new InPersonAppointment();
        assertEquals(60, type.getMaxDuration());
        assertEquals(4, type.getMaxParticipants());
        assertFalse(type.isVirtual());
        assertNotNull(type.getDescription());
    }

    @Test
    void testIndividualAppointmentAllMethods() {
        IndividualAppointment type = new IndividualAppointment();
        assertEquals(60, type.getMaxDuration());
        assertEquals(1, type.getMaxParticipants());
        assertNotNull(type.getDescription());
    }

    @Test
    void testGroupAppointmentAllMethods() {
        GroupAppointment type = new GroupAppointment();
        assertEquals(120, type.getMaxDuration());
        assertEquals(10, type.getMaxParticipants());
        assertNotNull(type.getDescription());
    }
}