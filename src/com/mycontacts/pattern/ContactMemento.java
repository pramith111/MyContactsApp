package com.mycontacts.pattern;

import com.mycontacts.model.Email;
import com.mycontacts.model.PhoneNumber;

import java.util.List;

public class ContactMemento {
    private final String firstName;
    private final String lastName;
    private final String companyName;
    private final String address;
    private final String industry;
    private final String website;
    private final List<PhoneNumber> phoneNumbers;
    private final List<Email> emails;

    public ContactMemento(String firstName, String lastName, String companyName,
                          String address, String industry, String website,
                          List<PhoneNumber> phoneNumbers, List<Email> emails) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyName = companyName;
        this.address = address;
        this.industry = industry;
        this.website = website;
        this.phoneNumbers = phoneNumbers;
        this.emails = emails;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getCompanyName() { return companyName; }
    public String getAddress() { return address; }
    public String getIndustry() { return industry; }
    public String getWebsite() { return website; }
    public List<PhoneNumber> getPhoneNumbers() { return phoneNumbers; }
    public List<Email> getEmails() { return emails; }
}