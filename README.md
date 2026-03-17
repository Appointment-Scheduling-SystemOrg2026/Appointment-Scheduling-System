🗓️ Appointment Scheduling System

📋 Project Overview

A comprehensive Appointment Scheduling System built using Java 8+, Maven, JUnit 5, JaCoCo, and Mockito. The system implements all required User Stories across 5 Agile Sprints.


🎯 Design Patterns

1. Strategy Pattern

Where: Appointment rule enforcement (duration, capacity, type-specific rules)

Why: Allows adding new rules without modifying core booking logic
java

interface BookingRuleStrategy {
    boolean isValid(Appointment appointment);
}

Implementations:

DurationRuleStrategy - US2.2

ParticipantLimitStrategy - US2.3

2. Observer Pattern

Where: Notifications (appointment reminders, updates)

Why: Allows multiple notification channels (email, calendar, SMS)
java

interface Observer {
    void notify(User user, String message);
}

Implementations:

NotificationService - Console logging

EmailNotificationService - Email notifications

MockNotificationService - Testing

📊 User Stories Implementation

Sprint 1: Core Scheduling & Authentication ✅

ID

User Story

Status

US1.1	Administrator login	✅

US1.2	Administrator logout	✅

US1.3	View available appointment slots	✅


Sprint 2: Booking & Business Rules ✅

ID

User Story

Status

US2.1	Book appointment	✅

US2.2	Enforce visit duration rule	✅

US2.3	Enforce participant limit	✅

Sprint 3: Notifications & Mocking ✅

ID

User Story

Status

US3.1	Send appointment reminders	✅

Sprint 4: Advanced Management Rules ✅

ID

User Story

Status

US4.1	Modify or cancel appointment	✅

US4.2	Manage reservations (Admin)	✅

Sprint 5: Appointment Types & Polymorphism ✅

ID

User Story

Status

US5.1	Support multiple appointment types	✅

US5.2	Apply different rules per type	✅

🚀 How to Run

Prerequisites

Java 8 or higher

Maven 3.x

Commands



# Compile

mvn compile

# Run tests

mvn test

# Generate coverage report

mvn jacoco:report

# Run application



🎬 Demo Mode
The system includes a comprehensive demo mode that showcases all User Stories:

Run the application

Select option 3. System Demo

Watch all Sprints demonstrated

📝 Appointment Types

Type

Description

Max Duration

Max Participants

Urgent	Emergency appointment	30 min	1

Follow-up	Post-treatment check	45 min	2

Assessment	Evaluation session	90 min	3

Virtual	Video conference	45 min	5

In-Person	Face-to-face	60 min	4

Individual	One-on-one	60 min	1

Group	Multiple participants	120 min	10


✅ Testing

JUnit 5 for unit testing

Mockito for mocking (notifications, time handling)

JaCoCo for code coverage

📄 Documentation

All classes, methods, and fields include Javadoc comments using standard syntax:

@param - Parameter descriptions

@return - Return value descriptions

@author - Author information

@version - Version information

Built with ❤️ using Java, Maven, and Design Patterns