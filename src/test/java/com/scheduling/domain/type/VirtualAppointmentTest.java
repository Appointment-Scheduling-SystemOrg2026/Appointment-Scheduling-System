package com.scheduling.domain.type;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VirtualAppointmentTest {

    @Test
    void testGetDescription() {
        VirtualAppointment virtual = new VirtualAppointment();
        assertEquals("Remote appointment via video conferencing", virtual.getDescription());
    }

    @Test
    void testGetMaxDuration() {
        VirtualAppointment virtual = new VirtualAppointment();
        assertEquals(45, virtual.getMaxDuration());
    }

    @Test
    void testGetMaxParticipants() {
        VirtualAppointment virtual = new VirtualAppointment();
        assertEquals(5, virtual.getMaxParticipants());
    }

    @Test
    void testIsVirtual() {
        VirtualAppointment virtual = new VirtualAppointment();
        assertTrue(virtual.isVirtual());
    }

    
    @Test
    void testGetTypeName() {
        VirtualAppointment virtual = new VirtualAppointment();
        virtual.getTypeName();
    }
}