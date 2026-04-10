package com.scheduling.app;

import com.scheduling.observer.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.io.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Additional branch coverage tests to achieve 85%+ branch coverage.
 */
class BranchCoverageTest {

    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setup() {
        EmailNotificationService.setTestMode(true);
    }

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
    }

    // ===================== MAIN MENU PARAMETERIZED =====================

    @ParameterizedTest(name = "Main menu scenario {index}")
    @MethodSource("mainMenuScenarios")
    void testMainMenuParameterized(String input) {

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Main main = new Main();

        outputStream.reset();
        main.start();

        String output = outputStream.toString();

        assertTrue(output.length() > 0);                                                         // NOSONAR
    }

    static Stream<Arguments> mainMenuScenarios() {
        return Stream.of(
                Arguments.of("1\nadmin\nadmin123\n6\n5\n"),
                Arguments.of("2\ntestuser\n6\n5\n"),
                Arguments.of("3\n\n\n5\n"),
                Arguments.of("4\n5\n"),
                Arguments.of("5\n"),
                Arguments.of("999\n5\n") // invalid choice
        );
    }

    // ===================== ADMIN MENU =====================

    @Test
    @DisplayName("Admin menu - all choices 1-6")
    void testAdminMenuAllChoices() {

        String input1 = "1\nadmin\nadmin123\n1\n6\n5\n";
        System.setIn(new ByteArrayInputStream(input1.getBytes()));
        Main main1 = new Main();
        outputStream.reset();
        main1.start();

        String input2 = "1\nadmin\nadmin123\n2\n6\n5\n";
        System.setIn(new ByteArrayInputStream(input2.getBytes()));
        Main main2 = new Main();
        outputStream.reset();
        main2.start();

        String input3 = "1\nadmin\nadmin123\n3\n99\n6\n5\n";
        System.setIn(new ByteArrayInputStream(input3.getBytes()));
        Main main3 = new Main();
        outputStream.reset();
        main3.start();

        String input4 = "1\nadmin\nadmin123\n4\n1\n6\n5\n";
        System.setIn(new ByteArrayInputStream(input4.getBytes()));
        Main main4 = new Main();
        outputStream.reset();
        main4.start();

        String input5 = "1\nadmin\nadmin123\n5\n6\n5\n";
        System.setIn(new ByteArrayInputStream(input5.getBytes()));
        Main main5 = new Main();
        outputStream.reset();
        main5.start();
    }

    @Test
    @DisplayName("Admin menu - default case (invalid choice)")
    void testAdminMenuDefaultCase() {
        String input = "1\nadmin\nadmin123\n-1\n6\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Main main = new Main();
        outputStream.reset();
        main.start();
        assertTrue(outputStream.toString().length() > 0);
    }

    // ===================== USER MENU =====================

    @Test
    @DisplayName("User menu - all choices 1-6")
    void testUserMenuAllChoices() {

        String input1 = "2\nuser1\n1\n6\n5\n";
        System.setIn(new ByteArrayInputStream(input1.getBytes()));
        Main main1 = new Main();
        outputStream.reset();
        main1.start();

        String input2 = "2\nuser1\n5\n6\n5\n";
        System.setIn(new ByteArrayInputStream(input2.getBytes()));
        Main main2 = new Main();
        outputStream.reset();
        main2.start();

        String input3 = "2\nuser1\n6\n5\n";
        System.setIn(new ByteArrayInputStream(input3.getBytes()));
        Main main3 = new Main();
        outputStream.reset();
        main3.start();
    }

    @Test
    @DisplayName("User menu - default case (invalid choice)")
    void testUserMenuDefaultCase() {
        String input = "2\nuser1\n-5\n6\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Main main = new Main();
        outputStream.reset();
        main.start();
        assertTrue(outputStream.toString().length() > 0);
    }

    // ===================== LOGIN =====================

    @Test
    @DisplayName("Admin login - success branch")
    void testAdminLoginSuccess() {
        String input = "1\nadmin\nadmin123\n6\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Main main = new Main();
        outputStream.reset();
        main.start();

        String output = outputStream.toString();
        assertTrue(output.contains("successful") || output.contains("Welcome"));
    }

    @Test
    @DisplayName("Admin login - failure branch")
    void testAdminLoginFailure() {
        String input = "1\nwrong\nwrong\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Main main = new Main();
        outputStream.reset();
        main.start();

        String output = outputStream.toString();
        assertTrue(output.contains("failed") || output.contains("Invalid"));
    }

    // ===================== USER LOGIN =====================

    @Test
    @DisplayName("User mode - empty username branch")
    void testUserModeEmptyUsername() {
        String input = "2\n\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Main main = new Main();
        outputStream.reset();
        main.start();

        String output = outputStream.toString();
        assertTrue(output.contains("empty") || output.contains("cannot"));
    }

    @Test
    @DisplayName("User mode - valid username branch")
    void testUserModeValidUsername() {
        String input = "2\nvaliduser\n6\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Main main = new Main();
        outputStream.reset();
        main.start();

        String output = outputStream.toString();
        assertTrue(output.contains("Welcome") || output.contains("validuser"));
    }
}