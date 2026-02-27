package com.mycontacts;

import com.mycontacts.auth.BasicAuth;
import com.mycontacts.auth.SessionManager;
import com.mycontacts.model.User;
import com.mycontacts.service.UserRegistrationService;

import java.util.Scanner;

public class Main {
    private static UserRegistrationService registrationService = new UserRegistrationService();
    private static BasicAuth auth = new BasicAuth(registrationService);
    private static SessionManager session = SessionManager.getInstance();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Welcome to MyContacts App ===");
        boolean running = true;
        while (running) {
            System.out.println("\n--- Main Menu ---");
            if (session.isLoggedIn()) {
                System.out.println("Logged in as: " + session.getCurrentUser().getName());
                System.out.println("1. Logout");
            } else {
                System.out.println("1. Register");
                System.out.println("2. Login");
            }
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            String choice = scanner.nextLine();

            if (session.isLoggedIn()) {
                switch (choice) {
                    case "1":
                        auth.logout(session.getCurrentUser().getUserId());
                        session.clearSession();
                        break;
                    case "0":
                        running = false;
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } else {
                switch (choice) {
                    case "1":
                        registerUser();
                        break;
                    case "2":
                        loginUser();
                        break;
                    case "0":
                        running = false;
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
        }
    }

    private static void registerUser() {
        System.out.println("\n--- User Registration ---");
        try {
            System.out.print("Enter name: ");
            String name = scanner.nextLine();

            System.out.print("Enter email: ");
            String email = scanner.nextLine();

            System.out.print("Enter password (8+ chars, 1 uppercase, 1 digit): ");
            String password = scanner.nextLine();

            System.out.print("Account type (free/premium): ");
            String type = scanner.nextLine();

            User user = registrationService.registerUser(name, email, password, type);
            System.out.println("\n✓ Registration successful!");
            System.out.println(user);

        } catch (IllegalArgumentException e) {
            System.out.println("✗ Registration failed: " + e.getMessage());
        }
    }

    private static void loginUser() {
        System.out.println("\n--- Login ---");
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        auth.login(email, password).ifPresent(user -> {
            session.setCurrentUser(user);
        });
    }
}