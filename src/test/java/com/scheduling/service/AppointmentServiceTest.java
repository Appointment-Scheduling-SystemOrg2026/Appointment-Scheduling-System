package com.scheduling.service;

import com.scheduling.domain.entity.Appointment;
import com.scheduling.domain.entity.AppointmentStatus;
import com.scheduling.repository.AppointmentRepository;
import com.scheduling.strategy.BookingRuleStrategy;
import com.scheduling.strategy.DurationRuleStrategy;
import com.scheduling.strategy.ParticipantLimitStrategy;
import com.scheduling.domain.type.InPersonAppointment;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests booking service.
 */
class AppointmentServiceTest {
    // ================= HELPER RULE =================
	private BookingRuleStrategy alwaysValidRule() {
	    return new BookingRuleStrategy() {

	        @Override
	        public boolean isValid(Appointment appointment) {
	            return true;
	        }

	        @Override
	        public String getDescription() {
	            return "test rule";
	        }
	    };
	}

    // ================= BOOK SUCCESS =================
    @Test
    void shouldBookSuccessfully() {

        AppointmentRepository repo = new AppointmentRepository();

        AppointmentService service =
                new AppointmentService(
                        repo,
                        List.of(
                                new DurationRuleStrategy(),
                                new ParticipantLimitStrategy()
                        )
                );

        Appointment appointment =
                new Appointment(
                        LocalDateTime.now().plusDays(1),
                        60,
                        2,
                        new InPersonAppointment()
                );

        assertTrue(service.book(appointment));
        assertEquals(AppointmentStatus.CONFIRMED, appointment.getStatus());
        assertEquals(1, repo.count());
    }

    // ================= BOOK FAIL =================
    @Test
    void shouldRejectLongDuration() {

        AppointmentRepository repo = new AppointmentRepository();

        AppointmentService service =
                new AppointmentService(
                        repo,
                        List.of(
                                new DurationRuleStrategy(),
                                new ParticipantLimitStrategy()
                        )
                );

        Appointment appointment =
                new Appointment(
                        LocalDateTime.now().plusDays(1),
                        200,
                        2,
                        new InPersonAppointment()
                );

        assertFalse(service.book(appointment));
    }

    // ================= AVAILABLE SLOTS =================
    @Test
    void shouldReturnAvailableSlotsOnly() {

        AppointmentRepository repo = new AppointmentRepository();

        AppointmentService service =
                new AppointmentService(repo, List.of());

        Appointment appointment =
                new Appointment(
                        LocalDateTime.now().plusDays(1),
                        30,
                        1,
                        new InPersonAppointment()
                );

        repo.save(appointment);

        assertEquals(1, service.viewAvailableSlots().size());
    }

    // ================= MODIFY SUCCESS =================
    @Test
    void shouldModifyAppointmentSuccessfully() {

        AppointmentRepository repo = new AppointmentRepository();

        AppointmentService service =
                new AppointmentService(repo, List.of(alwaysValidRule()));

        Appointment oldApt = new Appointment(
                LocalDateTime.now().plusDays(2),
                30,
                1,
                new InPersonAppointment()
        );

        Appointment updated = new Appointment(
                LocalDateTime.now().plusDays(3),
                90,
                3,
                new InPersonAppointment()
        );

        assertTrue(service.modifyAppointment(oldApt, updated));
        assertEquals(90, oldApt.getDuration());
        assertEquals(3, oldApt.getParticipants());
    }

    // ================= MODIFY FAIL (PAST) =================
    @Test
    void shouldFailModifyPastAppointment() {

        AppointmentRepository repo = new AppointmentRepository();

        AppointmentService service =
                new AppointmentService(repo, List.of(alwaysValidRule()));

        Appointment oldApt = new Appointment(
                LocalDateTime.now().minusDays(2),
                30,
                1,
                new InPersonAppointment()
        );

        Appointment updated = new Appointment(
                LocalDateTime.now().plusDays(1),
                60,
                2,
                new InPersonAppointment()
        );

        assertFalse(service.modifyAppointment(oldApt, updated));
    }

    // ================= CANCEL SUCCESS =================
    @Test
    void shouldCancelAppointmentSuccessfully() {

        AppointmentRepository repo = new AppointmentRepository();

        AppointmentService service =
                new AppointmentService(repo, List.of(alwaysValidRule()));

        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1),
                30,
                1,
                new InPersonAppointment()
        );

        repo.save(apt);

        assertTrue(service.cancelAppointment(apt));
        assertEquals(AppointmentStatus.CANCELLED, apt.getStatus());
    }

    // ================= CANCEL FAIL (PAST) =================
    @Test
    void shouldFailCancelPastAppointment() {

        AppointmentRepository repo = new AppointmentRepository();

        AppointmentService service =
                new AppointmentService(repo, List.of(alwaysValidRule()));

        Appointment apt = new Appointment(
                LocalDateTime.now().minusDays(1),
                30,
                1,
                new InPersonAppointment()
        );

        assertFalse(service.cancelAppointment(apt));
    }

    // ================= FIND + DELETE =================
    @Test
    void shouldFindByIdAndDeleteAppointment() {

        AppointmentRepository repo = new AppointmentRepository();

        AppointmentService service =
                new AppointmentService(repo, List.of(alwaysValidRule()));

        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1),
                30,
                1,
                new InPersonAppointment()
        );

        repo.save(apt);

        assertNotNull(service.findById(apt.getId()));
        assertTrue(service.deleteAppointment(apt));
    }

    // ================= ALL APPOINTMENTS =================
    @Test
    void shouldReturnAllAppointments() {

        AppointmentRepository repo = new AppointmentRepository();

        AppointmentService service =
                new AppointmentService(repo, List.of(alwaysValidRule()));

        repo.save(new Appointment(
                LocalDateTime.now().plusDays(1),
                30,
                1,
                new InPersonAppointment()
        ));

        assertEquals(1, service.findAllAppointments().size());
    }

    // ================= NULL DELETE =================
    @Test
    void shouldFailDeleteWhenNull() {

        AppointmentRepository repo = new AppointmentRepository();

        AppointmentService service =
                new AppointmentService(repo, List.of(alwaysValidRule()));

        assertFalse(service.deleteAppointment(null));
    }
}