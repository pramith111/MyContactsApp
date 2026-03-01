package com.mycontacts;

import com.mycontacts.auth.BasicAuth;
import com.mycontacts.auth.SessionManager;
import com.mycontacts.model.Contact;
import com.mycontacts.model.Organization;
import com.mycontacts.model.Person;
import com.mycontacts.model.User;
import com.mycontacts.pattern.BasicContactDisplay;
import com.mycontacts.pattern.ContactBuilder;
import com.mycontacts.pattern.ContactDisplay;
import com.mycontacts.pattern.ContactMemento;
import com.mycontacts.pattern.EditContactCommand;
import com.mycontacts.pattern.MaskedEmailContactDisplay;
import com.mycontacts.pattern.UpdateEmailCommand;
import com.mycontacts.pattern.UpdateNameCommand;
import com.mycontacts.pattern.UpdatePasswordCommand;
import com.mycontacts.pattern.UpperCaseContactDisplay;
import com.mycontacts.service.ContactService;
import com.mycontacts.service.EditContactService;
import com.mycontacts.service.ProfileService;
import com.mycontacts.service.UserRegistrationService;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static UserRegistrationService registrationService = new UserRegistrationService();
    private static BasicAuth auth = new BasicAuth(registrationService);
    private static SessionManager session = SessionManager.getInstance();
    private static ProfileService profileService = new ProfileService();
    private static ContactService contactService = new ContactService();
    private static EditContactService editContactService = new EditContactService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Welcome to MyContacts App ===");
        boolean running = true;
        while (running) {
            System.out.println("\n--- Main Menu ---");
            if (session.isLoggedIn()) {
                System.out.println("Logged in as: " + session.getCurrentUser().getName());
                System.out.println("1. Logout");
                System.out.println("2. Manage Profile");
                System.out.println("3. Add Contact");
                System.out.println("4. View All Contacts");
                System.out.println("5. View Contact Details");
                System.out.println("6. Edit Contact");
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
                    case "2":
                        manageProfile();
                        break;
                    case "3":
                        addContact();
                        break;
                    case "4":
                        viewAllContacts();
                        break;
                    case "5":
                        viewContactDetails();
                        break;
                    case "6":
                        editContact();
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
        auth.login(email, password).ifPresent(user -> session.setCurrentUser(user));
    }

    private static void manageProfile() {
        System.out.println("\n--- Manage Profile ---");
        User user = session.getCurrentUser();
        System.out.println("Current Profile: " + user);
        boolean back = false;
        while (!back) {
            System.out.println("\n1. Update Name");
            System.out.println("2. Update Email");
            System.out.println("3. Update Password");
            System.out.println("4. Undo Last Change");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.print("Enter new name: ");
                    profileService.executeCommand(new UpdateNameCommand(user, scanner.nextLine()));
                    break;
                case "2":
                    System.out.print("Enter new email: ");
                    profileService.executeCommand(new UpdateEmailCommand(user, scanner.nextLine()));
                    break;
                case "3":
                    System.out.print("Enter new password: ");
                    profileService.executeCommand(new UpdatePasswordCommand(user, scanner.nextLine()));
                    break;
                case "4":
                    profileService.undo();
                    break;
                case "0":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addContact() {
        System.out.println("\n--- Add Contact ---");
        System.out.print("Contact type (person/organization): ");
        String type = scanner.nextLine();
        try {
            ContactBuilder builder = new ContactBuilder().setType(type);
            if (type.equalsIgnoreCase("person")) {
                System.out.print("First name: ");
                builder.setFirstName(scanner.nextLine());
                System.out.print("Last name: ");
                builder.setLastName(scanner.nextLine());
                System.out.print("Address (optional, press Enter to skip): ");
                String address = scanner.nextLine();
                if (!address.isEmpty()) builder.setAddress(address);
            } else if (type.equalsIgnoreCase("organization")) {
                System.out.print("Company name: ");
                builder.setCompanyName(scanner.nextLine());
                System.out.print("Industry (optional, press Enter to skip): ");
                String industry = scanner.nextLine();
                if (!industry.isEmpty()) builder.setIndustry(industry);
                System.out.print("Website (optional, press Enter to skip): ");
                String website = scanner.nextLine();
                if (!website.isEmpty()) builder.setWebsite(website);
            }
            System.out.print("Phone number (optional, press Enter to skip): ");
            String phone = scanner.nextLine();
            if (!phone.isEmpty()) {
                System.out.print("Phone type (Mobile/Home/Work): ");
                builder.setPhoneNumber(phone, scanner.nextLine());
            }
            System.out.print("Email (optional, press Enter to skip): ");
            String email = scanner.nextLine();
            if (!email.isEmpty()) {
                System.out.print("Email type (Personal/Work): ");
                builder.setEmail(email, scanner.nextLine());
            }
            Contact contact = builder.build();
            contactService.addContact(contact);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("✗ Failed to add contact: " + e.getMessage());
        }
    }

    private static void viewAllContacts() {
        System.out.println("\n--- All Contacts (" + contactService.getTotalContacts() + ") ---");
        List<Contact> contacts = contactService.getAllContacts();
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            for (int i = 0; i < contacts.size(); i++) {
                System.out.println("[" + i + "] " + contacts.get(i).getDisplayName()
                        + " (" + contacts.get(i).getContactType() + ")");
            }
        }
    }

    private static void viewContactDetails() {
        System.out.println("\n--- View Contact Details ---");
        List<Contact> contacts = contactService.getAllContacts();
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }
        viewAllContacts();
        System.out.print("Enter contact number: ");
        try {
            int index = Integer.parseInt(scanner.nextLine());
            Contact contact = contacts.get(index);
            System.out.println("\nDisplay format:");
            System.out.println("1. Normal");
            System.out.println("2. Uppercase");
            System.out.println("3. Masked Email");
            System.out.println("4. Uppercase + Masked Email");
            System.out.print("Choose: ");
            String format = scanner.nextLine();
            ContactDisplay display;
            switch (format) {
                case "2":
                    display = new UpperCaseContactDisplay(new BasicContactDisplay());
                    break;
                case "3":
                    display = new MaskedEmailContactDisplay(new BasicContactDisplay());
                    break;
                case "4":
                    display = new UpperCaseContactDisplay(
                            new MaskedEmailContactDisplay(new BasicContactDisplay()));
                    break;
                default:
                    display = new BasicContactDisplay();
            }
            System.out.println("\n" + display.display(contact));
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println("✗ Invalid selection.");
        }
    }

    private static void editContact() {
        System.out.println("\n--- Edit Contact ---");
        List<Contact> contacts = contactService.getAllContacts();
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }
        viewAllContacts();
        System.out.print("Enter contact number to edit: ");
        try {
            int index = Integer.parseInt(scanner.nextLine());
            Contact contact = contacts.get(index);

            boolean back = false;
            while (!back) {
                System.out.println("\n1. Edit Contact Info");
                System.out.println("2. Undo Last Edit");
                System.out.println("3. Redo Last Edit");
                System.out.println("0. Back");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        ContactMemento newState = null;
                        if (contact instanceof Person) {
                            Person p = (Person) contact;
                            System.out.print("First name (" + p.getFirstName() + "): ");
                            String fn = scanner.nextLine();
                            System.out.print("Last name (" + p.getLastName() + "): ");
                            String ln = scanner.nextLine();
                            System.out.print("Address (" + p.getAddress() + "): ");
                            String addr = scanner.nextLine();
                            newState = new ContactMemento(
                                    fn.isEmpty() ? p.getFirstName() : fn,
                                    ln.isEmpty() ? p.getLastName() : ln,
                                    null,
                                    addr.isEmpty() ? p.getAddress() : addr,
                                    null, null,
                                    p.getPhoneNumbers(), p.getEmails());
                        } else if (contact instanceof Organization) {
                            Organization o = (Organization) contact;
                            System.out.print("Company name (" + o.getCompanyName() + "): ");
                            String cn = scanner.nextLine();
                            System.out.print("Industry (" + o.getIndustry() + "): ");
                            String ind = scanner.nextLine();
                            System.out.print("Website (" + o.getWebsite() + "): ");
                            String web = scanner.nextLine();
                            newState = new ContactMemento(
                                    null, null,
                                    cn.isEmpty() ? o.getCompanyName() : cn,
                                    null,
                                    ind.isEmpty() ? o.getIndustry() : ind,
                                    web.isEmpty() ? o.getWebsite() : web,
                                    o.getPhoneNumbers(), o.getEmails());
                        }
                        if (newState != null) {
                            editContactService.executeEdit(
                                    new EditContactCommand(contact, newState));
                        }
                        break;
                    case "2":
                        editContactService.undo();
                        break;
                    case "3":
                        editContactService.redo();
                        break;
                    case "0":
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println("✗ Invalid selection.");
        }
    }
}