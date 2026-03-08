package com.scheduling.app;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;

class MainCoverageTest {

    @Test
    void runApplicationFlow() {

        String input =
                "2\n" +              // user mode
                "test@test.com\n" +
                "1\n" +              // view slots
                "2\n" +              // book
                "3\n" +              // modify
                "4\n" +              // cancel
                "5\n" +              // back
                "1\n" +              // admin login
                "admin\n" +
                "1234\n" +
                "1\n" +              // view appointments
                "2\n" +              // send reminders
                "3\n" +              // logout
                "3\n";               // exit

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        new Main().start();
    }
}


/*package com.scheduling.app;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

class MainFullFlowTest {

    @Test
    void shouldExecuteFullApplicationFlow() {

        String simulatedInput =
                "2\n" +   // User Mode
                "user@test.com\n" +
                "1\n" +   // View Slots
                "2\n" +   // Book
                "3\n" +   // Modify
                "4\n" +   // Cancel
                "5\n" +   // Back
                "1\n" +   // Admin login
                "admin\n" +
                "1234\n" +
                "1\n" +   // View appointments
                "2\n" +   // Send reminders
                "3\n" +   // Logout
                "3\n";    // Exit

        System.setIn(
                new ByteArrayInputStream(
                        simulatedInput.getBytes()
                )
        );

        Main main = new Main();
        main.start();
    }
}*/