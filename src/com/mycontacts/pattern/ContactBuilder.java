package com.mycontacts.pattern;

import com.mycontacts.model.Contact;
import com.mycontacts.model.Email;
import com.mycontacts.model.Organization;
import com.mycontacts.model.Person;
import com.mycontacts.model.PhoneNumber;

public class ContactBuilder {
    private String type;
    private String firstName;
    private String lastName;
    private String companyName;
    private String address;
    private String industry;
    private String website;
    private PhoneNumber phoneNumber;
    private Email email;

    public ContactBuilder setType(String type) {
        this.type = type;
        return this;
    }

    public ContactBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ContactBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ContactBuilder setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public ContactBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public ContactBuilder setIndustry(String industry) {
        this.industry = industry;
        return this;
    }

    public ContactBuilder setWebsite(String website) {
        this.website = website;
        return this;
    }

    public ContactBuilder setPhoneNumber(String number, String phoneType) {
        this.phoneNumber = new PhoneNumber(number, phoneType);
        return this;
    }

    public ContactBuilder setEmail(String address, String emailType) {
        this.email = new Email(address, emailType);
        return this;
    }

    public Contact build() {
        if (type == null) throw new IllegalStateException("Contact type is required.");
        Contact contact;
        if (type.equalsIgnoreCase("person")) {
            if (firstName == null || lastName == null)
                throw new IllegalStateException("First and last name are required for Person.");
            Person person = new Person(firstName, lastName);
            if (address != null) person.setAddress(address);
            contact = person;
        } else if (type.equalsIgnoreCase("organization")) {
            if (companyName == null)
                throw new IllegalStateException("Company name is required for Organization.");
            Organization org = new Organization(companyName);
            if (industry != null) org.setIndustry(industry);
            if (website != null) org.setWebsite(website);
            contact = org;
        } else {
            throw new IllegalArgumentException("Unknown contact type: " + type);
        }
        if (phoneNumber != null) contact.addPhoneNumber(phoneNumber);
        if (email != null) contact.addEmail(email);
        return contact;
    }
}