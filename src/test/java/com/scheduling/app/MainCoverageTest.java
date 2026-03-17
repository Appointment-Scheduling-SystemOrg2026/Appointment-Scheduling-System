package com.scheduling.app;

import com.scheduling.domain.entity.*;
import com.scheduling.domain.type.*;
import com.scheduling.domain.valueobject.NotificationMessage;
import com.scheduling.repository.AppointmentRepository;
import com.scheduling.service.*;
import com.scheduling.strategy.*;
import com.scheduling.observer.*;
import com.scheduling.observer.Observer;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class MainCoverageTest {

    @Test
    void testAppointmentCreation() {
        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1),
                30, 1, new InPersonAppointment());
        
        assertNotNull(apt.getId());
        assertNotNull(apt.getDateTime());
        assertEquals(30, apt.getDuration());
        assertEquals(1, apt.getParticipants());
        assertEquals(AppointmentStatus.AVAILABLE, apt.getStatus());
    }

    @Test
    void testAppointmentConfirm() {
        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1),
                30, 1, new InPersonAppointment());
        apt.confirm();
        assertEquals(AppointmentStatus.CONFIRMED, apt.getStatus());
    }

    @Test
    void testAppointmentCancel() {
        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1),
                30, 1, new InPersonAppointment());
        apt.cancel();
        assertEquals(AppointmentStatus.CANCELLED, apt.getStatus());
    }

    @Test
    void testAppointmentReschedule() {
        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1),
                30, 1, new InPersonAppointment());
        LocalDateTime newDate = LocalDateTime.now().plusDays(5);
        apt.reschedule(newDate);
        assertEquals(newDate, apt.getDateTime());
    }

    @Test
    void testAppointmentSetters() {
        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1),
                30, 1, new InPersonAppointment());
        
        apt.setDateTime(LocalDateTime.now().plusDays(3));
        apt.setDuration(60);
        apt.setParticipants(3);
        apt.setType(new VirtualAppointment());
        apt.setStatus(AppointmentStatus.CONFIRMED);
        
        assertEquals(60, apt.getDuration());
        assertEquals(3, apt.getParticipants());
        assertTrue(apt.getType() instanceof VirtualAppointment);
        assertEquals(AppointmentStatus.CONFIRMED, apt.getStatus());
    }

    @Test
    void testAppointmentToString() {
        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1),
                30, 1, new InPersonAppointment());
        assertNotNull(apt.toString());
    }

    @Test
    void testAppointmentGetType() {
        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1),
                30, 1, new UrgentAppointment());
        assertTrue(apt.getType() instanceof UrgentAppointment);
    }

    @Test
    void testRepositorySave() {
        AppointmentRepository repo = new AppointmentRepository();
        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1), 30, 1, new InPersonAppointment());
        repo.save(apt);
        assertEquals(1, repo.findAll().size());
    }

    @Test
    void testRepositoryFindAll() {
        AppointmentRepository repo = new AppointmentRepository();
        repo.save(new Appointment(LocalDateTime.now().plusDays(1), 30, 1, new InPersonAppointment()));
        repo.save(new Appointment(LocalDateTime.now().plusDays(2), 45, 2, new VirtualAppointment()));
        assertEquals(2, repo.findAll().size());
    }

    @Test
    void testRepositoryFindById() {
        AppointmentRepository repo = new AppointmentRepository();
        Appointment apt = new Appointment(LocalDateTime.now().plusDays(1), 30, 1, new InPersonAppointment());
        repo.save(apt);
        assertNotNull(repo.findById(apt.getId()));
    }

    @Test
    void testRepositoryFindByIdNotFound() {
        AppointmentRepository repo = new AppointmentRepository();
        assertNull(repo.findById("nonexistent"));
    }

    @Test
    void testRepositoryDelete() {
        AppointmentRepository repo = new AppointmentRepository();
        Appointment apt = new Appointment(LocalDateTime.now().plusDays(1), 30, 1, new InPersonAppointment());
        repo.save(apt);
        assertTrue(repo.delete(apt));
    }

    @Test
    void testRepositoryClear() {
        AppointmentRepository repo = new AppointmentRepository();
        repo.save(new Appointment(LocalDateTime.now().plusDays(1), 30, 1, new InPersonAppointment()));
        repo.clear();
        assertEquals(0, repo.findAll().size());
    }

    @Test
    void testRepositoryCount() {
        AppointmentRepository repo = new AppointmentRepository();
        assertEquals(0, repo.count());
        repo.save(new Appointment(LocalDateTime.now().plusDays(1), 30, 1, new InPersonAppointment()));
        assertEquals(1, repo.count());
    }

    @Test
    void testRepositoryFindAvailable() {
        AppointmentRepository repo = new AppointmentRepository();
        Appointment apt1 = new Appointment(LocalDateTime.now().plusDays(1), 30, 1, new InPersonAppointment());
        Appointment apt2 = new Appointment(LocalDateTime.now().plusDays(2), 45, 2, new VirtualAppointment());
        apt2.confirm();
        
        repo.save(apt1);
        repo.save(apt2);
        
        assertEquals(2, repo.findAvailable().size());
    }

    @Test
    void testDurationRuleStrategyValid() {
        DurationRuleStrategy strategy = new DurationRuleStrategy(120);
        Appointment apt = new Appointment(LocalDateTime.now().plusDays(1), 60, 1, new InPersonAppointment());
        assertTrue(strategy.isValid(apt));
    }

    @Test
    void testDurationRuleStrategyExceedsMax() {
        DurationRuleStrategy strategy = new DurationRuleStrategy(120);
        Appointment apt = new Appointment(LocalDateTime.now().plusDays(1), 200, 1, new InPersonAppointment());
        assertFalse(strategy.isValid(apt));
    }

    @Test
    void testDurationRuleStrategyZero() {
        DurationRuleStrategy strategy = new DurationRuleStrategy(120);
        Appointment apt = new Appointment(LocalDateTime.now().plusDays(1), 0, 1, new InPersonAppointment());
        assertFalse(strategy.isValid(apt));
    }

    @Test
    void testDurationRuleStrategyDescription() {
        DurationRuleStrategy strategy = new DurationRuleStrategy(120);
        assertNotNull(strategy.getDescription());
    }

    @Test
    void testDurationRuleStrategyDefaultConstructor() {
        DurationRuleStrategy strategy = new DurationRuleStrategy();
        assertEquals(120, strategy.getMaxDuration());
    }

    @Test
    void testParticipantLimitStrategyValid() {
        ParticipantLimitStrategy strategy = new ParticipantLimitStrategy(10);
        Appointment apt = new Appointment(LocalDateTime.now().plusDays(1), 60, 5, new GroupAppointment());
        assertTrue(strategy.isValid(apt));
    }

    @Test
    void testParticipantLimitStrategyExceedsMax() {
        ParticipantLimitStrategy strategy = new ParticipantLimitStrategy(10);
        Appointment apt = new Appointment(LocalDateTime.now().plusDays(1), 60, 15, new GroupAppointment());
        assertFalse(strategy.isValid(apt));
    }

    @Test
    void testParticipantLimitStrategyZero() {
        ParticipantLimitStrategy strategy = new ParticipantLimitStrategy(10);
        Appointment apt = new Appointment(LocalDateTime.now().plusDays(1), 60, 0, new InPersonAppointment());
        assertFalse(strategy.isValid(apt));
    }

    @Test
    void testParticipantLimitStrategyDescription() {
        ParticipantLimitStrategy strategy = new ParticipantLimitStrategy(10);
        assertNotNull(strategy.getDescription());
    }

    @Test
    void testParticipantLimitStrategyDefaultConstructor() {
        ParticipantLimitStrategy strategy = new ParticipantLimitStrategy();
        assertEquals(10, strategy.getMaxParticipants());
    }

    @Test
    void testBookingValid() {
        AppointmentRepository repo = new AppointmentRepository();
        List<BookingRuleStrategy> rules = Arrays.asList(
                new DurationRuleStrategy(120),
                new ParticipantLimitStrategy(10));
        AppointmentService service = new AppointmentService(repo, rules);
        
        Appointment apt = new Appointment(LocalDateTime.now().plusDays(1), 60, 5, new GroupAppointment());
        assertTrue(service.book(apt));
        assertEquals(AppointmentStatus.CONFIRMED, apt.getStatus());
    }

    @Test
    void testBookingInvalidDuration() {
        AppointmentRepository repo = new AppointmentRepository();
        List<BookingRuleStrategy> rules = Arrays.asList(
                new DurationRuleStrategy(120),
                new ParticipantLimitStrategy(10));
        AppointmentService service = new AppointmentService(repo, rules);
        
        Appointment apt = new Appointment(LocalDateTime.now().plusDays(1), 200, 5, new GroupAppointment());
        assertFalse(service.book(apt));
    }

    @Test
    void testBookingInvalidParticipants() {
        AppointmentRepository repo = new AppointmentRepository();
        List<BookingRuleStrategy> rules = Arrays.asList(
                new DurationRuleStrategy(120),
                new ParticipantLimitStrategy(10));
        AppointmentService service = new AppointmentService(repo, rules);
        
        Appointment apt = new Appointment(LocalDateTime.now().plusDays(1), 60, 15, new GroupAppointment());
        assertFalse(service.book(apt));
    }

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
    void testAdministrator() {
        Administrator admin = new Administrator("admin", "pass");
        assertEquals("admin", admin.getUsername());
        assertTrue(admin.passwordMatches("pass"));
        assertFalse(admin.passwordMatches("wrong"));
    }

    @Test
    void testUser() {
        User user = new User("user", "pass");
        assertEquals("user", user.getUsername());
        assertTrue(user.passwordMatches("pass"));
        assertFalse(user.passwordMatches("wrong"));
    }

    @Test
    void testAuthenticationLogin() {
        AuthenticationService auth = new AuthenticationService();
        Administrator admin = new Administrator("admin", "admin123");
        assertTrue(auth.login(admin, "admin", "admin123"));
        assertTrue(auth.isLoggedIn());
    }

    @Test
    void testAuthenticationLogout() {
        AuthenticationService auth = new AuthenticationService();
        Administrator admin = new Administrator("admin", "admin123");
        auth.login(admin, "admin", "admin123");
        auth.logout();
        assertFalse(auth.isLoggedIn());
    }

    @Test
    void testAuthenticationWrongPassword() {
        AuthenticationService auth = new AuthenticationService();
        Administrator admin = new Administrator("admin", "admin123");
        assertFalse(auth.login(admin, "admin", "wrong"));
        assertFalse(auth.isLoggedIn());
    }

    @Test
    void testAppointmentServiceViewSlots() {
        AppointmentRepository repo = new AppointmentRepository();
        List<BookingRuleStrategy> rules = Arrays.asList(new DurationRuleStrategy(120), new ParticipantLimitStrategy(10));
        AppointmentService service = new AppointmentService(repo, rules);
        
        repo.save(new Appointment(LocalDateTime.now().plusDays(1), 30, 1, new InPersonAppointment()));
        assertEquals(1, service.viewAvailableSlots().size());
    }

    @Test
    void testAppointmentStatusAvailable() {
        assertEquals("Available", AppointmentStatus.AVAILABLE.toString());
    }

    @Test
    void testAppointmentStatusConfirmed() {
        assertEquals("Confirmed", AppointmentStatus.CONFIRMED.toString());
    }

    @Test
    void testAppointmentStatusCancelled() {
        assertEquals("Cancelled", AppointmentStatus.CANCELLED.toString());
    }

    @Test
    void testAppointmentStatusCompleted() {
        assertEquals("Completed", AppointmentStatus.COMPLETED.toString());
    }

    @Test
    void testAppointmentStatusValues() {
        assertEquals(4, AppointmentStatus.values().length);
    }

    @Test
    void testNotificationService() {
        NotificationService service = new NotificationService();
        User user = new User("test@mail.com", "1234");
        assertDoesNotThrow(() -> service.notify(user, "Test"));
    }

    @Test
    void testEmailNotificationService() {
        EmailNotificationService service = new EmailNotificationService();
        User user = new User("test@mail.com", "1234");
        assertDoesNotThrow(() -> service.notify(user, "Test"));
    }

    @Test
    void testEmailNotificationServiceWithEmail() {
        EmailNotificationService service = new EmailNotificationService();
        assertDoesNotThrow(() -> service.notifyWithEmail("test@mail.com", "Test"));
    }

    @Test
    void testMockNotificationServiceObserver() {
        com.scheduling.observer.MockNotificationService service = new com.scheduling.observer.MockNotificationService();
        User user = new User("test@mail.com", "1234");
        service.notify(user, "Test");
        assertEquals(1, service.getSentCount());
    }

    @Test
    void testMockNotificationServiceReset() {
        com.scheduling.observer.MockNotificationService service = new com.scheduling.observer.MockNotificationService();
        User user = new User("test@mail.com", "1234");
        service.notify(user, "Test");
        service.reset();
        assertEquals(0, service.getSentCount());
    }

    @Test
    void testMockNotificationServiceLog() {
        com.scheduling.observer.MockNotificationService service = new com.scheduling.observer.MockNotificationService();
        User user = new User("test@mail.com", "1234");
        service.notify(user, "Test");
        assertNotNull(service.getLog());
    }

    @Test
    void testNotificationManager() {
        List<Observer> observers = new ArrayList<>();
        observers.add(new NotificationService());
        NotificationManager manager = new NotificationManager(observers);
        
        Appointment apt = new Appointment(LocalDateTime.now().plusDays(1), 30, 1, new InPersonAppointment());
        assertDoesNotThrow(() -> manager.sendReminderToEmail("test@mail.com", apt));
    }

    @Test
    void testNotificationManagerSendReminder() {
        List<Observer> observers = new ArrayList<>();
        observers.add(new NotificationService());
        NotificationManager manager = new NotificationManager(observers);
        
        User user = new User("test@mail.com", "1234");
        Appointment apt = new Appointment(LocalDateTime.now().plusDays(1), 30, 1, new InPersonAppointment());
        assertDoesNotThrow(() -> manager.sendReminder(user, apt));
    }

    @Test
    void testNotificationMessageContent() {
        NotificationMessage msg = new NotificationMessage("Hello");
        assertEquals("Hello", msg.getContent());
        assertEquals("Hello", msg.getMessage());
        assertEquals("unknown", msg.getRecipient());
    }

    @Test
    void testNotificationMessageWithRecipient() {
        NotificationMessage msg = new NotificationMessage("test@mail.com", "Hello");
        assertEquals("test@mail.com", msg.getRecipient());
        assertEquals("Hello", msg.getContent());
    }

    @Test
    void testNotificationMessageTimestamp() {
        NotificationMessage msg = new NotificationMessage("Test");
        assertTrue(msg.getTimestamp() > 0);
    }

    @Test
    void testNotificationMessageToString() {
        NotificationMessage msg = new NotificationMessage("test@mail.com", "Hello");
        assertNotNull(msg.toString());
    }

    @Test
    void testAppointmentServiceCancelFuture() {
        AppointmentRepository repo = new AppointmentRepository();
        List<BookingRuleStrategy> rules = Arrays.asList(new DurationRuleStrategy(120), new ParticipantLimitStrategy(10));
        AppointmentService service = new AppointmentService(repo, rules);
        
        Appointment apt = new Appointment(LocalDateTime.now().plusDays(1), 30, 1, new InPersonAppointment());
        repo.save(apt);
        
        boolean result = service.cancelAppointment(apt);
        assertTrue(result);
        assertEquals(0, repo.findAll().size());
    }

    @Test
    void testTypeSpecificDurationUrgent() {
        DurationRuleStrategy strategy = new DurationRuleStrategy(120);
        // Urgent max is 30 minutes
        Appointment apt = new Appointment(LocalDateTime.now().plusDays(1), 60, 1, new UrgentAppointment());
        assertFalse(strategy.isValid(apt));
    }

    @Test
    void testTypeSpecificParticipantsIndividual() {
        ParticipantLimitStrategy strategy = new ParticipantLimitStrategy(10);
        // Individual max is 1 participant
        Appointment apt = new Appointment(LocalDateTime.now().plusDays(1), 60, 2, new IndividualAppointment());
        assertFalse(strategy.isValid(apt));
    }
}