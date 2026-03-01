package com.scheduling.domain.type;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppointmentTypeTest {

    @Test
    void shouldCreateTypes() {

        assertNotNull(new InPersonAppointment());
        assertNotNull(new VirtualAppointment());
        assertNotNull(new GroupAppointment());
    }
}