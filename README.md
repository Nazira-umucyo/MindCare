# MindCare — Youth Mental Health System

A JavaFX Maven desktop application for managing mental health support for youth.

## About
MindCare is a role-based mental health management system that connects patients with therapists. It was built as part of a Java backend development course to demonstrate OOP, Collections, Exception Handling, File I/O, and JavaFX UI integration.

## Features

### Admin
- Login with secure credentials
- Add and remove patients
- Add and remove therapists
- View all patients and therapists in a table
- Search patients by name
- Sort patients alphabetically

### Patient
- Register and login
- View personal profile
- Book an available therapist
- View session history and appointment date

### Therapist
- Register and login
- View booked patients
- Set appointment dates for patients

## Technologies Used
- Java 17+
- JavaFX 21
- Maven
- File I/O (BufferedReader/BufferedWriter)

## OOP Concepts Demonstrated
- **Abstraction** — Abstract `Person` class
- **Inheritance** — `Patient`, `Therapist`, `Admin` extend `Person`
- **Polymorphism** — `getRole()` overridden in each class
- **Encapsulation** — Private fields with getters/setters
- **Generics** — `Repository<T>` for type-safe data storage
- **Collections** — `ArrayList` via `Repository<T>`
- **Exception Handling** — Custom exceptions for invalid data
- **File I/O** — Data persisted in `.txt` files

## How to Run
1. Clone the repository
2. Open in IntelliJ IDEA
3. Make sure Java 17+ and Maven are configured
4. Run `HelloApplication.java`

## Default Admin Login
- Email: `admin@mindcare.com`
- Password: `admin123`

## Author
Nazira — cohort#16
