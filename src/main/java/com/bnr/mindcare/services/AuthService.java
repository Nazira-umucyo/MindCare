package com.bnr.mindcare.services;

import java.io.*;

public class AuthService {

    private static final String USERS_FILE = "users.txt";

    public void registerUser(String role, String name, String email, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            writer.write(role + "," + name + "," + email + "," + password);
            writer.newLine();
            System.out.println("User registered: " + name);
        } catch (IOException e) {
            System.out.println("Error registering user: " + e.getMessage());
        }
    }

    public String login(String email, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[2].equals(email) && parts[3].equals(password)) {
                    return parts[0];
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading users file: " + e.getMessage());
        }
        return null;
    }

    public String getUserName(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[2].equals(email)) {
                    return parts[1];
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading users file: " + e.getMessage());
        }
        return null;
    }

    public void createAdminIfNotExists() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Admin")) {
                    return;
                }
            }
        } catch (IOException e) {
        }
        registerUser("Admin", "Admin", "admin@mindcare.com", "admin123");
        System.out.println("Default admin created: admin@mindcare.com / admin123");
    }

    public String checkIfCreatedByAdmin(String email, String role) {
        try (BufferedReader reader = new BufferedReader(new FileReader("registered_emails.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[1].equals(email) && parts[0].equals(role)) {
                    return parts[0];
                }
            }
        } catch (IOException e) {
            System.out.println("No registered emails file found");
        }
        return null;
    }

    public void saveEmailForRegistration(String role, String email) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("registered_emails.txt", true))) {
            writer.write(role + "," + email);
            writer.newLine();
            System.out.println("Saved to: " + new File("registered_emails.txt").getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error saving email: " + e.getMessage());
        }
    }
}