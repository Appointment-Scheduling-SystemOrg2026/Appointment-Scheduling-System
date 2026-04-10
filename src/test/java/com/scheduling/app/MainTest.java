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

    private Object getPrivateField(String fieldName) throws Exception {
        Field field = Main.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(mainApp);
    }
    
    @Test
    void testInitialization() throws Exception {
        mainApp = new Main(); 
        
        assertNotNull(getPrivateField("repository"));
        assertNotNull(getPrivateField("appointmentTypes"));
        assertFalse(((List<?>) getPrivateField("appointmentTypes")).isEmpty());
    }

    @Test
    void testAdminLoginFlow_Success() throws Exception {
        provideInput("admin\nadmin123\n6\n");

        mainApp = new Main();

        invokePrivateMethod("adminLoginFlow", null, null);

        String output = testOut.toString();
        assertTrue(output.contains("Login successful"), "Should show login success message");
        assertTrue(output.contains("Logged out."), "Should show logout message");
    }

    @Test
    void testAdminLoginFlow_InvalidCredentials() throws Exception {
        provideInput("wronguser\nwrongpass\n");
        
        mainApp = new Main();

        invokePrivateMethod("adminLoginFlow", null, null);

        String output = testOut.toString();
        assertTrue(output.contains("Invalid credentials"), "Should show invalid credentials message");
    }

    @Test
    void testUserModeFlow_Success() throws Exception {
        provideInput("testUser\n6\n");

        mainApp = new Main();

        invokePrivateMethod("userModeFlow", null, null);

        String output = testOut.toString();
        assertTrue(output.contains("Welcome, testUser!"), "Should welcome the user");
        assertNotNull(getPrivateField("currentUser"));
    }

    @Test
    void testUserModeFlow_EmptyUsername() throws Exception {
        provideInput("\n");
        mainApp = new Main();

        invokePrivateMethod("userModeFlow", null, null);

        String output = testOut.toString();
        assertTrue(output.contains("Username cannot be empty"), "Should reject empty username");
    }

    @Test
    void testViewAvailableSlots() throws Exception {
        mainApp = new Main();
        
        invokePrivateMethod("viewAvailableSlots", null, null);

        String output = testOut.toString();
        assertTrue(output.contains("available slot(s)"), "Should display available slots");
    }

    @Test
    void testGetFutureAppointments() throws Exception {
        mainApp = new Main();
        
        Field currentUserField = Main.class.getDeclaredField("currentUser");
        currentUserField.setAccessible(true);
        currentUserField.set(mainApp, new User("testUser", "pass"));

        List<?> result = (List<?>) invokePrivateMethod("getFutureAppointments", null, null);
        
        assertNotNull(result);
    }
    
    @Test
    void testSelectAppointmentType_ValidChoice() throws Exception {
        provideInput("1\n");
        mainApp = new Main();

        Object result = invokePrivateMethod("selectAppointmentType", null, null);
        
        assertNotNull(result);
        assertTrue(result.toString().contains("UrgentAppointment"));
        
        String output = testOut.toString();
        assertTrue(output.contains("Selected: UrgentAppointment"));
    }

    @Test
    void testSelectAppointmentType_InvalidChoice() throws Exception {
        provideInput("10\n");
        mainApp = new Main();

        Object result = invokePrivateMethod("selectAppointmentType", null, null);
        
        assertNull(result);
        assertTrue(testOut.toString().contains("Invalid selection"));
    }
}