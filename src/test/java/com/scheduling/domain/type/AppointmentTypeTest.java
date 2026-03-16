package com.scheduling.domain.type;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppointmentTypeTest {

    @Test
    void testUrgentAppointment() {
        UrgentAppointment type = new UrgentAppointment();
        assertEquals(30, type.getMaxDuration());
        assertEquals(1, type.getMaxParticipants());
        assertTrue(type.isUrgent());
        assertFalse(type.isVirtual());
        assertNotNull(type.getDescription());
    }

    @Test
    void testFollowUpAppointment() {
        FollowUpAppointment type = new FollowUpAppointment();
        assertEquals(45, type.getMaxDuration());
        assertEquals(2, type.getMaxParticipants());
        assertNotNull(type.getDescription());
    }

    @Test
    void testAssessmentAppointment() {
        AssessmentAppointment type = new AssessmentAppointment();
        assertEquals(90, type.getMaxDuration());
        assertEquals(3, type.getMaxParticipants());
        assertNotNull(type.getDescription());
    }

    @Test
    void testVirtualAppointment() {
        VirtualAppointment type = new VirtualAppointment();
        assertEquals(45, type.getMaxDuration());
        assertEquals(5, type.getMaxParticipants());
        assertTrue(type.isVirtual());
        assertNotNull(type.getDescription());
    }

    @Test
    void testInPersonAppointment() {
        InPersonAppointment type = new InPersonAppointment();
        assertEquals(60, type.getMaxDuration());
        assertEquals(4, type.getMaxParticipants());
        assertNotNull(type.getDescription());
    }

    @Test
    void testIndividualAppointment() {
        IndividualAppointment type = new IndividualAppointment();
        assertEquals(60, type.getMaxDuration());
        assertEquals(1, type.getMaxParticipants());
        assertNotNull(type.getDescription());
    }

    @Test
    void testGroupAppointment() {
        GroupAppointment type = new GroupAppointment();
        assertEquals(120, type.getMaxDuration());
        assertEquals(10, type.getMaxParticipants());
        assertNotNull(type.getDescription());
    }

    @Test
    void testAllTypesHaveDescription() {
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
            assertTrue(type.getMaxDuration() > 0);
            assertTrue(type.getMaxParticipants() > 0);
        }
    }
}