package com.scheduling.app;

import com.scheduling.domain.entity.User;
import com.scheduling.repository.AppointmentRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private Main mainApp;
    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;
    private InputStream originalSystemIn;
    private PrintStream originalSystemOut;

    @BeforeEach
    void setUp() {
        originalSystemIn = System.in;
        originalSystemOut = System.out;

        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
    }

    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    private Object invokePrivateMethod(String methodName, Class<?>[] paramTypes, Object[] args) throws Exception {
        if (paramTypes == null) paramTypes = new Class<?>[0];
        if (args == null) args = new Object[0];

        Method method = Main.class.getDeclaredMethod(methodName, paramTypes);
        method.setAccessible(true);
        return method.invoke(mainApp, args);
    }

    private void setPrivateField(String fieldName, Object value) throws Exception {
        Field field = Main.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(mainApp, value);
    }
    
    private Object getPrivateField(String fieldName) throws Exception {
        Field field = Main.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(mainApp);
    }

    @Test
    void testStartAndExitImmediately() throws Exception {
        provideInput("5\n");
        mainApp = new Main();
        mainApp.start();
        
        String output = testOut.toString();
        assertTrue(output.contains("APPOINTMENT SCHEDULING SYSTEM"));
        assertTrue(output.contains("Thank you for using our system!"));
    }

    @Test
    void testRunMenuInvalidChoice() throws Exception {
        provideInput("99\n5\n");
        mainApp = new Main();
        mainApp.start();
        assertTrue(testOut.toString().contains("Invalid choice."));
    }

    @Test
    void testAdminLoginSuccess() throws Exception {
        provideInput("admin\nadmin123\n7\n");
        mainApp = new Main();
        
        invokePrivateMethod("adminLoginFlow", null, null);
        
        String output = testOut.toString();
        assertTrue(output.contains("Login successful"));
        assertTrue(output.contains("Logged out."));
    }
    
    @ParameterizedTest
    @CsvSource({
        "wrong\\nwrong\\n, adminLoginFlow, Invalid credentials",
        "TestUser\\n4\\n1\\nn\\n6\\n, userModeFlow, You have no bookings to cancel",
        "TestUser\\n3\\n99\\n6\\n, userModeFlow, No modifiable appointments found",
        "\\n\\n, runSystemDemo, DEMO COMPLETED SUCCESSFULLY"
    })
    void testSimpleFlows(String input, String methodName, String expectedOutput) throws Exception {
        String processedInput = input.replace("\\n", "\n");
        provideInput(processedInput);
        mainApp = new Main();
        invokePrivateMethod(methodName, null, null);
        assertTrue(testOut.toString().contains(expectedOutput));
    }

    @Test
    void testAdminFullCycle() throws Exception {
        String input = "admin\nadmin123\n" +
                       "1\n" + // Add Slot
                       "5\n2027\n05\n05\n10\n0\n30\n1\n" + // Type 5, Date, Dur, Part
                       "4\n1\n2028\n06\n06\n11\n0\n30\n1\n5\n" + // Modify appt 1
                       "6\n" + // Statistics
                       "7\n"; // Logout
        
        provideInput(input);
        mainApp = new Main();
        invokePrivateMethod("adminLoginFlow", null, null);
        
        String output = testOut.toString();
        assertTrue(output.contains("Login successful"));
        assertTrue(output.contains("Slot added successfully!"));
        assertTrue(output.contains("Modified by Admin!"));
    }

    @Test
    void testAdminCancelConfirmedAndAlreadyCancelled() throws Exception {
        String input = "2\nTestUser\n2\n1\ny\n6\n" + 
                       "1\nadmin\nadmin123\n" +      
                       "5\n1\n" +                    
                       "5\n1\n" +                    
                       "7\n" +                       
                       "5\n";                        

        provideInput(input);
        mainApp = new Main();
        mainApp.start(); 

        String output = testOut.toString();
        assertTrue(output.contains("Appointment booked successfully!"));
        assertTrue(output.contains("Booking cancelled. Slot is now Available again"));
        assertTrue(output.contains("Slot cancelled successfully"));
    }
    
    @Test
    void testAdminEmptyRepository() throws Exception {
        mainApp = new Main();
        
        // Use reflection to access the repository instance inside Main
        Field repoField = Main.class.getDeclaredField("repository");
        repoField.setAccessible(true);
        Object repoInstance = repoField.get(mainApp);
        
        // Deep reflection to clear the internal storage of the repository
        // This handles both List, Set, or Map fields inside the repository
        for (Field f : repoInstance.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            Object val = f.get(repoInstance);
            if (val instanceof Collection) {
                ((Collection<?>) val).clear();
            } else if (val instanceof Map) {
                ((Map<?, ?>) val).clear();
            }
        }

        // Now the repository should be truly empty
        
        // Test View All Empty
        invokePrivateMethod("viewAllAppointments", null, null);
        assertTrue(testOut.toString().contains("No appointments found"));
        
        // Test Admin Cancel Empty
        invokePrivateMethod("adminCancelReservation", null, null);
        assertTrue(testOut.toString().contains("No appointments in the system"));
        
        // Test Admin Modify Empty
        invokePrivateMethod("adminModifyReservation", null, null);
        assertTrue(testOut.toString().contains("No appointments in the system"));
    }

    @Test
    void testUserBookModifyCancelSuccess() throws Exception {
        String input = "TestUser\n" +
                       "2\n" + // Book Menu
                       "1\n" + // Select Slot #1
                       "y\n" + // Confirm
                       "3\n" + // Modify Menu
                       "1\n" + // Select My Booking #1
                       "1\n" + // Select New Slot #1
                       "4\n" + // Cancel Menu
                       "1\n" + // Select My Booking #1
                       "y\n" + // Confirm Cancel
                       "6\n"; // Back
        
        provideInput(input);
        mainApp = new Main();
        invokePrivateMethod("userModeFlow", null, null);
        
        String output = testOut.toString();
        assertTrue(output.contains("Appointment booked successfully!"));
        assertTrue(output.contains("Appointment modified successfully!"));
        assertTrue(output.contains("Appointment cancelled successfully!"));
    }
    
    @Test
    void testUserViewMyBookingsEmpty() throws Exception {
        provideInput("TestUser\n5\n6\n"); // View My Bookings (5), then Back
        mainApp = new Main();
        invokePrivateMethod("userModeFlow", null, null);
        assertTrue(testOut.toString().contains("You have no bookings yet"));
    }

    @Test
    void testUserCancelAbort() throws Exception {
        String input = "TestUser\n" +
                       "2\n1\ny\n" +
                       "4\n1\nn\n" +
                       "6\n";
        
        provideInput(input);
        mainApp = new Main();
        invokePrivateMethod("userModeFlow", null, null);
        
        assertTrue(testOut.toString().contains("Cancellation aborted."));
    }

    @Test
    void testUserModeSuccessAndBook() throws Exception {
        String input = "TestUser\n" +
                       "2\n" +
                       "1\n" +
                       "y\n" +
                       "5\n" +
                       "6\n";
        
        provideInput(input);
        mainApp = new Main();
        
        invokePrivateMethod("userModeFlow", null, null);
        
        String output = testOut.toString();
        assertTrue(output.contains("Welcome, TestUser!"));
        assertTrue(output.contains("Appointment booked successfully!"));
        assertTrue(output.contains("MY BOOKINGS"));
    }

    @Test
    void testUserBookingRejectionAndCancel() throws Exception {
        String input = "TestUser\n" +
                       "2\n" +
                       "99\n" +
                       "6\n";
        
        provideInput(input);
        mainApp = new Main();
        invokePrivateMethod("userModeFlow", null, null);
        
        String output = testOut.toString();
        assertTrue(output.contains("Invalid selection"));
    }
    
    @Test
    void testUserBookingAbort() throws Exception {
        String input = "TestUser\n" +
                       "2\n" +
                       "1\n" +
                       "n\n" +
                       "6\n";
        
        provideInput(input);
        mainApp = new Main();
        invokePrivateMethod("userModeFlow", null, null);
        
        String output = testOut.toString();
        assertFalse(output.contains("Appointment booked successfully!"));
        assertTrue(output.contains("USER MENU"));
    }
    
    @Test
    void testUserNoAvailableSlotsToBook() throws Exception {
        // Book all available slots first
        // Initial data has 4 slots.
        String input = "TestUser\n" +
                       "2\n1\ny\n" + // Book 1
                       "2\n1\ny\n" + // Book 2
                       "2\n1\ny\n" + // Book 3
                       "2\n1\ny\n" + // Book 4
                       "2\n" +       // Try Book again
                       "6\n";
        
        provideInput(input);
        mainApp = new Main();
        invokePrivateMethod("userModeFlow", null, null);
        
        assertTrue(testOut.toString().contains("No available slots at the moment"));
    }
    
    @Test
    void testUserModifyNoOtherSlots() throws Exception {
        String input = 
            // User books slot 1
            "2\nTestUser\n2\n1\ny\n6\n" +
            // Admin Login
            "1\nadmin\nadmin123\n" +
            // Admin Cancel Slot 2
            "5\n2\n" +
            // Admin Cancel Slot 3
            "5\n3\n" +
            // Admin Cancel Slot 4
            "5\n4\n" +
            // Logout
            "7\n" +
            // User tries to modify
            "2\nTestUser\n3\n1\n6\n" + 
            "5\n"; 

        provideInput(input);
        mainApp = new Main();
        mainApp.start();
        
        assertTrue(testOut.toString().contains("No other available slots to move to"));
    }

    @ParameterizedTest
    @CsvSource({
        "viewAvailableSlots, AVAILABLE APPOINTMENT SLOTS",
        "sendAllReminders, SEND REMINDERS",
        "displayStatistics, SYSTEM STATISTICS",
        "displayUserStories, IMPLEMENTED USER STORIES"
    })
    void testDisplayMethods(String methodName, String expectedOutput) throws Exception {
        mainApp = new Main();
        invokePrivateMethod(methodName, null, null);
        assertTrue(testOut.toString().contains(expectedOutput));
    }
    
    @Test
    void testSendRemindersWithConfirmedAppointments() throws Exception {
        // User books a slot to make it confirmed
        String input = "TestUser\n2\n1\ny\n6\n";
        provideInput(input);
        mainApp = new Main();
        invokePrivateMethod("userModeFlow", null, null);
        
        // Admin sends reminders
        invokePrivateMethod("sendAllReminders", null, null);
        assertTrue(testOut.toString().contains("Sent 1 reminder(s) successfully!"));
    }
    
    @Test
    void testSendRemindersNoConfirmed() throws Exception {
        // Default state has only Available appointments
        mainApp = new Main();
        invokePrivateMethod("sendAllReminders", null, null);
        assertTrue(testOut.toString().contains("No confirmed appointments to send reminders for"));
    }

    @Test
    void testHelperInputMethods() throws Exception {
        provideInput("abc\n");
        mainApp = new Main();
        int result = (int) invokePrivateMethod("readIntInput", null, null);
        assertEquals(-1, result);
    }

    @Test
    void testEnterDateTimeInvalid() throws Exception {
        provideInput("2026\n13\n1\n10\n0\n");
        mainApp = new Main();
        Object result = invokePrivateMethod("enterDateTime", null, null);
        assertNull(result);
    }

    @Test
    void testEnterDurationInvalid() throws Exception {
        provideInput("0\n");
        mainApp = new Main();
        int res = (int) invokePrivateMethod("enterDuration", null, null);
        assertEquals(-1, res);
    }
    
    @Test
    void testEnterDurationExceedsMax() throws Exception {
        provideInput("150\n"); // > 120
        mainApp = new Main();
        int res = (int) invokePrivateMethod("enterDuration", null, null);
        assertEquals(-1, res);
        assertTrue(testOut.toString().contains("Duration must be between"));
    }

    @Test
    void testEnterParticipantsInvalid() throws Exception {
        provideInput("0\n");
        mainApp = new Main();
        int res = (int) invokePrivateMethod("enterParticipants", null, null);
        assertEquals(-1, res);
    }
    
    @Test
    void testEnterParticipantsExceedsMax() throws Exception {
        provideInput("15\n"); // > 10
        mainApp = new Main();
        int res = (int) invokePrivateMethod("enterParticipants", null, null);
        assertEquals(-1, res);
        assertTrue(testOut.toString().contains("Participants must be between"));
    }

    @Test
    void testSelectAppointmentTypeInvalid() throws Exception {
        provideInput("10\n");
        mainApp = new Main();
        Object res = invokePrivateMethod("selectAppointmentType", null, null);
        assertNull(res);
    }
}