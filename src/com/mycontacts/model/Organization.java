package com.mycontacts.model;

public class Organization extends Contact {
    private String companyName;
    private String industry;
    private String website;

    public Organization(String companyName) {
        super();
        this.companyName = companyName;
    }

    public String getCompanyName() { return companyName; }
    public String getIndustry() { return industry; }
    public String getWebsite() { return website; }

    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public void setIndustry(String industry) { this.industry = industry; }
    public void setWebsite(String website) { this.website = website; }

    @Override
    public String getContactType() { return "Organization"; }

    @Override
    public String getDisplayName() { return companyName; }

    @Override
    public String toString() {
        return super.toString()
                + "\n  Industry: " + (industry != null ? industry : "N/A")
                + "\n  Website: " + (website != null ? website : "N/A")
                + "\n  Phones: " + getPhoneNumbers()
                + "\n  Emails: " + getEmails()
                + "\n  Created: " + getCreatedAt();
    }
}