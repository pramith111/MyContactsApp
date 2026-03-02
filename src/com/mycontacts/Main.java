package com.mycontacts;

import com.mycontacts.auth.BasicAuth;
import com.mycontacts.auth.SessionManager;
import com.mycontacts.model.Contact;
import com.mycontacts.model.ContactGroup;
import com.mycontacts.model.Organization;
import com.mycontacts.model.Person;
import com.mycontacts.model.Tag;
import com.mycontacts.model.User;
import com.mycontacts.pattern.BasicContactDisplay;
import com.mycontacts.pattern.ContactBuilder;
import com.mycontacts.pattern.ContactDeletionLogger;
import com.mycontacts.pattern.ContactDisplay;
import com.mycontacts.pattern.ContactFilter;
import com.mycontacts.pattern.ContactMemento;
import com.mycontacts.pattern.ContactSortStrategy;
import com.mycontacts.pattern.ContactTypeFilter;
import com.mycontacts.pattern.EditContactCommand;
import com.mycontacts.pattern.EmailSearchSpecification;
import com.mycontacts.pattern.MaskedEmailContactDisplay;
import com.mycontacts.pattern.NameSearchSpecification;
import com.mycontacts.pattern.OrSearchSpecification;
import com.mycontacts.pattern.PhoneSearchSpecification;
import com.mycontacts.pattern.SearchSpecification;
import com.mycontacts.pattern.SortByDateAdded;
import com.mycontacts.pattern.SortByNameAsc;
import com.mycontacts.pattern.SortByNameDesc;
import com.mycontacts.pattern.TagChangeLogger;
import com.mycontacts.pattern.TagFactory;
import com.mycontacts.pattern.UpdateEmailCommand;
import com.mycontacts.pattern.UpdateNameCommand;
import com.mycontacts.pattern.UpdatePasswordCommand;
import com.mycontacts.pattern.UpperCaseContactDisplay;
import com.mycontacts.service.ContactService;
import com.mycontacts.service.EditContactService;
import com.mycontacts.service.FilterSortService;
import com.mycontacts.service.GroupService;
import com.mycontacts.service.ProfileService;
import com.mycontacts.service.SearchService;
import com.mycontacts.service.TagService;
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
    private static ContactDeletionLogger deletionLogger = new ContactDeletionLogger();
    private static GroupService groupService = new GroupService();
    private static SearchService searchService = new SearchService();
    private static FilterSortService filterSortService = new FilterSortService();
    private static TagService tagService = new TagService();
    private static TagChangeLogger tagChangeLogger = new TagChangeLogger();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        contactService.addObserver(deletionLogger);
        tagService.addObserver(tagChangeLogger);
        System.out.println("=== Welcome to MyContacts App ===");
        boolean running = true;
        while (running) {
            System.out.println("\n--- Main Menu ---");
            if (session.isLoggedIn()) {
                System.out.println("Logged in as: " + session.getCurrentUser().getName());
                System.out.println("1.  Logout");
                System.out.println("2.  Manage Profile");
                System.out.println("3.  Add Contact");
                System.out.println("4.  View All Contacts");
                System.out.println("5.  View Contact Details");
                System.out.println("6.  Edit Contact");
                System.out.println("7.  Delete Contact");
                System.out.println("8.  View Deleted Contacts");
                System.out.println("9.  View Deletion Log");
                System.out.println("10. Manage Groups");
                System.out.println("11. Search Contacts");
                System.out.println("12. Filter & Sort Contacts");
                System.out.println("13. Manage Tags");
                System.out.println("14. Find Contacts by Tag");
                System.out.println("15. View Tag Change Log");
            } else {
                System.out.println("1. Register");
                System.out.println("2. Login");
            }
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            String choice = scanner.nextLine();

            if (session.isLoggedIn()) {
                switch (choice) {
                    case "1": auth.logout(session.getCurrentUser().getUserId()); session.clearSession(); break;
                    case "2": manageProfile(); break;
                    case "3": addContact(); break;
                    case "4": viewAllContacts(); break;
                    case "5": viewContactDetails(); break;
                    case "6": editContact(); break;
                    case "7": deleteContact(); break;
                    case "8": viewDeletedContacts(); break;
                    case "9": deletionLogger.printLog(); break;
                    case "10": manageGroups(); break;
                    case "11": searchContacts(); break;
                    case "12": filterAndSort(); break;
                    case "13": manageTags(); break;
                    case "14": findContactsByTag(); break;
                    case "15": tagChangeLogger.printLog(); break;
                    case "0": running = false; System.out.println("Goodbye!"); break;
                    default: System.out.println("Invalid choice.");
                }
            } else {
                switch (choice) {
                    case "1": registerUser(); break;
                    case "2": loginUser(); break;
                    case "0": running = false; System.out.println("Goodbye!"); break;
                    default: System.out.println("Invalid choice.");
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
            System.out.println("\n1. Update Name  2. Update Email  3. Update Password  4. Undo  0. Back");
            System.out.print("Choose: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1": System.out.print("New name: "); profileService.executeCommand(new UpdateNameCommand(user, scanner.nextLine())); break;
                case "2": System.out.print("New email: "); profileService.executeCommand(new UpdateEmailCommand(user, scanner.nextLine())); break;
                case "3": System.out.print("New password: "); profileService.executeCommand(new UpdatePasswordCommand(user, scanner.nextLine())); break;
                case "4": profileService.undo(); break;
                case "0": back = true; break;
                default: System.out.println("Invalid choice.");
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
                System.out.print("First name: "); builder.setFirstName(scanner.nextLine());
                System.out.print("Last name: "); builder.setLastName(scanner.nextLine());
                System.out.print("Address (optional): "); String address = scanner.nextLine();
                if (!address.isEmpty()) builder.setAddress(address);
            } else if (type.equalsIgnoreCase("organization")) {
                System.out.print("Company name: "); builder.setCompanyName(scanner.nextLine());
                System.out.print("Industry (optional): "); String industry = scanner.nextLine();
                if (!industry.isEmpty()) builder.setIndustry(industry);
                System.out.print("Website (optional): "); String website = scanner.nextLine();
                if (!website.isEmpty()) builder.setWebsite(website);
            }
            System.out.print("Phone (optional): "); String phone = scanner.nextLine();
            if (!phone.isEmpty()) { System.out.print("Phone type: "); builder.setPhoneNumber(phone, scanner.nextLine()); }
            System.out.print("Email (optional): "); String email = scanner.nextLine();
            if (!email.isEmpty()) { System.out.print("Email type: "); builder.setEmail(email, scanner.nextLine()); }
            contactService.addContact(builder.build());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("✗ Failed: " + e.getMessage());
        }
    }

    private static void viewAllContacts() {
        System.out.println("\n--- All Contacts (" + contactService.getTotalContacts() + ") ---");
        List<Contact> contacts = contactService.getAllContacts();
        if (contacts.isEmpty()) { System.out.println("No contacts found."); return; }
        for (int i = 0; i < contacts.size(); i++) {
            System.out.println("[" + i + "] " + contacts.get(i).getDisplayName() + " (" + contacts.get(i).getContactType() + ")");
        }
    }

    private static void viewContactDetails() {
        System.out.println("\n--- View Contact Details ---");
        List<Contact> contacts = contactService.getAllContacts();
        if (contacts.isEmpty()) { System.out.println("No contacts found."); return; }
        viewAllContacts();
        System.out.print("Enter contact number: ");
        try {
            int index = Integer.parseInt(scanner.nextLine());
            Contact contact = contacts.get(index);
            System.out.println("Format: 1.Normal 2.Uppercase 3.Masked Email 4.Both");
            System.out.print("Choose: ");
            String format = scanner.nextLine();
            ContactDisplay display;
            switch (format) {
                case "2": display = new UpperCaseContactDisplay(new BasicContactDisplay()); break;
                case "3": display = new MaskedEmailContactDisplay(new BasicContactDisplay()); break;
                case "4": display = new UpperCaseContactDisplay(new MaskedEmailContactDisplay(new BasicContactDisplay())); break;
                default: display = new BasicContactDisplay();
            }
            System.out.println("\n" + display.display(contact));
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println("✗ Invalid selection.");
        }
    }

    private static void editContact() {
        System.out.println("\n--- Edit Contact ---");
        List<Contact> contacts = contactService.getAllContacts();
        if (contacts.isEmpty()) { System.out.println("No contacts found."); return; }
        viewAllContacts();
        System.out.print("Enter contact number: ");
        try {
            int index = Integer.parseInt(scanner.nextLine());
            Contact contact = contacts.get(index);
            boolean back = false;
            while (!back) {
                System.out.println("\n1. Edit  2. Undo  3. Redo  0. Back");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        ContactMemento newState = null;
                        if (contact instanceof Person) {
                            Person p = (Person) contact;
                            System.out.print("First name (" + p.getFirstName() + "): "); String fn = scanner.nextLine();
                            System.out.print("Last name (" + p.getLastName() + "): "); String ln = scanner.nextLine();
                            System.out.print("Address (" + p.getAddress() + "): "); String addr = scanner.nextLine();
                            newState = new ContactMemento(fn.isEmpty()?p.getFirstName():fn, ln.isEmpty()?p.getLastName():ln, null, addr.isEmpty()?p.getAddress():addr, null, null, p.getPhoneNumbers(), p.getEmails());
                        } else if (contact instanceof Organization) {
                            Organization o = (Organization) contact;
                            System.out.print("Company (" + o.getCompanyName() + "): "); String cn = scanner.nextLine();
                            System.out.print("Industry (" + o.getIndustry() + "): "); String ind = scanner.nextLine();
                            System.out.print("Website (" + o.getWebsite() + "): "); String web = scanner.nextLine();
                            newState = new ContactMemento(null, null, cn.isEmpty()?o.getCompanyName():cn, null, ind.isEmpty()?o.getIndustry():ind, web.isEmpty()?o.getWebsite():web, o.getPhoneNumbers(), o.getEmails());
                        }
                        if (newState != null) editContactService.executeEdit(new EditContactCommand(contact, newState));
                        break;
                    case "2": editContactService.undo(); break;
                    case "3": editContactService.redo(); break;
                    case "0": back = true; break;
                    default: System.out.println("Invalid choice.");
                }
            }
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println("✗ Invalid selection.");
        }
    }

    private static void deleteContact() {
        System.out.println("\n--- Delete Contact ---");
        List<Contact> contacts = contactService.getAllContacts();
        if (contacts.isEmpty()) { System.out.println("No contacts found."); return; }
        viewAllContacts();
        System.out.print("Enter contact number: ");
        try {
            int index = Integer.parseInt(scanner.nextLine());
            Contact contact = contacts.get(index);
            System.out.println("1. Soft Delete  2. Hard Delete");
            System.out.print("Choose: ");
            String choice = scanner.nextLine();
            System.out.print("Are you sure? (yes/no): ");
            if (!scanner.nextLine().equalsIgnoreCase("yes")) { System.out.println("Cancelled."); return; }
            if (choice.equals("1")) contactService.softDelete(contact.getContactId());
            else if (choice.equals("2")) contactService.hardDelete(contact.getContactId());
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println("✗ Invalid selection.");
        }
    }

    private static void viewDeletedContacts() {
        System.out.println("\n--- Deleted Contacts ---");
        List<Contact> deleted = contactService.getDeletedContacts();
        if (deleted.isEmpty()) { System.out.println("No deleted contacts."); return; }
        for (int i = 0; i < deleted.size(); i++) System.out.println("[" + i + "] " + deleted.get(i).getDisplayName());
        System.out.println("1. Restore  0. Back");
        System.out.print("Choose: ");
        if (scanner.nextLine().equals("1")) {
            System.out.print("Enter contact number: ");
            try {
                int index = Integer.parseInt(scanner.nextLine());
                contactService.restore(deleted.get(index).getContactId());
            } catch (NumberFormatException | IndexOutOfBoundsException e) { System.out.println("✗ Invalid."); }
        }
    }

    private static void manageGroups() {
        System.out.println("\n--- Manage Groups ---");
        boolean back = false;
        while (!back) {
            System.out.println("\n1. Create Group  2. Add Contact to Group  3. View Groups  4. Bulk Add All  0. Back");
            System.out.print("Choose: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1": System.out.print("Group name: "); groupService.createGroup(scanner.nextLine()); break;
                case "2":
                    List<ContactGroup> groups = groupService.getAllGroups();
                    if (groups.isEmpty()) { System.out.println("No groups."); break; }
                    for (int i = 0; i < groups.size(); i++) System.out.println("[" + i + "] " + groups.get(i).getName());
                    System.out.print("Select group: ");
                    try {
                        int gi = Integer.parseInt(scanner.nextLine());
                        viewAllContacts();
                        System.out.print("Select contact: ");
                        int ci = Integer.parseInt(scanner.nextLine());
                        groups.get(gi).addContact(contactService.getAllContacts().get(ci));
                    } catch (NumberFormatException | IndexOutOfBoundsException e) { System.out.println("✗ Invalid."); }
                    break;
                case "3": groupService.displayAllGroups(); break;
                case "4":
                    List<ContactGroup> ag = groupService.getAllGroups();
                    if (ag.isEmpty()) { System.out.println("No groups."); break; }
                    for (int i = 0; i < ag.size(); i++) System.out.println("[" + i + "] " + ag.get(i).getName());
                    System.out.print("Select group: ");
                    try {
                        int gi = Integer.parseInt(scanner.nextLine());
                        groupService.bulkAddToGroup(ag.get(gi), contactService.getAllContacts());
                    } catch (NumberFormatException | IndexOutOfBoundsException e) { System.out.println("✗ Invalid."); }
                    break;
                case "0": back = true; break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void searchContacts() {
        System.out.println("\n--- Search Contacts ---");
        List<Contact> contacts = contactService.getAllContacts();
        if (contacts.isEmpty()) { System.out.println("No contacts found."); return; }
        System.out.println("1. By Name  2. By Phone  3. By Email  4. Name OR Email  5. Name AND Phone");
        System.out.print("Choose: ");
        String choice = scanner.nextLine();
        SearchSpecification spec = null;
        switch (choice) {
            case "1": System.out.print("Name keyword: "); spec = new NameSearchSpecification(scanner.nextLine()); break;
            case "2": System.out.print("Phone: "); spec = new PhoneSearchSpecification(scanner.nextLine()); break;
            case "3": System.out.print("Email keyword: "); spec = new EmailSearchSpecification(scanner.nextLine()); break;
            case "4":
                System.out.print("Name keyword: "); String n1 = scanner.nextLine();
                System.out.print("Email keyword: "); String e1 = scanner.nextLine();
                spec = new OrSearchSpecification(new NameSearchSpecification(n1), new EmailSearchSpecification(e1));
                break;
            case "5":
                System.out.print("Name keyword: "); String n2 = scanner.nextLine();
                System.out.print("Phone: "); String p2 = scanner.nextLine();
                spec = new OrSearchSpecification(new NameSearchSpecification(n2), new PhoneSearchSpecification(p2));
                break;
            default: System.out.println("Invalid choice."); return;
        }
        searchService.displayResults(searchService.search(contacts, spec));
    }

    private static void filterAndSort() {
        System.out.println("\n--- Filter & Sort Contacts ---");
        List<Contact> contacts = contactService.getAllContacts();
        if (contacts.isEmpty()) { System.out.println("No contacts found."); return; }
        System.out.println("Filter: 1.Person  2.Organization  3.All");
        System.out.print("Choose: ");
        String filterChoice = scanner.nextLine();
        ContactFilter filter;
        switch (filterChoice) {
            case "1": filter = new ContactTypeFilter("Person"); break;
            case "2": filter = new ContactTypeFilter("Organization"); break;
            default: filter = c -> contacts.stream().collect(java.util.stream.Collectors.toList());
        }
        System.out.println("Sort: 1.Name A-Z  2.Name Z-A  3.Date Added");
        System.out.print("Choose: ");
        String sortChoice = scanner.nextLine();
        ContactSortStrategy sort;
        switch (sortChoice) {
            case "2": sort = new SortByNameDesc(); break;
            case "3": sort = new SortByDateAdded(); break;
            default: sort = new SortByNameAsc();
        }
        filterSortService.displayResults(filterSortService.filterAndSort(contacts, filter, sort));
    }

    private static void manageTags() {
        System.out.println("\n--- Manage Tags ---");
        boolean back = false;
        while (!back) {
            System.out.println("\n1. Create Tag");
            System.out.println("2. View All Tags");
            System.out.println("3. Add Tag to Contact");
            System.out.println("4. Remove Tag from Contact");
            System.out.println("5. View Tags for Contact");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.print("Tag name: "); String tagName = scanner.nextLine();
                    System.out.print("Tag color: "); String tagColor = scanner.nextLine();
                    TagFactory.getTag(tagName, tagColor);
                    break;
                case "2":
                    TagFactory.displayAllTags();
                    break;
                case "3":
                    List<Contact> contacts = contactService.getAllContacts();
                    if (contacts.isEmpty()) { System.out.println("No contacts."); break; }
                    viewAllContacts();
                    System.out.print("Select contact: ");
                    try {
                        int ci = Integer.parseInt(scanner.nextLine());
                        Contact contact = contacts.get(ci);
                        TagFactory.displayAllTags();
                        System.out.print("Enter tag name: ");
                        Tag tag = TagFactory.getTag(scanner.nextLine());
                        tagService.addTagToContact(contact, tag);
                    } catch (NumberFormatException | IndexOutOfBoundsException e) { System.out.println("✗ Invalid."); }
                    break;
                case "4":
                    List<Contact> allC = contactService.getAllContacts();
                    if (allC.isEmpty()) { System.out.println("No contacts."); break; }
                    viewAllContacts();
                    System.out.print("Select contact: ");
                    try {
                        int ci = Integer.parseInt(scanner.nextLine());
                        Contact contact = allC.get(ci);
                        tagService.displayTagsForContact(contact);
                        System.out.print("Enter tag name to remove: ");
                        Tag tag = TagFactory.getTag(scanner.nextLine());
                        tagService.removeTagFromContact(contact, tag);
                    } catch (NumberFormatException | IndexOutOfBoundsException e) { System.out.println("✗ Invalid."); }
                    break;
                case "5":
                    List<Contact> allContacts = contactService.getAllContacts();
                    if (allContacts.isEmpty()) { System.out.println("No contacts."); break; }
                    viewAllContacts();
                    System.out.print("Select contact: ");
                    try {
                        int ci = Integer.parseInt(scanner.nextLine());
                        tagService.displayTagsForContact(allContacts.get(ci));
                    } catch (NumberFormatException | IndexOutOfBoundsException e) { System.out.println("✗ Invalid."); }
                    break;
                case "0": back = true; break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void findContactsByTag() {
        System.out.println("\n--- Find Contacts by Tag ---");
        List<Contact> contacts = contactService.getAllContacts();
        if (contacts.isEmpty()) { System.out.println("No contacts found."); return; }
        TagFactory.displayAllTags();
        System.out.print("Enter tag name to search: ");
        String tagName = scanner.nextLine();
        List<Contact> results = tagService.getContactsByTag(contacts, tagName);
        if (results.isEmpty()) {
            System.out.println("No contacts found with tag: " + tagName);
        } else {
            System.out.println("\n--- Contacts with tag '" + tagName + "' ---");
            results.forEach(c -> System.out.println("  - " + c.getDisplayName() + " (" + c.getContactType() + ")"));
        }
    }
}