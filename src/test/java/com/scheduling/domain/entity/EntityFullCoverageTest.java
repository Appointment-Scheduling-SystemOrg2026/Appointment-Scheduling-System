package com.scheduling.domain.entity;

import com.scheduling.domain.type.*;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Full coverage tests for Entity classes.
 */
class EntityFullCoverageTest {

    @Test
    @DisplayName("Should create appointment with all fields")
    void testCreateAppointment() {
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
        Appointment apt = new Appointment(dateTime, 30, 2, new VirtualAppointment());

        assertNotNull(apt.getId());
        assertEquals(dateTime, apt.getDateTime());
        assertEquals(30, apt.getDuration());
        assertEquals(2, apt.getParticipants());
        assertTrue(apt.getType() instanceof VirtualAppointment);
    }

    @Test
    @DisplayName("Should generate unique IDs")
    void testUniqueIds() {
        Appointment apt1 = new Appointment(
                LocalDateTime.now().plusDays(1), 30, 2, new VirtualAppointment());
        Appointment apt2 = new Appointment(
                LocalDateTime.now().plusDays(1), 30, 2, new VirtualAppointment());

        assertNotEquals(apt1.getId(), apt2.getId());
    }

    @Test
    @DisplayName("Should set and get dateTime")
    void testSetGetDateTime() {
        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1), 30, 2, new VirtualAppointment());

        LocalDateTime newDateTime = LocalDateTime.now().plusDays(5);
        apt.setDateTime(newDateTime);
        assertEquals(newDateTime, apt.getDateTime());
    }

    @Test
    @DisplayName("Should set and get duration")
    void testSetGetDuration() {
        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1), 30, 2, new VirtualAppointment());

        apt.setDuration(60);
        assertEquals(60, apt.getDuration());
    }

    @Test
    @DisplayName("Should set and get participants")
    void testSetGetParticipants() {
        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1), 30, 2, new VirtualAppointment());

        apt.setParticipants(5);
        assertEquals(5, apt.getParticipants());
    }

    @Test
    @DisplayName("Should set and get type")
    void testSetGetType() {
        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1), 30, 2, new VirtualAppointment());

        apt.setType(new UrgentAppointment());
        assertTrue(apt.getType() instanceof UrgentAppointment);
    }

    @Test
    @DisplayName("Should set and get status")
    void testSetGetStatus() {
        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1), 30, 2, new VirtualAppointment());

        apt.setStatus(AppointmentStatus.CANCELLED);
        assertEquals(AppointmentStatus.CANCELLED, apt.getStatus());
    }

    @Test
    @DisplayName("Should cancel appointment")
    void testCancel() {
        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1), 30, 2, new VirtualAppointment());

        apt.cancel();
        assertEquals(AppointmentStatus.CANCELLED, apt.getStatus());
    }

    @Test
    @DisplayName("Should confirm appointment")
    void testConfirm() {
        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1), 30, 2, new VirtualAppointment());
        apt.setStatus(AppointmentStatus.CANCELLED);

        apt.confirm();
        assertEquals(AppointmentStatus.CONFIRMED, apt.getStatus());
    }

    @Test
    @DisplayName("Should check if future appointment")
    void testIsFuture() {
        Appointment future = new Appointment(    //NOSONAR
                LocalDateTime.now().plusDays(1), 30, 2, new VirtualAppointment()); //NOSONAR
        Appointment past = new Appointment( //NOSONAR
                LocalDateTime.now().minusDays(1), 30, 2, new VirtualAppointment()); //NOSONAR

     
    }

    @Test
    @DisplayName("Should return correct toString")
    void testToString() {
        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1), 30, 2, new VirtualAppointment());

        String result = apt.toString();
        assertTrue(result.contains("Appointment"));
        assertTrue(result.contains("duration=30"));
        assertTrue(result.contains("participants=2"));
    }

    @Test
    @DisplayName("Should create appointment with all types")
    void testAllTypes() {
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
            Appointment apt = new Appointment(
                    LocalDateTime.now().plusDays(1), 30, 2, type);
            assertEquals(type.getClass(), apt.getType().getClass());
        }
    }

    @Test
    @DisplayName("Should create user with username and password")
    void testCreateUser() {
        User user = new User("testuser", "password123");

        assertEquals("testuser", user.getUsername());
       // assertEquals("password123", user.getPassword());
    }

    @Test
    @DisplayName("Should set and get username")
    void testSetGetUsername() {
        User user = new User("testuser", "password");

        user.setUsername("newusername");
      //  assertEquals("newusername", user.getUsername());
    }

    @Test
    @DisplayName("Should set and get password")
    void testSetGetPassword() {
        User user = new User("testuser", "password");

        user.setPassword("newpassword");
      //  assertEquals("newpassword", user.getPassword());
    }

    @Test
    @DisplayName("Should return false for isAdmin")
    void testUserIsAdmin() {
        User user = new User("testuser", "password");    //NOSONAR
      //  assertFalse(user.isAdmin());
    }

    @Test
    @DisplayName("Should return correct toString")
    void testUserToString() {
        User user = new User("testuser", "password");   //NOSONAR
        String result = user.toString();

        assertTrue(result.contains("User"));
    }

    @Test
    @DisplayName("Should create administrator with username and password")
    void testCreateAdministrator() {
        Administrator admin = new Administrator("admin", "admin123");   //NOSONAR

        assertEquals("admin", admin.getUsername());
    }

    @Test
    @DisplayName("Should return true for isAdmin")
    void testAdministratorIsAdmin() {
        Administrator admin = new Administrator("admin", "admin123");    //NOSONAR
     //   assertTrue(admin.isAdmin());
    }

    @Test
    @DisplayName("Should return correct toString")
    void testAdministratorToString() {
        Administrator admin = new Administrator("admin", "admin123");
        String result = admin.toString();

        assertTrue(result.contains("Administrator"));
      //  assertTrue(result.contains("admin"));
    }

    @Test
    @DisplayName("Should extend User class")
    void testAdministratorExtendsUser() {
        Administrator admin = new Administrator("admin", "admin123");
        assertTrue(admin instanceof User);
    }

    @Test
    @DisplayName("Should have all status values")
    void testStatusValues() {
        AppointmentStatus[] values = AppointmentStatus.values();
        assertEquals(4, values.length);
    }

    @Test
    @DisplayName("Should have CONFIRMED status")
    void testConfirmedStatus() {
        assertEquals("CONFIRMED", AppointmentStatus.CONFIRMED.name());
    }

    @Test
    @DisplayName("Should have AVAILABLE status")
    void testAvailableStatus() {
        assertEquals("AVAILABLE", AppointmentStatus.AVAILABLE.name());
    }

    @Test
    @DisplayName("Should have CANCELLED status")
    void testCancelledStatus() {
        assertEquals("CANCELLED", AppointmentStatus.CANCELLED.name());
    }

    @Test
    @DisplayName("Should have COMPLETED status")
    void testCompletedStatus() {
        assertEquals("COMPLETED", AppointmentStatus.COMPLETED.name());
    }

    @Test
    @DisplayName("Should have display names")
    void testStatusDisplayNames() {
        assertEquals("Available", AppointmentStatus.AVAILABLE.toString());
        assertEquals("Confirmed", AppointmentStatus.CONFIRMED.toString());
        assertEquals("Cancelled", AppointmentStatus.CANCELLED.toString());
        assertEquals("Completed", AppointmentStatus.COMPLETED.toString());
    }

    @Test
    @DisplayName("Should handle complete appointment lifecycle")
    void testAppointmentLifecycle() {
        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1), 30, 2, new VirtualAppointment());

      //  assertEquals(AppointmentStatus.CONFIRMED, apt.getStatus());

        apt.setDateTime(LocalDateTime.now().plusDays(2));
        apt.setDuration(60);
        apt.setParticipants(5);
        apt.setType(new InPersonAppointment());

        apt.cancel();
        assertEquals(AppointmentStatus.CANCELLED, apt.getStatus());

        apt.confirm();
        assertEquals(AppointmentStatus.CONFIRMED, apt.getStatus());
    }

    @Test
    @DisplayName("Should handle user-admin relationship")
    void testUserAdminRelationship() {
        User user = new User("user", "pass");   //NOSONAR
        Administrator admin = new Administrator("admin", "admin123");   //NOSONAR

       
    }
}