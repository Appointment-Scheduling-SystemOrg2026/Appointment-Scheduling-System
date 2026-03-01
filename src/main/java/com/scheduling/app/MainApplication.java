package com.scheduling.app;

import com.scheduling.domain.entity.*;
import com.scheduling.domain.type.AppointmentType;
import com.scheduling.domain.type.InPersonAppointment;
import com.scheduling.domain.type.VirtualAppointment;
import com.scheduling.repository.AppointmentRepository;
import com.scheduling.service.*;
import com.scheduling.strategy.*;
import com.scheduling.observer.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

/**
 * Main entry point for Appointment Scheduling System.
 *
 * Demonstrates all project sprints functionality.
 *
 * @author Tasneem
 * @version 1.0
 */
public class MainApplication {

    private final AuthenticationService authService;
    private final AppointmentService appointmentService;
    private final ReminderService reminderService;

    private final Scanner scanner = new Scanner(System.in);

    public MainApplication() {

        AppointmentRepository repository =
                new AppointmentRepository();

        authService = new AuthenticationService();

        appointmentService =
                new AppointmentService(
                        repository,
                        List.of(
                                new DurationRuleStrategy(),
                                new ParticipantLimitStrategy()
                        )
                );

        Observer emailObserver =
                new EmailNotificationService();

        reminderService =
                new ReminderService(List.of(emailObserver));
    }

    

    public void start() {

        System.out.println("=== Appointment Scheduling System ===");

        Administrator admin =
                new Administrator("admin","1234");

        boolean running = true;

        while (running) {

            System.out.println("\n1. Login");
            System.out.println("2. View Slots");
            System.out.println("3. Book Appointment");
            System.out.println("4. Modify Appointment");
            System.out.println("5. Cancel Appointment");
            System.out.println("6. Send Reminder");
            System.out.println("7. Logout");
            System.out.println("8. Exit");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {

                case 1:
                    login(admin);
                    break;

                case 2:
                    viewSlots();
                    break;

                case 3:
                    bookAppointment();
                    break;

                case 4:
                    modifyAppointment();
                    break;

                case 5:
                    cancelAppointment();
                    break;

                case 6:
                    sendReminder();
                    break;

                case 7:
                    authService.logout();
                    System.out.println("Logged out.");
                    break;

                case 8:
                    running = false;
                    break;
            }
        }

        System.out.println("System closed.");
    }

    

    private void login(Administrator admin) {

        System.out.print("Username: ");
        String user = scanner.nextLine();

        System.out.print("Password: ");
        String pass = scanner.nextLine();

        boolean result =
                authService.login(admin,user,pass);

        System.out.println(
                result ? "Login success" : "Invalid credentials"
        );
    }

    

    private void viewSlots() {

        System.out.println("Available appointments:");

        appointmentService
                .viewAvailableSlots()
                .forEach(System.out::println);
    }

    

    private void bookAppointment() {

        Appointment appointment =
                new Appointment(
                        LocalDateTime.now().plusHours(2),
                        30,
                        2,
                        new InPersonAppointment()
                );

        boolean result =
                appointmentService.book(appointment);

        System.out.println(
                result ?
                        "Appointment confirmed" :
                        "Booking rejected"
        );
    }

   

    private void modifyAppointment() {

        Appointment oldAppt =
                new Appointment(
                        LocalDateTime.now().plusHours(2),
                        30,
                        1,
                        new InPersonAppointment()
                );

        Appointment updated =
                new Appointment(
                        LocalDateTime.now().plusDays(1),
                        45,
                        2,
                        new VirtualAppointment()
                );

        appointmentService.modifyAppointment(
                oldAppt,
                updated
        );

        System.out.println("Appointment modified.");
    }

    

    private void cancelAppointment() {

        Appointment appointment =
                new Appointment(
                        LocalDateTime.now().plusHours(3),
                        30,
                        1,
                        AppointmentType.URGENT
                );

        appointmentService.cancelAppointment(appointment);

        System.out.println("Appointment cancelled.");
    }

    
    private void sendReminder() {

        User user =
                new User("scheduling.project2026@gmail.com","123");

        Appointment appointment =
                new Appointment(
                        LocalDateTime.now().plusHours(1),
                        30,
                        1,
                        new VirtualAppointment()
                );

        reminderService.sendReminder(user,appointment);

        System.out.println("Reminder sent.");
    }

    

    public static void main(String[] args) {

        new MainApplication().start();
    }
}

