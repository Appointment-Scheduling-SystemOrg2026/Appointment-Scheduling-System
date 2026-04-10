package com.scheduling.app;

import com.scheduling.domain.entity.User;
import org.junit.jupiter.api.*;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

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
    void testAdminLoginSuccess() throws Exception {
        provideInput("admin\nadmin123\n6\n");
        mainApp = new Main();
        
        invokePrivateMethod("adminLoginFlow", null, null);
        
        String output = testOut.toString();
        assertTrue(output.contains("Login successful"));
        assertTrue(output.contains("Logged out."));
    }

    @Test
    void testAdminLoginFail() throws Exception {
        provideInput("wrong\nwrong\n");
        mainApp = new Main();
        
        invokePrivateMethod("adminLoginFlow", null, null);
        
        assertTrue(testOut.toString().contains("Invalid credentials"));
    }
    
    @Test
    void testAdminFunctions() throws Exception {
        String input = "admin\nadmin123\n" +
                       "3\n1\n2026\n05\n05\n10\n0\n30\n1\n1\n" +
                       "5\n" +
                       "6\n";
        
        provideInput(input);
        mainApp = new Main();
        invokePrivateMethod("adminLoginFlow", null, null);
        
        String output = testOut.toString();
        assertTrue(output.contains("Login successful"));
        assertTrue(output.contains("SYSTEM STATISTICS"));
    }

    @Test
    void testUserModeSuccessAndBook() throws Exception {
        String input = "TestUser\n" +
                       "2\n" +
                       "1\n" +
                       "2027\n05\n10\n10\n0\n" +
                       "30\n" +
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
                       "1\n" +
                       "2027\n05\n10\n10\n0\n" +
                       "500\n" +
                       "1\n" +
                       "y\n" +
                       "6\n";
        
        provideInput(input);
        mainApp = new Main();
        invokePrivateMethod("userModeFlow", null, null);
        
        String output = testOut.toString();
        assertFalse(output.contains("Appointment booked successfully!"));
        assertTrue(output.contains("USER MENU"));
    }
    
    @Test
    void testUserBookingAbort() throws Exception {
        String input = "TestUser\n" +
                       "2\n" +
                       "1\n" +
                       "2027\n05\n10\n10\n0\n" +
                       "30\n" + "1\n" +
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
    void testCancelAppointmentFlow() throws Exception {
        provideInput("TestUser\n4\n1\nn\n6\n");
        mainApp = new Main();
        invokePrivateMethod("userModeFlow", null, null);
        assertTrue(testOut.toString().contains("No cancellable appointments found"));
    }

    @Test
    void testModifyAppointmentFlowInvalidIndex() throws Exception {
        provideInput("TestUser\n3\n99\n6\n");
        mainApp = new Main();
        invokePrivateMethod("userModeFlow", null, null);
        assertTrue(testOut.toString().contains("No modifiable appointments found"));
    }

    @Test
    void testViewAvailableSlots() throws Exception {
        mainApp = new Main();
        invokePrivateMethod("viewAvailableSlots", null, null);
        assertTrue(testOut.toString().contains("AVAILABLE APPOINTMENT SLOTS"));
    }

    @Test
    void testSendReminders() throws Exception {
        mainApp = new Main();
        invokePrivateMethod("sendAllReminders", null, null);
        assertTrue(testOut.toString().contains("SEND REMINDERS"));
    }

    @Test
    void testDisplayStatistics() throws Exception {
        mainApp = new Main();
        invokePrivateMethod("displayStatistics", null, null);
        assertTrue(testOut.toString().contains("SYSTEM STATISTICS"));
    }
    
    @Test
    void testDisplayUserStories() throws Exception {
        mainApp = new Main();
        invokePrivateMethod("displayUserStories", null, null);
        assertTrue(testOut.toString().contains("IMPLEMENTED USER STORIES"));
    }

    @Test
    void testRunSystemDemo() throws Exception {
        provideInput("\n\n");
        mainApp = new Main();
        invokePrivateMethod("runSystemDemo", null, null);
        assertTrue(testOut.toString().contains("DEMO COMPLETED SUCCESSFULLY"));
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
    void testEnterParticipantsInvalid() throws Exception {
        provideInput("0\n");
        mainApp = new Main();
        int res = (int) invokePrivateMethod("enterParticipants", null, null);
        assertEquals(-1, res);
    }

    @Test
    void testSelectAppointmentTypeInvalid() throws Exception {
        provideInput("10\n");
        mainApp = new Main();
        Object res = invokePrivateMethod("selectAppointmentType", null, null);
        assertNull(res);
    }
}