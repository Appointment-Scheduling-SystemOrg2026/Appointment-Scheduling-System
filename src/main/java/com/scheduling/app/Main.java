package com.scheduling.app;

import com.scheduling.domain.entity.*;
import com.scheduling.domain.type.InPersonAppointment;
import com.scheduling.domain.type.VirtualAppointment;
import com.scheduling.repository.AppointmentRepository;
import com.scheduling.service.*;
import com.scheduling.strategy.*;
import com.scheduling.observer.*;

import java.time.LocalDateTime;
import java.util.*;

public class Main {

    private final Scanner scanner = new Scanner(System.in);

    // ===== Layers =====
    private AppointmentRepository repository;
    private AppointmentService appointmentService;
    private ReminderService reminderService;
    private AuthenticationService authService;

    private Administrator admin;
    private User currentUser;

    public Main() {
        initializeSystem();
    }

    /**
     * ===============================
     * SYSTEM INITIALIZATION
     * ===============================
     */
    private void initializeSystem() {

        repository = new AppointmentRepository();

        appointmentService =
                new AppointmentService(
                        repository,
                        Arrays.asList(
                                new DurationRuleStrategy(),
                                new ParticipantLimitStrategy()
                        )
                );

        reminderService =
                new ReminderService(
                        Arrays.asList(
                                new NotificationService()
                        )
                );

        authService = new AuthenticationService();

        admin = new Administrator("admin", "1234");

        initializeTestAppointments();

        System.out.println("✅ Appointment Scheduling System Ready");
    }

    /**
     * Add Sample Data (IMPORTANT)
     */
    private void initializeTestAppointments() {

        repository.save(
                new Appointment(
                        LocalDateTime.now().plusHours(2),
                        30,
                        1,
                        new InPersonAppointment()
                )
        );

        repository.save(
                new Appointment(
                        LocalDateTime.now().plusDays(1),
                        45,
                        2,
                        new VirtualAppointment()
                )
        );

        System.out.println("✅ Sample appointments added");
    }

    /**
     * ===============================
     * MAIN APPLICATION LOOP
     * ===============================
     */
    public void start() {

        boolean running = true;

        while (running) {

            System.out.println("\n==== MAIN MENU ====");
            System.out.println("1. Admin Login");
            System.out.println("2. User Mode");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    adminMenu();
                    break;

                case 2:
                    userMenu();
                    break;

                case 3:
                    running = false;
                    break;
            }
        }
    }

    /**
     * ===============================
     * ADMIN MENU (Sprint 1 + Sprint 4)
     * ===============================
     */
    private void adminMenu() {

        scanner.nextLine();

        System.out.print("Username: ");
        String user = scanner.nextLine();

        System.out.print("Password: ");
        String pass = scanner.nextLine();

        if (!authService.login(admin, user, pass)) {
            System.out.println("❌ Login failed");
            return;
        }

        System.out.println("✅ Admin Logged In");

        boolean logged = true;

        while (logged) {

            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. View Appointments");
            System.out.println("2. Send Reminders");
            System.out.println("3. Logout");

            int c = scanner.nextInt();

            switch (c) {

                case 1:
                    repository.findAll()
                            .forEach(System.out::println);
                    break;

                case 2:
                    sendReminders();
                    break;

                case 3:
                    authService.logout();
                    logged = false;
                    break;
            }
        }
    }

    /**
     * ===============================
     * USER MENU (Sprint 2 + Sprint 5)
     * ===============================
     */
    private void userMenu() {

        scanner.nextLine();

        System.out.print("Enter email(username): ");
        String username = scanner.nextLine();

        currentUser = new User(username, "1234");

        boolean active = true;

        while (active) {

            System.out.println("\n--- USER MENU ---");
            System.out.println("1. View Available Slots");
            System.out.println("2. Book Appointment");
            System.out.println("3. Modify Appointment");
            System.out.println("4. Cancel Appointment");
            System.out.println("5. Back");

            int choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    viewSlots();
                    break;

                case 2:
                    bookAppointment();
                    break;

                case 3:
                    modifyAppointment();
                    break;

                case 4:
                    cancelAppointment();
                    break;

                case 5:
                    active = false;
                    break;
            }
        }
    }

    /**
     * ===============================
     * FEATURES IMPLEMENTATION
     * ===============================
     */

    private void viewSlots() {

        System.out.println("\nAvailable Slots:");

        appointmentService
                .viewAvailableSlots()
                .forEach(System.out::println);
    }

    private void bookAppointment() {

        Appointment appointment =
                new Appointment(
                        LocalDateTime.now().plusDays(1),
                        30,
                        2,
                        new InPersonAppointment()
                );

        boolean booked = appointmentService.book(appointment);

        if (booked) {
            appointment.confirm();
            System.out.println("✅ Appointment Booked");
        } else {
            System.out.println("❌ Booking rejected");
        }
    }

    private void modifyAppointment() {

        List<Appointment> list = repository.findAll();

        if (list.isEmpty()) {
            System.out.println("No appointments");
            return;
        }

        Appointment original = list.get(0);

        Appointment updated =
                new Appointment(
                        LocalDateTime.now().plusDays(2),
                        45,
                        1,
                        new VirtualAppointment()
                );

        appointmentService.modifyAppointment(original, updated);

        System.out.println("✅ Appointment Modified");
    }

    private void cancelAppointment() {

        List<Appointment> list = repository.findAll();

        if (list.isEmpty()) return;

        appointmentService.cancelAppointment(list.get(0));

        System.out.println("✅ Appointment Cancelled");
    }

    private void sendReminders() {

        repository.findAll().forEach(a ->
                reminderService.sendReminder(
                        new User("test@gmail.com", "1234"),
                        a
                )
        );

        System.out.println("✅ Reminders sent");
    }

    /**
     * ===============================
     * APPLICATION ENTRY POINT
     * ===============================
     */
    public static void main(String[] args) {
        new Main().start();
    }
}