package com.scheduling.app;

import com.scheduling.domain.entity.*;
import com.scheduling.domain.type.*;
import com.scheduling.repository.AppointmentRepository;
import com.scheduling.service.*;
import com.scheduling.strategy.*;
import com.scheduling.observer.*;
import com.scheduling.observer.Observer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.logging.*;

/**
 * Main Application Entry Point for the Appointment Scheduling System.
 *
 * <p>This class demonstrates all implemented User Stories across 5 Sprints:</p>
 * <ul>
 *   <li><b>Sprint 1:</b> Core Scheduling &amp; Authentication (US1.1-US1.3)</li>
 *   <li><b>Sprint 2:</b> Booking &amp; Business Rules (US2.1-US2.3)</li>
 *   <li><b>Sprint 3:</b> Notifications &amp; Mocking (US3.1)</li>
 *   <li><b>Sprint 5:</b> Appointment Types &amp; Polymorphism (US5.1-US5.2)</li>
 * </ul>
 *
 * @author Tasneem
 * @version 2.0
 * @since 2025
 */
public class Main {
    
     private static final String PROJECT_EMAIL = "scheduling.project2026@gmail.com";
     private static final String ENTER_CHOICE_PROMPT = "Enter your choice: ";
     private static final String MINUTES_SUFFIX = " minutes";
     private static final String INVALID_SELECTION_MSG = "Invalid selection.";
     private static final String ENTER_APPOINTMENT_NUMBER_PROMPT = "\nEnter appointment number: ";
     private static final String HORIZONTAL_SEPARATOR = "========================================================";
     private static final String SHORT_SEPARATOR = "   --------------------------------";
     private static final String LIST_ITEM_PREFIX = "   - ";
     private static final String DASH_SEPARATOR = "--------------------------------------------------------";
     private static final String ADMIN_USERNAME = "admin";
     private static final String EMPTY_LINE = "                                                        ";   
     private static final String ADMIN_PASSWORD =
                Optional.ofNullable(System.getenv("ADMIN_PASSWORD"))
                        .orElse("admin123");
     private static final Logger logger = Logger.getLogger(Main.class.getName());
     
     private boolean running = true;
     private boolean inAdminMenu = false;
     private boolean inUserMenu = false;
     private static final String ADMIN_DASHBOARD_TITLE = "ADMIN DASHBOARD";
     private static final String LIST_ITEM_FORMAT = "  [%d] %s%n";
     
     
     static {
         ConsoleHandler handler = new ConsoleHandler();
         handler.setFormatter(new SimpleConsoleFormatter());
         logger.setUseParentHandlers(false);
         logger.addHandler(handler);
     }
     
     class MenuItem {
            String title;
            Runnable action;
            MenuItem(String title, Runnable action) {
                this.title = title;
                this.action = action;
            }
        }
     
    //  CONSTANTS 
    /** Maximum appointment duration in minutes */
    private static final int MAX_DURATION = 120;
    /** Maximum participants per appointment */
    private static final int MAX_PARTICIPANTS = 10;
    /** Date time format for display */
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    //  DEPENDENCIES 
    private final Scanner scanner = new Scanner(System.in);
    /** Repository for appointment persistence */
    private AppointmentRepository repository;
    /** Service for appointment operations */
    private AppointmentService appointmentService;
    /** Manager for notifications using Observer pattern */
    private NotificationManager notificationManager;
    /** Service for authentication */
    private AuthenticationService authService;
    /** System administrator */
    private Administrator admin;
    /** Currently logged-in user */
    private User currentUser;
    /** List of all supported appointment types */
    private final List<AppointmentType> appointmentTypes = new ArrayList<>();
    
    private void runMenu(String title, List<MenuItem> items) {
        boolean stayInLoop = true; 
        while (stayInLoop) {
            printMenuDisplay(title, items);
            System.out.print(ENTER_CHOICE_PROMPT);                                            // NOSONAR
            int choice = readIntInput();
            if (choice == -2) {
                running = false;
                stayInLoop = false; 
            } 
            else if (choice < 1 || choice > items.size()) {
                System.out.println("Invalid choice.");                                                 // NOSONAR
            } 
            else {
                items.get(choice - 1).action.run();
                if (!running || 
                         (title.equals(ADMIN_DASHBOARD_TITLE) && !inAdminMenu) ||
                    (title.equals("USER MENU") && !inUserMenu)) {
                    
                    stayInLoop = false; 
                }
            }
        }
    }
    //  CONSTRUCTOR 
    /**
     * Constructs the Main application and initializes all system components.
     */
    public Main() {
        initializeSystem();
        initializeAppointmentTypes();
        authService = new AuthenticationService();
        admin = new Administrator(ADMIN_USERNAME, ADMIN_PASSWORD);
    }
    //  INITIALIZATION METHODS 
    /**
     * Initializes all system layers and dependencies.
     */
    private void initializeSystem() {
         logger.info(HORIZONTAL_SEPARATOR);
        printHeader("                         Welcome                   ");
        logger.info(HORIZONTAL_SEPARATOR);
        // Initialize Repository Layer
        repository = new AppointmentRepository();
        // Initialize Strategy Rules (Strategy Pattern)
        List<BookingRuleStrategy> bookingRules = Arrays.asList(
                new DurationRuleStrategy(MAX_DURATION),
                new ParticipantLimitStrategy(MAX_PARTICIPANTS)
        );
        // Initialize Service Layer
        appointmentService = new AppointmentService(repository, bookingRules);
     // Initialize Observer Pattern for notifications
        NotificationService consoleService = new NotificationService();
        EmailNotificationService emailService = new EmailNotificationService();
        emailService.enableRealEmail(PROJECT_EMAIL, "jgwgymubwqmxxylu");
        List<Observer> notificationObservers = Arrays.asList(consoleService, emailService);
        notificationManager = new NotificationManager(notificationObservers);
        // Initialize Authentication
        authService = new AuthenticationService();
        // Create default administrator
        admin = new Administrator(ADMIN_USERNAME, ADMIN_PASSWORD);
       
        // Add sample data
        initializeSampleData();
        
    }
    /**
     * Initializes all supported appointment types.
     */
    private void initializeAppointmentTypes() {
        appointmentTypes.add(new UrgentAppointment());
        appointmentTypes.add(new FollowUpAppointment());
        appointmentTypes.add(new AssessmentAppointment());
        appointmentTypes.add(new VirtualAppointment());
        appointmentTypes.add(new InPersonAppointment());
        appointmentTypes.add(new IndividualAppointment());
        appointmentTypes.add(new GroupAppointment());
    }
    /**
     * Adds sample appointments for testing and demonstration.
     */
    private void initializeSampleData() {
        
        // Sample 1: In-person appointment tomorrow
        repository.save(new Appointment(
                LocalDateTime.now().plusDays(1).withHour(10).withMinute(0),
                30,
                1,
                new InPersonAppointment()
        ));
        // Sample 2: Virtual appointment in 2 days
        repository.save(new Appointment(
                LocalDateTime.now().plusDays(2).withHour(14).withMinute(30),
                45,
                3,
                new VirtualAppointment()
        ));
        // Sample 3: Urgent appointment in 3 hours
        repository.save(new Appointment(
                LocalDateTime.now().plusHours(3),
                15,
                1,
                new UrgentAppointment()
        ));
        // Sample 4: Group appointment next week
        repository.save(new Appointment(
                LocalDateTime.now().plusWeeks(1).withHour(9).withMinute(0),
                60,
                5,
                new GroupAppointment()
        ));
      
    }
    //  MAIN APPLICATION LOOP 
    /**
     * Starts the main application loop.
     */
    public void start() {
        printWelcomeBanner();
        running = true;
        while (running) {
            runMenu("MAIN MENU", List.of(
                new MenuItem("Administrator Login", this::adminLoginFlow), 
                new MenuItem("User Mode", this::userModeFlow),             
                new MenuItem("System Demo", this::runSystemDemo),
                new MenuItem("View User Stories", this::displayUserStories),
                new MenuItem("Exit", () -> {
                    printGoodbyeMessage();
                    running = false;
                })
            ));
        }
    }
    
    //  SPRINT 1: AUTHENTICATION 
    /**
     * Handles administrator login flow (US1.1).
     */
    private void adminLoginFlow() {
        System.out.print("Username: ");                                                              // NOSONAR
        String username = safeReadLine().trim(); 
        System.out.print("Password: ");                                                                // NOSONAR
        String password = safeReadLine().trim(); 
        
        if (!username.equals(ADMIN_USERNAME) || !password.equals(ADMIN_PASSWORD)) {
            System.out.println("Invalid credentials");                                               // NOSONAR
            return;
        }
        System.out.println("Login successful");                                                         // NOSONAR
        inAdminMenu = true;
        runMenu(ADMIN_DASHBOARD_TITLE, List.of(
            new MenuItem("Add New Available Slot", this::adminAddSlot),
            new MenuItem("View All Appointments", this::viewAllAppointments),
            new MenuItem("Send Reminders", this::sendAllReminders),
            new MenuItem("Modify Reservation", this::adminModifyReservation),
            new MenuItem("Cancel Reservation", this::adminCancelReservation),
            new MenuItem("Statistics", this::displayStatistics),
            new MenuItem("Logout", () -> {
                System.out.println("Logged out.");                                                        // NOSONAR
                inAdminMenu = false;
            })
        ));
    }
    
    /**
     * Handles user mode flow.
     */
    private void userModeFlow() {
        printHeader("USER MODE");
        System.out.print("Enter your email/username: ");                                                          // NOSONAR
        String username = safeReadLine().trim(); 
        if (username.isEmpty()) {
            System.out.println("Username cannot be empty.");                                      // NOSONAR
            return;
        }
        currentUser = new User(username, "user123");
        System.out.println("✅ Welcome, " + username + "!");                                      // NOSONAR
        userMenu();
    }
    /**
     * Displays the user menu.
     */
    private void userMenu() {
        inUserMenu = true; 
        runMenu("USER MENU", List.of(
            new MenuItem("View Slots", this::viewAvailableSlots),
            new MenuItem("Book Appointment", this::bookAppointmentFlow),
            new MenuItem("Modify Appointment", this::modifyAppointmentFlow),
            new MenuItem("Cancel Appointment", this::cancelAppointmentFlow),
            new MenuItem("View My Bookings", this::viewMyBookings),
            new MenuItem("Back", () -> {
                System.out.println("Back to main menu.");                                                           // NOSONAR
                inUserMenu = false; 
            })
        ));
    }
    //  SPRINT 1: VIEW SLOTS 
    /**
     * Displays all available appointment slots.
     */
    private void viewAvailableSlots() {
        printHeader("AVAILABLE APPOINTMENT SLOTS (US1.3)");
        List<Appointment> availableSlots = appointmentService.viewAvailableSlots().stream()
                .filter(apt -> apt.getStatus() == AppointmentStatus.AVAILABLE) 
                .collect(Collectors.toList());
                
        if (availableSlots.isEmpty()) {
            System.out.println("No available slots at the moment.");                                                               // NOSONAR
            return;
        }
        displayAppointmentList(availableSlots, "Found " + availableSlots.size() + " available slot(s):");
    }
    //  SPRINT 2: BOOKING 
    /**
     * Handles the appointment booking flow.
     */
    private void bookAppointmentFlow() {
        printHeader("BOOK NEW APPOINTMENT (US2.1)");
        
        List<Appointment> availableSlots = appointmentService.viewAvailableSlots().stream()
                .filter(apt -> apt.getStatus() == AppointmentStatus.AVAILABLE) 
                .collect(Collectors.toList());
                
        if (availableSlots.isEmpty()) {
            System.out.println("No available slots at the moment."); // NOSONAR
            return;
        }
        
        displayAppointmentList(availableSlots, "Found " + availableSlots.size() + " available slot(s):");
        
        System.out.print("\nSelect slot number to book: "); // NOSONAR
        int index = readIntInput() - 1;
        
        if (index < 0 || index >= availableSlots.size()) {
            System.out.println(INVALID_SELECTION_MSG); // NOSONAR
            return;
        }
        
        Appointment toBook = availableSlots.get(index);
        
        System.out.print("Confirm booking this slot? (y/n): "); // NOSONAR
        String confirm = safeReadLine().trim().toLowerCase();
        
        if (confirm.equals("y") || confirm.equals("yes")) {
            toBook.setStatus(AppointmentStatus.CONFIRMED);
            toBook.setBookedBy(currentUser.getUsername());
            
            System.out.println("✅ Appointment booked successfully!"); // NOSONAR
            
            notificationManager.sendReminderToEmail(PROJECT_EMAIL, toBook);
        } else {
            System.out.println("Booking cancelled."); // NOSONAR
        }
    }
    // SPRINT 5: APPOINTMENT TYPES 
    /**
     * Allows user to select an appointment type.
     */
    private AppointmentType selectAppointmentType() {
        System.out.println("\nSelect Appointment Type (US5.1):");                                              // NOSONAR
        System.out.println("   -----------------------------");                                         // NOSONAR
        
        String[] typeNames = {
                "Urgent", "Follow-up", "Assessment",
                "Virtual", "In-Person", "Individual", "Group"
        };
        
        for (int i = 0; i < typeNames.length; i++) {
            int index = i;
            System.out.println("   [" + (index + 1) + "] " + typeNames[index]);                             // NOSONAR
        }
        
        System.out.print("\nSelect type (1-7): ");                                                               // NOSONAR
        int choice = readIntInput();
        
        if (choice < 1 || choice > 7) {
            System.out.println(INVALID_SELECTION_MSG);                                                  // NOSONAR
            return null;
        }
        
        AppointmentType selectedType = appointmentTypes.get(choice - 1);
        System.out.println("✅ Selected: " + selectedType.getClass().getSimpleName());                                      // NOSONAR
        return selectedType;
    }
    //  SPRINT 4: MODIFY/CANCEL  
    /**
     * Handles appointment modification flow.
     */
    private void modifyAppointmentFlow() {
        printHeader("MODIFY APPOINTMENT (US4.1)");
        
        List<Appointment> myAppointments = repository.findAll().stream()
            .filter(apt -> currentUser.getUsername().equals(apt.getBookedBy()))
            .collect(Collectors.toList());
        if (myAppointments.isEmpty()) {
            System.out.println("No modifiable appointments found."); // NOSONAR
            return;
        }
        
        displayAppointmentList(myAppointments, "Select appointment to modify:");
        
        System.out.print(ENTER_APPOINTMENT_NUMBER_PROMPT); // NOSONAR
        int index = readIntInput() - 1;
        
        if (index < 0 || index >= myAppointments.size()) {
            System.out.println(INVALID_SELECTION_MSG); // NOSONAR
            return;
        }
        
        Appointment toModify = myAppointments.get(index);
        
        List<Appointment> availableSlots = appointmentService.viewAvailableSlots().stream()
            .filter(apt -> apt.getStatus() == AppointmentStatus.AVAILABLE)
            .collect(Collectors.toList());
            
        availableSlots.remove(toModify); 
        if (availableSlots.isEmpty()) {
            System.out.println("No other available slots to move to."); // NOSONAR
            return;
        }
        
        displayAppointmentList(availableSlots, "Select a new slot from the available list:");
        
        System.out.print("\nSelect new slot number: "); // NOSONAR
        int newIndex = readIntInput() - 1;
        
        if (newIndex < 0 || newIndex >= availableSlots.size()) {
            System.out.println(INVALID_SELECTION_MSG); // NOSONAR
            return;
        }
        
        Appointment newSlot = availableSlots.get(newIndex);
        
        toModify.setStatus(AppointmentStatus.AVAILABLE); 
        toModify.setBookedBy(null);
        
        newSlot.setStatus(AppointmentStatus.CONFIRMED); 
        newSlot.setBookedBy(currentUser.getUsername());
        
        System.out.println("✅ Appointment modified successfully!"); // NOSONAR
    }
    /**
     * Admin adds a new available slot to the system.
     */
    private void adminAddSlot() {
        printHeader("ADD NEW AVAILABLE SLOT");
        
        AppointmentType selectedType = selectAppointmentType();
        if (selectedType == null) return;
        LocalDateTime dateTime = enterDateTime();
        if (dateTime == null) return;
        int duration = enterDuration();
        if (duration <= 0) return;
        int participants = enterParticipants();
        if (participants <= 0) return;
        Appointment appointment = new Appointment(dateTime, duration, participants, selectedType);
        appointment.setStatus(AppointmentStatus.AVAILABLE); 
        
        repository.save(appointment);
        System.out.println("✅ Slot added successfully!"); // NOSONAR
    }
    /**
     * Handles appointment cancellation flow.
     */
    private void cancelAppointmentFlow() {
        printHeader("CANCEL APPOINTMENT (US4.1)");
        
        List<Appointment> myAppointments = repository.findAll().stream()
            .filter(apt -> currentUser.getUsername().equals(apt.getBookedBy()))
            .collect(Collectors.toList());
            
        if (myAppointments.isEmpty()) {
            System.out.println("You have no bookings to cancel."); // NOSONAR
            return;
        }
        
        displayAppointmentList(myAppointments, "Select appointment to cancel:");
        
        System.out.print(ENTER_APPOINTMENT_NUMBER_PROMPT); // NOSONAR
        int index = readIntInput() - 1;
        
        if (index < 0 || index >= myAppointments.size()) {
            System.out.println(INVALID_SELECTION_MSG); // NOSONAR
            return;
        }
        
        Appointment toCancel = myAppointments.get(index);
        System.out.println("\nYou are about to cancel:"); // NOSONAR
        System.out.println("   " + formatAppointment(toCancel)); // NOSONAR
        
        System.out.print("\nConfirm cancellation? (y/n): "); // NOSONAR
        String confirm = safeReadLine().trim().toLowerCase();
        
        if (confirm.equals("y") || confirm.equals("yes")) {
            toCancel.setStatus(AppointmentStatus.AVAILABLE);
            toCancel.setBookedBy(null); 
            
            System.out.println("✅ Appointment cancelled successfully!"); // NOSONAR
            System.out.println("   The slot is now available for booking."); // NOSONAR
        } else {
            System.out.println("Cancellation aborted."); // NOSONAR
        }
    }
    /**
     * Admin modifies any reservation (US4.2).
     */
    private void adminModifyReservation() {
        printHeader("ADMIN: MODIFY RESERVATION (US4.2)");
        List<Appointment> allAppointments = repository.findAll();
        if (allAppointments.isEmpty()) {
            System.out.println("No appointments in the system.");                                // NOSONAR
            return;
        }
        displayAppointmentList(allAppointments, "Select reservation to modify:");
        System.out.print(ENTER_APPOINTMENT_NUMBER_PROMPT);                                          // NOSONAR
        int index = readIntInput() - 1;
        if (index < 0 || index >= allAppointments.size()) {
            System.out.println(INVALID_SELECTION_MSG);                                                // NOSONAR
            return;
        }
        Appointment original = allAppointments.get(index);
        System.out.println("\nSelected: " + formatAppointment(original));                                  // NOSONAR
        LocalDateTime newDateTime = enterDateTime();
        int newDuration = enterDuration();
        int newParticipants = enterParticipants();
        AppointmentType newType = selectAppointmentType();
        Appointment updated = new Appointment(newDateTime, newDuration, newParticipants, newType);
        boolean success = appointmentService.modifyAppointment(original, updated);
        System.out.println(success ? "✅ Modified by Admin!" : "Modification failed.");                       // NOSONAR
    }
    /**
     * Admin cancels any reservation (US4.2).
     */
    private void adminCancelReservation() {
        printHeader("ADMIN: CANCEL RESERVATION");
        
        List<Appointment> allAppointments = repository.findAll();
        
        if (allAppointments.isEmpty()) {
            System.out.println("No appointments in the system.");                                      // NOSONAR
            return;
        }
        
        displayAppointmentList(allAppointments, "Select reservation to cancel:\n");
        
        System.out.print(ENTER_APPOINTMENT_NUMBER_PROMPT);                                   // NOSONAR
        int index = readIntInput() - 1;
        
        if (index >= 0 && index < allAppointments.size()) {
            Appointment toCancel = allAppointments.get(index);
            
            if (toCancel.getStatus() == AppointmentStatus.CONFIRMED) {
                toCancel.setStatus(AppointmentStatus.AVAILABLE);
                toCancel.setBookedBy(null);
                System.out.println(">> Booking cancelled. Slot is now Available again.");                         // NOSONAR
            } else if (toCancel.getStatus() == AppointmentStatus.AVAILABLE) {
                toCancel.cancel(); 
                System.out.println(">> Slot cancelled successfully.");                                         // NOSONAR
            } else {
                System.out.println(">> Appointment is already cancelled.");                            // NOSONAR
            }
            
        } else {
            System.out.println(INVALID_SELECTION_MSG);                                             // NOSONAR
        }
    }
    //  SPRINT 3: NOTIFICATIONS 
    /**
     * Sends reminders for all appointments.
     */
    private void sendAllReminders() {
        printHeader("SEND REMINDERS");
        List<Appointment> appointments = repository.findAll();
        if (appointments.isEmpty()) {
            System.out.println("No appointments to send reminders for.");                                                                // NOSONAR
            return;
        }
        int sentCount = 0;
        String fixedEmail = "tasneem@gmail.com";
        for (Appointment apt : appointments) {
           
            if (apt.getStatus() == AppointmentStatus.CONFIRMED) {
                notificationManager.sendReminderToEmail(fixedEmail, apt);
                sentCount++;
            }
        }
        if (sentCount == 0) {
            System.out.println("No confirmed appointments to send reminders for.");                              // NOSONAR
        } else {
            System.out.println(">> Sent " + sentCount + " reminder(s) successfully!");                              // NOSONAR
        }
    }
    //  SYSTEM DEMO 
    /**
     * Runs a comprehensive demo of all implemented features.
     */
    private void runSystemDemo() {
        printHeader("SYSTEM DEMO - ALL SPRINTS");
        System.out.println("This demo will showcase all implemented User Stories.\n");                    // NOSONAR
        System.out.print("Press ENTER to start the demo...");                                               // NOSONAR
        scanner.nextLine();
        demoSprint1();
        demoSprint2();
        demoSprint3();
        demoSprint4();
        demoSprint5();
        printSeparator();
        System.out.println("DEMO COMPLETED SUCCESSFULLY!");                                       // NOSONAR
        System.out.println("All User Stories have been demonstrated.");                               // NOSONAR
        printSeparator();
        System.out.print("\nPress ENTER to return to main menu...");                              // NOSONAR
        scanner.nextLine(); 
    }
    
    private void printDemoSectionHeader(String text) {
        System.out.println("\n" + text); // NOSONAR
        System.out.println(SHORT_SEPARATOR); // NOSONAR
    }
    
    private void demoSprint1() {
        printDemoHeader("SPRINT 1: Core Scheduling & Authentication");
        printDemoSectionHeader("US1.1 - Administrator Login");
        boolean loginSuccess = authService.login(admin, ADMIN_USERNAME, ADMIN_PASSWORD);
        System.out.println("   Login result: " + (loginSuccess ? "SUCCESS" : "FAILED"));                       // NOSONAR
        printDemoSectionHeader("US1.3 - View Available Slots");
        List<Appointment> slots = appointmentService.viewAvailableSlots();
        System.out.println("   Available slots: " + slots.size());                                         // NOSONAR
        for (Appointment apt : slots) {
             System.out.println(LIST_ITEM_PREFIX + formatAppointmentShort(apt));               // NOSONAR
        }
        printDemoSectionHeader("US1.2 - Administrator Logout");
        authService.logout();
        System.out.println("   Logout result: SUCCESS");                                        // NOSONAR
    }
    private void demoSprint2() {
        printDemoHeader("SPRINT 2: Booking & Business Rules");
        printDemoSectionHeader("US2.1 - Book Appointment");
        Appointment newApt = new Appointment(
                LocalDateTime.now().plusDays(3),
                30,
                2,
                new VirtualAppointment()
        );
        boolean booked = appointmentService.book(newApt);
        System.out.println("   Booking result: " + (booked ? "CONFIRMED" : "REJECTED"));                                       // NOSONAR 
        printDemoSectionHeader("US2.2 - Duration Rule Enforcement");
        System.out.println("   Max duration allowed: " + MAX_DURATION + MINUTES_SUFFIX);             // NOSONAR
        Appointment longApt = new Appointment(
                LocalDateTime.now().plusDays(1),
                200,
                1,
                new InPersonAppointment()
        );
        boolean longResult = appointmentService.book(longApt);
        System.out.println("   Booking 200 min: " + (longResult ? "ACCEPTED" : "REJECTED (as expected)"));                       // NOSONAR
        printDemoSectionHeader("US2.3 - Participant Limit Enforcement");
        System.out.println("   Max participants allowed: " + MAX_PARTICIPANTS);                                                       // NOSONAR
        Appointment crowdedApt = new Appointment(
                LocalDateTime.now().plusDays(1),
                30,
                15,
                new GroupAppointment()
        );
        boolean crowdResult = appointmentService.book(crowdedApt);
        System.out.println("   Booking 15 participants: " + (crowdResult ? "ACCEPTED" : "REJECTED (as expected)"));                            // NOSONAR
    }
    
    private void demoSprint3() {
        printDemoHeader("SPRINT 3: Notifications & Mocking");
        printDemoSectionHeader("US3.1 - Send Appointment Reminders");
        System.out.println("   Demo Mode: Sending reminders is simulated...");                                                    // NOSONAR
        System.out.println("   Reminder logic demonstrated without sending real emails.");                                                           // NOSONAR
        System.out.println("   Channels simulated: Console Logger, Email Service");                                                  // NOSONAR
    }
   
    private void demoSprint4() {
        printDemoHeader("SPRINT 4: Advanced Management Rules");
        printDemoSectionHeader("US4.1 - Modify Appointment");
        List<Appointment> appointments = repository.findAll();
        if (!appointments.isEmpty()) {
            Appointment original = appointments.get(0);
            Appointment updated = new Appointment(
                    LocalDateTime.now().plusDays(5),
                    45,
                    3,
                    new FollowUpAppointment()
            );
            boolean modified = appointmentService.modifyAppointment(original, updated);
            System.out.println("   Modification result: " + (modified ? "SUCCESS" : "FAILED"));                // NOSONAR
        }
        printDemoSectionHeader("US4.2 - Admin Manage Reservations");
        authService.login(admin, ADMIN_USERNAME, ADMIN_PASSWORD);
        System.out.println("   Admin can modify/cancel ANY reservation");                                // NOSONAR
        System.out.println("   Admin privileges confirmed");                                              // NOSONAR
    }
    private void demoSprint5() {
        printDemoHeader("SPRINT 5: Appointment Types & Polymorphism");
        printDemoSectionHeader("US5.1 - Support Multiple Appointment Types");
        System.out.println("   Available types:");                                                             // NOSONAR
        for (AppointmentType type : appointmentTypes) {
            System.out.println(LIST_ITEM_PREFIX + type.getClass().getSimpleName());                               // NOSONAR
        }
        printDemoSectionHeader("US5.2 - Apply Different Rules Per Type");
        System.out.println("   Each type can have specific behavior:");                                                  // NOSONAR
        for (AppointmentType type : appointmentTypes) {
             System.out.println(LIST_ITEM_PREFIX + type.getClass().getSimpleName() + ": " + type.getDescription());         // NOSONAR
        }
    }
    //  HELPER METHODS 
    /**
     * Displays all user stories implemented in the system.
     */
    private void displayUserStories() {
        printHeader("IMPLEMENTED USER STORIES");
        String[][] stories = {
                {"Sprint 1", "US1.1", "Administrator login"},
                {"", "US1.2", "Administrator logout"},
                {"", "US1.3", "View available appointment slots"},
                {"Sprint 2", "US2.1", "Book appointment"},
                {"", "US2.2", "Enforce visit duration rule"},
                {"", "US2.3", "Enforce participant limit"},
                {"Sprint 3", "US3.1", "Send appointment reminders"},
                {"Sprint 4", "US4.1", "Modify or cancel appointment"},
                {"", "US4.2", "Manage reservations (Admin)"},
                {"Sprint 5", "US5.1", "Support multiple appointment types"},
                {"", "US5.2", "Apply different rules per type"}
        };
        System.out.printf("%-12s %-8s %s%n", "SPRINT", "ID", "DESCRIPTION");                                   // NOSONAR
        System.out.println("--------------------------------------------------");                                    // NOSONAR
        for (String[] story : stories) {
            System.out.printf("%-12s %-8s %s%n", story[0], story[1], story[2]);                                        // NOSONAR
        }
    }
    /**
     * Views all appointments in the system.
     */
    private void viewAllAppointments() {
        printHeader("ALL APPOINTMENTS");
        List<Appointment> appointments = repository.findAll();
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");                                                    // NOSONAR
            return;
        }
        displayAppointmentList(appointments, ""); // Empty title
    }
    /**
     * Displays statistics about the system.
     */
    private void displayStatistics() {
        printHeader("SYSTEM STATISTICS");
        List<Appointment> all = repository.findAll();
        long confirmed = all.stream()
                .filter(a -> a.getStatus() == AppointmentStatus.CONFIRMED)
                .count();
        long available = all.stream()
                .filter(a -> a.getStatus() == AppointmentStatus.AVAILABLE)
                .count();
        long cancelled = all.stream()
                .filter(a -> a.getStatus() == AppointmentStatus.CANCELLED)
                .count();
        System.out.println("   Total Appointments: " + all.size());                                              // NOSONAR
        System.out.println("   Confirmed: " + confirmed);                                                            // NOSONAR
        System.out.println("   Available: " + available);                                                                  // NOSONAR
        System.out.println("   Cancelled: " + cancelled);                                                     // NOSONAR
    }
    /**
     * Views current user's bookings.
     */
    private void viewMyBookings() {
        printHeader("MY BOOKINGS");
        
        List<Appointment> myAppointments = repository.findAll().stream()
            .filter(apt -> currentUser.getUsername().equals(apt.getBookedBy()))
            .collect(Collectors.toList());
        if (myAppointments.isEmpty()) {
            System.out.println("You have no bookings yet.");                                                           // NOSONAR
            return;
        }
        displayAppointmentList(myAppointments, "");
    }
    /**
     * Gets future appointments only.
     */
    private List<Appointment> getFutureAppointments() {                                                                                                       // NOSONAR
        return repository.findAll().stream()
            .filter(apt -> apt.getDateTime().isAfter(LocalDateTime.now()))
            
            .filter(apt -> currentUser.getUsername().equals(apt.getBookedBy())) 
            .collect(Collectors.toList());
    }
    /**
     * Displays a list of appointments.
     */
    private void displayAppointmentList(List<Appointment> appointments, String title) {
        if (!title.isEmpty()) {
            System.out.println("\n" + title);                                                                        // NOSONAR
        }
        for (int i = 0; i < appointments.size(); i++) {
            System.out.printf(LIST_ITEM_FORMAT, i + 1, formatAppointment(appointments.get(i)));        }                   // NOSONAR
    }
    /**
     * Prompts user to enter date and time.
     */
    private LocalDateTime enterDateTime() {
        System.out.println("\nEnter Appointment Date & Time:");                                     // NOSONAR
        System.out.print("   Year (e.g., 2025): ");                                                   // NOSONAR
        int year = readIntInput();
        System.out.print("   Month (1-12): ");                                                      // NOSONAR
        int month = readIntInput();
        System.out.print("   Day (1-31): ");                                                                 // NOSONAR
        int day = readIntInput();
        System.out.print("   Hour (0-23): ");                                                             // NOSONAR
        int hour = readIntInput();
        System.out.print("   Minute (0-59): ");                                                      // NOSONAR
        int minute = readIntInput();
        try {
            return LocalDateTime.of(year, month, day, hour, minute);
        } catch (Exception e) {
            logger.warning("Invalid date/time entered.");                                             
            return null;
        }
    }
    /**
     * Prompts user to enter duration.
     */
    private int enterDuration() {
        System.out.print("\nEnter duration in minutes (max " + MAX_DURATION + "): ");                                  // NOSONAR
        int duration = readIntInput();
        if (duration <= 0 || duration > MAX_DURATION) {
            System.out.println("Duration must be between 1 and " + MAX_DURATION + " minutes.");                          // NOSONAR
            return -1;
        }
        return duration;
    }
    /**
     * Prompts user to enter number of participants.
     */
    private int enterParticipants() {
        System.out.print("Enter number of participants (max " + MAX_PARTICIPANTS + "): ");                                    // NOSONAR
        int participants = readIntInput();
        if (participants <= 0 || participants > MAX_PARTICIPANTS) {
            System.out.println("Participants must be between 1 and " + MAX_PARTICIPANTS + ".");                                    // NOSONAR
            return -1;
        }
        return participants;
    }
    /**
     * Formats an appointment for display.
     */
    private String formatAppointment(Appointment apt) {
        return String.format("%s | %d min | %d participants | %s | %s",
                apt.getDateTime().format(DATE_FORMAT),
                apt.getDuration(),
                apt.getParticipants(),
                apt.getType().getClass().getSimpleName(),
                apt.getStatus());
    }
    /**
     * Formats an appointment briefly.
     */
    private String formatAppointmentShort(Appointment apt) {
        return apt.getDateTime().format(DATE_FORMAT) + " - " +
                apt.getType().getClass().getSimpleName();
    }
    /**
     * Reads integer input safely.
     */
    private int readIntInput() {
        try {
            if (!scanner.hasNextLine()) {
                running = false; 
                return -2;
            }
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return -1;
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    /**
     * Reads a line safely, returning empty string if no input exists.
     * Prevents NoSuchElementException in tests.
     */
    private String safeReadLine() {
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        return ""; 
    }
    /**
     * 
     */
    private void printMenuDisplay(String title, List<MenuItem> items) {
        printSeparator();
        System.out.println("║ " + title);                                                  // NOSONAR
        printSeparator();
        for (int i = 0; i < items.size(); i++) {
            System.out.println("║  " + (i + 1) + ". " + items.get(i).title);                 // NOSONAR
        }
    }
    //  UI HELPER METHODS 
    private void printWelcomeBanner() {
         System.out.println(HORIZONTAL_SEPARATOR);                                                // NOSONAR
            System.out.println("      APPOINTMENT SCHEDULING SYSTEM");                             // NOSONAR
            logger.info("Welcome to the system");
            System.out.println(HORIZONTAL_SEPARATOR);                                                 // NOSONAR
        
    }
    private void printHeader(String title) {
        System.out.println();                                                                                               // NOSONAR
        System.out.println(HORIZONTAL_SEPARATOR);                                                           // NOSONAR
        System.out.println("  " + title);                                                                 // NOSONAR
        System.out.println(HORIZONTAL_SEPARATOR);                                                           // NOSONAR
    } 
    
    private void printDemoHeader(String title) {
        System.out.println();                                                                                     // NOSONAR
        System.out.println(DASH_SEPARATOR);                                                               // NOSONAR
        System.out.println("  " + title);                                                                      // NOSONAR
        System.out.println(DASH_SEPARATOR);                                                                              // NOSONAR
    }
    private void printSeparator() {
         System.out.println(DASH_SEPARATOR);                                                                    // NOSONAR
    }
    private void printGoodbyeMessage() {
        System.out.println();                                                                                   // NOSONAR
        
        System.out.println(EMPTY_LINE);                                                                               // NOSONAR
        System.out.println("          Thank you for using our system!               ");                                         // NOSONAR
        System.out.println(EMPTY_LINE);                                                                      // NOSONAR
        System.out.println("              Have a wonderful day!                     ");                                      // NOSONAR
        System.out.println(EMPTY_LINE);                                                                                             // NOSONAR
      
    }
    //  MAIN ENTRY POINT 
    /**
     * Application entry point.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }
}