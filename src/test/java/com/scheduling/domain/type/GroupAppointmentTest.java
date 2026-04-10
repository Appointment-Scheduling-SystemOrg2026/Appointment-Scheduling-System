package com.scheduling.domain.type;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GroupAppointmentTest {

    @Test
    void testGroupAppointmentProperties() {
        GroupAppointment group = new GroupAppointment();

        assertEquals("Group session with multiple participants", group.getDescription());

        assertEquals(120, group.getMaxDuration());

        assertEquals(10, group.getMaxParticipants());
        
        assertEquals("Group", group.getTypeName());
    }
}