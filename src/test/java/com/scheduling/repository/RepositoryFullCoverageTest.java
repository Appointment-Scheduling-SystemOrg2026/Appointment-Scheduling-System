package com.scheduling.repository;

import com.scheduling.domain.entity.*;
import com.scheduling.domain.type.*;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Full coverage tests for AppointmentRepository class.
 */
class RepositoryFullCoverageTest {

    private AppointmentRepository repository;
    private Appointment testAppointment1;
    private Appointment testAppointment2;
    private Appointment testAppointment3;

    @BeforeEach
    void setUp() {
        repository = new AppointmentRepository();

        testAppointment1 = new Appointment(
                LocalDateTime.now().plusDays(1), 30, 2, new VirtualAppointment());
        testAppointment2 = new Appointment(
                LocalDateTime.now().plusDays(2), 45, 3, new InPersonAppointment());
        testAppointment3 = new Appointment(
                LocalDateTime.now().plusDays(3), 60, 5, new GroupAppointment());
    }

    @Test
    @DisplayName("Should save single appointment")
    void testSaveSingle() {
        repository.save(testAppointment1);
        assertEquals(1, repository.count());
    }

    @Test
    @DisplayName("Should save multiple appointments")
    void testSaveMultiple() {
        repository.save(testAppointment1);
        repository.save(testAppointment2);
        repository.save(testAppointment3);
        assertEquals(3, repository.count());
    }

    @Test
    @DisplayName("Should save appointments with different types")
    void testSaveDifferentTypes() {
        AppointmentType[] types = {
                new UrgentAppointment(),
                new FollowUpAppointment(),
                new AssessmentAppointment(),
                new VirtualAppointment(),
                new InPersonAppointment(),
                new IndividualAppointment(),
                new GroupAppointment()
        };

        for (int i = 0; i < types.length; i++) {
            repository.save(new Appointment(
                    LocalDateTime.now().plusDays(i + 1), 30, 1, types[i]));
        }

        assertEquals(7, repository.count());
    }

    @Test
    @DisplayName("Should find all appointments when empty")
    void testFindAllEmpty() {
        List<Appointment> result = repository.findAll();
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should find all appointments")
    void testFindAll() {
        repository.save(testAppointment1);
        repository.save(testAppointment2);
        repository.save(testAppointment3);

        List<Appointment> result = repository.findAll();
        assertEquals(3, result.size());
    }

    @Test
    @DisplayName("FindAll should return a copy")
    void testFindAllReturnsCopy() {
        repository.save(testAppointment1);
        List<Appointment> result1 = repository.findAll();
        List<Appointment> result2 = repository.findAll();

        assertNotSame(result1, result2);
    }

    @Test
    @DisplayName("Should find available appointments when empty")
    void testFindAvailableEmpty() {
        List<Appointment> result = repository.findAvailable();
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should find available appointments")
    void testFindAvailable() {
        testAppointment1.setStatus(AppointmentStatus.CONFIRMED);
        testAppointment2.setStatus(AppointmentStatus.AVAILABLE);
        testAppointment3.setStatus(AppointmentStatus.CANCELLED);

        repository.save(testAppointment1);
        repository.save(testAppointment2);
        repository.save(testAppointment3);

        List<Appointment> result = repository.findAvailable();
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Should find only confirmed appointments")
    void testFindAvailableConfirmedOnly() {
        testAppointment1.setStatus(AppointmentStatus.CONFIRMED);
        testAppointment2.setStatus(AppointmentStatus.CONFIRMED);

        repository.save(testAppointment1);
        repository.save(testAppointment2);

        List<Appointment> result = repository.findAvailable();
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Should not find cancelled appointments")
    void testFindAvailableNoCancelled() {
        testAppointment1.setStatus(AppointmentStatus.CANCELLED);
        repository.save(testAppointment1);

        List<Appointment> result = repository.findAvailable();
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should find appointment by ID")
    void testFindById() {
        repository.save(testAppointment1);
        String id = testAppointment1.getId();

        Appointment result = repository.findById(id);
        assertNotNull(result);
        assertEquals(testAppointment1, result);
    }

    @Test
    @DisplayName("Should return null for non-existent ID")
    void testFindByIdNotFound() {
        Appointment result = repository.findById("non-existent-id");
        assertNull(result);
    }

    @Test
    @DisplayName("Should find correct appointment among many")
    void testFindByIdAmongMany() {
        repository.save(testAppointment1);
        repository.save(testAppointment2);
        repository.save(testAppointment3);

        String targetId = testAppointment2.getId();
        Appointment result = repository.findById(targetId);

        assertNotNull(result);
        assertEquals(testAppointment2, result);
    }

    @Test
    @DisplayName("Should delete appointment")
    void testDelete() {
        repository.save(testAppointment1);
        assertEquals(1, repository.count());

        boolean result = repository.delete(testAppointment1);
        assertTrue(result);
        assertEquals(0, repository.count());
    }

    @Test
    @DisplayName("Should return false when deleting non-existent appointment")
    void testDeleteNonExistent() {
        boolean result = repository.delete(testAppointment1);
        assertFalse(result);
    }

    @Test
    @DisplayName("Should delete correct appointment")
    void testDeleteCorrectAppointment() {
        repository.save(testAppointment1);
        repository.save(testAppointment2);
        repository.save(testAppointment3);

        boolean result = repository.delete(testAppointment2);
        assertTrue(result);
        assertEquals(2, repository.count());

        List<Appointment> remaining = repository.findAll();
        assertTrue(remaining.contains(testAppointment1));
        assertFalse(remaining.contains(testAppointment2));
        assertTrue(remaining.contains(testAppointment3));
    }

    @Test
    @DisplayName("Should handle delete on empty repository")
    void testDeleteEmpty() {
        boolean result = repository.delete(testAppointment1);
        assertFalse(result);
    }

    @Test
    @DisplayName("Should update appointment")
    void testUpdate() {
        repository.save(testAppointment1);

        testAppointment1.setDuration(90);
        repository.update(testAppointment1);

        assertEquals(90, testAppointment1.getDuration());
    }

    @Test
    @DisplayName("Should update appointment status")
    void testUpdateStatus() {
        repository.save(testAppointment1);

        testAppointment1.setStatus(AppointmentStatus.CANCELLED);
        repository.update(testAppointment1);

        assertEquals(AppointmentStatus.CANCELLED, testAppointment1.getStatus());
    }

    @Test
    @DisplayName("Should update appointment type")
    void testUpdateType() {
        repository.save(testAppointment1);

        testAppointment1.setType(new UrgentAppointment());
        repository.update(testAppointment1);

        assertTrue(testAppointment1.getType() instanceof UrgentAppointment);
    }

    @Test
    @DisplayName("Should clear all appointments")
    void testClear() {
        repository.save(testAppointment1);
        repository.save(testAppointment2);
        repository.save(testAppointment3);
        assertEquals(3, repository.count());

        repository.clear();
        assertEquals(0, repository.count());
    }

    @Test
    @DisplayName("Should clear empty repository")
    void testClearEmpty() {
        repository.clear();
        assertEquals(0, repository.count());
    }

    @Test
    @DisplayName("Should be able to add after clear")
    void testAddAfterClear() {
        repository.save(testAppointment1);
        repository.clear();
        repository.save(testAppointment2);

        assertEquals(1, repository.count());
    }

    @Test
    @DisplayName("Should count empty repository")
    void testCountEmpty() {
        assertEquals(0, repository.count());
    }

    @Test
    @DisplayName("Should count correctly after operations")
    void testCountAfterOperations() {
        assertEquals(0, repository.count());

        repository.save(testAppointment1);
        assertEquals(1, repository.count());

        repository.save(testAppointment2);
        assertEquals(2, repository.count());

        repository.delete(testAppointment1);
        assertEquals(1, repository.count());

        repository.clear();
        assertEquals(0, repository.count());
    }

    @Test
    @DisplayName("Should handle complete lifecycle")
    void testCompleteLifecycle() {
        repository.save(testAppointment1);
        assertEquals(1, repository.count());

        Appointment found = repository.findById(testAppointment1.getId());
        assertNotNull(found);

        testAppointment1.setDuration(120);
        repository.update(testAppointment1);

        boolean deleted = repository.delete(testAppointment1);
        assertTrue(deleted);
        assertEquals(0, repository.count());
    }

    @Test
    @DisplayName("Should handle bulk operations")
    void testBulkOperations() {
        for (int i = 0; i < 10; i++) {
            repository.save(new Appointment(
                    LocalDateTime.now().plusDays(i + 1), 30, 1, new VirtualAppointment()));
        }
        assertEquals(10, repository.count());

        List<Appointment> all = repository.findAll();
        assertEquals(10, all.size());

        repository.clear();
        assertEquals(0, repository.count());
    }

    @Test
    @DisplayName("Should find appointments by different statuses")
    void testFindByStatus() {
        Appointment confirmed = new Appointment(
                LocalDateTime.now().plusDays(1), 30, 1, new VirtualAppointment());
        confirmed.setStatus(AppointmentStatus.CONFIRMED);

        Appointment available = new Appointment(
                LocalDateTime.now().plusDays(2), 30, 1, new VirtualAppointment());
        available.setStatus(AppointmentStatus.AVAILABLE);

        Appointment cancelled = new Appointment(
                LocalDateTime.now().plusDays(3), 30, 1, new VirtualAppointment());
        cancelled.setStatus(AppointmentStatus.CANCELLED);

        repository.save(confirmed);
        repository.save(available);
        repository.save(cancelled);

        List<Appointment> result = repository.findAvailable();
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Should handle appointment with max duration")
    void testMaxDurationAppointment() {
        Appointment longAppointment = new Appointment(
                LocalDateTime.now().plusDays(1), 120, 1, new GroupAppointment());
        repository.save(longAppointment);

        assertEquals(1, repository.count());
    }

    @Test
    @DisplayName("Should handle appointment with max participants")
    void testMaxParticipantsAppointment() {
        Appointment largeAppointment = new Appointment(
                LocalDateTime.now().plusDays(1), 60, 10, new GroupAppointment());
        repository.save(largeAppointment);

        assertEquals(1, repository.count());
    }

    @Test
    @DisplayName("Should handle past appointments")
    void testPastAppointment() {
        Appointment pastAppointment = new Appointment(
                LocalDateTime.now().minusDays(1), 30, 1, new VirtualAppointment());
        repository.save(pastAppointment);

        assertEquals(1, repository.count());
    }

    @Test
    @DisplayName("Should handle appointment with all types")
    void testAllAppointmentTypes() {
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
                    LocalDateTime.now().plusDays(1), 30, 1, type);
            repository.save(apt);
        }

        assertEquals(7, repository.count());
    }

    @Test
    @DisplayName("Should handle completed status")
    void testCompletedStatus() {
        Appointment completed = new Appointment(
                LocalDateTime.now().plusDays(1), 30, 1, new VirtualAppointment());
        completed.setStatus(AppointmentStatus.COMPLETED);
        repository.save(completed);

        List<Appointment> available = repository.findAvailable();
        assertEquals(0, available.size());
    }
}