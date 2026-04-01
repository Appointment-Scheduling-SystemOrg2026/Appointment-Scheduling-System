package com.scheduling.domain.type;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InPersonAppointmentTest {

    @Test
    void getDescription_shouldReturnValue() {
        InPersonAppointment appt = new InPersonAppointment();

        assertEquals("Face-to-face appointment at the facility",
                appt.getDescription());
    }

    @Test
    void getMaxDuration_shouldBe60() {
        InPersonAppointment appt = new InPersonAppointment();

        assertEquals(60, appt.getMaxDuration());
    }

    @Test
    void getMaxParticipants_shouldBe4() {
        InPersonAppointment appt = new InPersonAppointment();

        assertEquals(4, appt.getMaxParticipants());
    }

    @Test
    void getTypeName_shouldReturnCorrectValue() {
        InPersonAppointment appt = new InPersonAppointment();

        assertEquals("IN_PERSON", appt.getTypeName());
    }

    @Test
    void getTypeName1_shouldReturnCorrectValue() {
        InPersonAppointment appt = new InPersonAppointment();

        assertEquals("IN_PERSON_1", appt.getTypeName1());
    }
}