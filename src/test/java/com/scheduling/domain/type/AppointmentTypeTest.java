package com.scheduling.domain.type;

import org.junit.jupiter.api.Test;

class AppointmentTypeCoverageTest {

    @Test
    void testTypes() {
        new InPersonAppointment().getTypeName();
        new VirtualAppointment().getTypeName();
        new GroupAppointment().getTypeName();
    }
}


/*package com.scheduling.domain.type;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppointmentTypeTest {

    @Test
    void shouldCreateTypes() {

        assertNotNull(new InPersonAppointment());
        assertNotNull(new VirtualAppointment());
        assertNotNull(new GroupAppointment());
    }
}*/
/*
package com.scheduling.domain.type;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppointmentTypeTest {

    @Test
    void shouldCreateTypes() {

        InPersonAppointment inPerson =
                new InPersonAppointment();

        VirtualAppointment virtual =
                new VirtualAppointment();

        assertNotNull(inPerson);
        assertNotNull(virtual);
    }
}*/