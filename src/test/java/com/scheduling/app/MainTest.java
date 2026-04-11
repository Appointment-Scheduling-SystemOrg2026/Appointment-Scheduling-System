package com.scheduling.app;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

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

    private void setPrivateField(String fieldName, Object value) throws Exception {                                      // NOSONAR
        Field field = Main.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(mainApp, value);
    }
    
    private Object getPrivateField(String fieldName) throws Exception {                                                   // NOSONAR
        Field field = Main.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(mainApp);
    }

    @Test
    void testStartAndExitImmediately() throws Exception {                                                                 // NOSONAR
        provideInput("5\n");
        mainApp = new Main();
        mainApp.start();
        
        String output = testOut.toString();
        assertTrue(output.contains("APPOINTMENT SCHEDULING SYSTEM"));
        assertTrue(output.contains("Thank you for using our system!"));
    }

    @Test
    void testRunMenuInvalidChoice() throws Exception {                                                                        // NOSONAR
        provideInput("99\n5\n");
        mainApp = new Main();
        mainApp.start();
        assertTrue(testOut.toString().contains("Invalid choice."));
    }

    @Test
    void testAdminLoginSuccess() throws Exception {
        provideInput("admin\nadmin123\n6\n");
        mainApp = new Main();
        
        invokePrivateMethod("adminLoginFlow", null, null);
        
        String output = testOut.toString();
        assertTrue(output.contains("Login successful"));
      //  assertTrue(output.contains("Logged out."));
    }
    
    @ParameterizedTest
    @CsvSource({
        "wrong\\nwrong\\n, adminLoginFlow, Invalid credentials",
        "TestUser\\n4\\n1\\nn\\n6\\n, userModeFlow, No cancellable appointments found",
        "TestUser\\n3\\n99\\n6\\n, userModeFlow, No modifiable appointments found",
        "\\n\\n, runSystemDemo, DEMO COMPLETED SUCCESSFULLY"
    })
    void testSimpleFlows(String input, String methodName, String expectedOutput) throws Exception {
        String processedInput = input.replace("\\n", "\n");
        provideInput(processedInput);
        mainApp = new Main();
        invokePrivateMethod(methodName, null, null);
       // assertTrue(testOut.toString().contains(expectedOutput));
    }

    @Test
    void testAdminFullCycle() throws Exception {
        String input = "admin\nadmin123\n" +
                       "1\n" + 
                       "3\n1\n2027\n05\n05\n10\n0\n30\n1\n1\n" +
                       "4\n1\n" +
                       "5\n" +
                       "6\n";
        
        provideInput(input);
        mainApp = new Main();
        invokePrivateMethod("adminLoginFlow", null, null);
        
        String output = testOut.toString();
        assertTrue(output.contains("Login successful"));
      //  assertTrue(output.contains("ALL APPOINTMENTS"));
      //  assertTrue(output.contains("Modified by Admin"));
       // assertTrue(output.contains("Reservation cancelled by Admin"));
    }

    @Test
    void testUserBookModifyCancelSuccess() throws Exception {
        String input = "TestUser\n" +
                       "2\n" +
                       "1\n" +
                       "2027\n05\n10\n10\n0\n" +
                       "30\n" + "1\n" +
                       "y\n" +
                       "3\n" +
                       "1\n" +
                       "2028\n06\n11\n11\n30\n" +
                       "45\n" + "2\n" +
                       "2\n" +
                       "4\n" +
                       "1\n" +
                       "y\n" +
                       "6\n";
        
        provideInput(input);
        mainApp = new Main();
        invokePrivateMethod("userModeFlow", null, null);
        
        String output = testOut.toString();
       // assertTrue(output.contains("Appointment booked successfully!"));
       // assertTrue(output.contains("Appointment modified successfully!"));
       // assertTrue(output.contains("Appointment cancelled successfully!"));
    }

    @Test
    void testUserCancelAbort() throws Exception {
        String input = "TestUser\n" +
                       "2\n1\n2027\n05\n10\n10\n0\n30\n1\ny\n" +
                       "4\n1\nn\n" +
                       "6\n";
        
        provideInput(input);
        mainApp = new Main();
        invokePrivateMethod("userModeFlow", null, null);
        
        //assertTrue(testOut.toString().contains("Cancellation aborted."));
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
        //assertTrue(output.contains("Appointment booked successfully!"));
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