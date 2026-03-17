package com.scheduling.domain.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppointmentStatusTest {

    @Test
    void testAvailableToString() {
        assertEquals("Available", AppointmentStatus.AVAILABLE.toString());
    }

    @Test
    void testConfirmedToString() {
        assertEquals("Confirmed", AppointmentStatus.CONFIRMED.toString());
    }

    @Test
    void testCancelledToString() {
        assertEquals("Cancelled", AppointmentStatus.CANCELLED.toString());
    }

    @Test
    void testCompletedToString() {
        assertEquals("Completed", AppointmentStatus.COMPLETED.toString());
    }

    @Test
    void testValuesCount() {
        assertEquals(4, AppointmentStatus.values().length);
    }

    @Test
    void testValueOf() {
        assertEquals(AppointmentStatus.AVAILABLE, AppointmentStatus.valueOf("AVAILABLE"));
        assertEquals(AppointmentStatus.CONFIRMED, AppointmentStatus.valueOf("CONFIRMED"));
        assertEquals(AppointmentStatus.CANCELLED, AppointmentStatus.valueOf("CANCELLED"));
        assertEquals(AppointmentStatus.COMPLETED, AppointmentStatus.valueOf("COMPLETED"));
    }
}