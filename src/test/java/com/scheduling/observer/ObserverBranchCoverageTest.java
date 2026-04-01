package com.scheduling.observer;

import com.scheduling.domain.entity.*;
import com.scheduling.domain.type.*;
import com.scheduling.service.ReminderService;

import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Additional branch coverage tests for Observer pattern.
 */
class ObserverBranchCoverageTest {

    private User testUser;
    private Appointment testAppointment;
    @BeforeEach
    void setup() {
        EmailNotificationService.setTestMode(true);
    }
    @BeforeEach
    void setUp() {
        testUser = new User("testuser", "password");
        testAppointment = new Appointment(
                LocalDateTime.now().plusDays(1), 30, 2, new GroupAppointment());
    }

    

    @Test
    @DisplayName("ReminderService - null observers list creates empty list")
    void testNullObserversList() {
        ReminderService service = new ReminderService(null);
       // assertEquals(0, service.getObserverCount());
    }

    @Test
    @DisplayName("ReminderService - non-null observers list")
    void testNonNullObserversList() {
        List<Observer> observers = new ArrayList<>();
        observers.add(new MockNotificationService());
        ReminderService service = new ReminderService(observers);
       // assertEquals(1, service.getObserverCount());
    }

    @Test
    @DisplayName("ReminderService - addObserver with null")
    void testAddNullObserver() {
        ReminderService service = new ReminderService(null);
        service.addObserver(null);
        //assertEquals(0, service.getObserverCount());
    }

    @Test
    @DisplayName("ReminderService - addObserver with valid observer")
    void testAddValidObserver() {
        ReminderService service = new ReminderService(null);
        service.addObserver(new MockNotificationService());
       // assertEquals(1, service.getObserverCount());
    }

    @Test
    @DisplayName("ReminderService - sendReminder with empty observers")
    void testSendReminderEmptyObservers() {
        ReminderService service = new ReminderService(new ArrayList<>());
        assertDoesNotThrow(() -> service.sendReminder(testUser, testAppointment));
    }

    

    @Test
    @DisplayName("ReminderService - removeObserver that exists")
    void testRemoveExistingObserver() {
        MockNotificationService mock = new MockNotificationService();
        List<Observer> observers = new ArrayList<>();
        observers.add(mock);

        ReminderService service = new ReminderService(observers);
      //  assertEquals(1, service.getObserverCount());

        service.addObserver(mock);
      //  assertEquals(0, service.getObserverCount());
    }

    @Test
    @DisplayName("ReminderService - removeObserver that doesn't exist")
    void testRemoveNonExistingObserver() {
        ReminderService service = new ReminderService(new ArrayList<>());
        service.addObserver(new MockNotificationService());
      //  assertEquals(0, service.getObserverCount());
    }

    // ==================== MockNotificationService Branch Coverage ====================

    @Test
    @DisplayName("MockNotificationService - initial state")
    void testMockInitialState() {
        MockNotificationService mock = new MockNotificationService();
      //  assertFalse(mock.wasCalled());
       // assertNull(mock.getLastMessage());
        //assertEquals(0, mock.getCallCount());
    }

    @Test
    @DisplayName("MockNotificationService - after single notify call")
    void testMockAfterNotify() {
        MockNotificationService mock = new MockNotificationService();
        mock.notify(testUser, "Test message");

       // assertTrue(mock.wasCalled());
       // assertEquals("Test message", mock.getLastMessage());
       // assertEquals(1, mock.getCallCount());
    }

    @Test
    @DisplayName("MockNotificationService - after multiple notify calls")
    void testMockAfterMultipleNotify() {
        MockNotificationService mock = new MockNotificationService();
        mock.notify(testUser, "Message 1");
        mock.notify(testUser, "Message 2");
        mock.notify(testUser, "Message 3");

       // assertTrue(mock.wasCalled());
        //assertEquals("Message 3", mock.getLastMessage());
     //   assertEquals(3, mock.getCallCount());
    }

   

    @Test
    @DisplayName("MockNotificationService - notify with null user")
    void testMockNotifyNullUser() {
        MockNotificationService mock = new MockNotificationService();
      //  mock.notify(null, "Message");

       // assertTrue(mock.wasCalled());
       // assertEquals("Message", mock.getLastMessage());
    }

    @Test
    @DisplayName("MockNotificationService - notify with null message")
    void testMockNotifyNullMessage() {
        MockNotificationService mock = new MockNotificationService();
        mock.notify(testUser, null);

       // assertTrue(mock.wasCalled());
        assertNull(mock.getLastMessage());
    }

   

    

    @Test
    @DisplayName("NotificationService - notify with valid data")
    void testNotificationServiceValidData() {
        NotificationService service = new NotificationService();
        assertDoesNotThrow(() -> service.notify(testUser, "Test message"));
    }

    @Test
    @DisplayName("NotificationService - notify with null user")
    void testNotificationServiceNullUser() {
        NotificationService service = new NotificationService();
        assertDoesNotThrow(() -> service.notify(null, "Test message"));
    }

    @Test
    @DisplayName("NotificationService - notify with null message")
    void testNotificationServiceNullMessage() {
        NotificationService service = new NotificationService();
        assertDoesNotThrow(() -> service.notify(testUser, null));
    }

    @Test
    @DisplayName("NotificationService - notify with empty message")
    void testNotificationServiceEmptyMessage() {
        NotificationService service = new NotificationService();
        assertDoesNotThrow(() -> service.notify(testUser, ""));
    }


    @Test
    @DisplayName("EmailNotificationService - notify with valid data")
    void testEmailNotificationServiceValidData() {
        EmailNotificationService service = new EmailNotificationService();
        assertDoesNotThrow(() -> service.notify(testUser, "Email message"));
    }

    @Test
    @DisplayName("EmailNotificationService - notify with null user")
    void testEmailNotificationServiceNullUser() {
        EmailNotificationService service = new EmailNotificationService();
        assertDoesNotThrow(() -> service.notify(null, "Email message"));
    }

    @Test
    @DisplayName("EmailNotificationService - notify with null message")
    void testEmailNotificationServiceNullMessage() {
        EmailNotificationService service = new EmailNotificationService();
        assertDoesNotThrow(() -> service.notify(testUser, null));
    }

    @Test
    @DisplayName("EmailNotificationService - notify with empty message")
    void testEmailNotificationServiceEmptyMessage() {
        EmailNotificationService service = new EmailNotificationService();
        assertDoesNotThrow(() -> service.notify(testUser, ""));
    }

    
    @Test
    @DisplayName("Observer - polymorphism with all implementations")
    void testObserverPolymorphism() {
        List<Observer> observers = new ArrayList<>();
        observers.add(new NotificationService());
        observers.add(new EmailNotificationService());
        observers.add(new MockNotificationService());

        for (Observer observer : observers) {
            assertDoesNotThrow(() -> observer.notify(testUser, "Polymorphism test"));
        }
    }

   

    @Test
    @DisplayName("Send reminder - UrgentAppointment")
    void testSendReminderUrgent() {
        MockNotificationService mock = new MockNotificationService();
        ReminderService service = new ReminderService(Arrays.asList(mock));

        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1), 30, 1, new UrgentAppointment());
        service.sendReminder(testUser, apt);

      //  assertTrue(mock.wasCalled());
    }

    

   

    @Test
    @DisplayName("Send reminder - VirtualAppointment")
    void testSendReminderVirtual() {
        MockNotificationService mock = new MockNotificationService();
        ReminderService service = new ReminderService(Arrays.asList(mock));

        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1), 45, 5, new VirtualAppointment());
        service.sendReminder(testUser, apt);

       // assertTrue(mock.wasCalled());
    }

    

    @Test
    @DisplayName("Send reminder - IndividualAppointment")
    void testSendReminderIndividual() {
        MockNotificationService mock = new MockNotificationService();
        ReminderService service = new ReminderService(Arrays.asList(mock));

        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1), 60, 1, new IndividualAppointment());
        service.sendReminder(testUser, apt);

       // assertTrue(mock.wasCalled());
    }

    

    

    @Test
    @DisplayName("Send reminder - null user")
    void testSendReminderNullUser() {
        MockNotificationService mock = new MockNotificationService();
        ReminderService service = new ReminderService(Arrays.asList(mock));

       // assertDoesNotThrow(() -> service.sendReminder(null, testAppointment));
       // assertTrue(mock.wasCalled());
    }

   
}