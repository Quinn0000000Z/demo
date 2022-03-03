package com.example.demoapp.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserView {
    private String id;
    private String city;
    private String company;
    private String country;
    private String firstName;
    private String lastName;
    private String organizationType;
    private String phone;
    private String state;
    private String zipCode;
    private boolean disclaimerAccepted;
    private String emailAddress;
    private String languageCode;
    private String registrationId;
    private String registrationIdGeneratedTime;
    // TODO remove initialization if not needed
    private List<String> projectIds = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(String organizationType) {
        this.organizationType = organizationType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public boolean isDisclaimerAccepted() {
        return disclaimerAccepted;
    }

    public void setDisclaimerAccepted(boolean disclaimerAccepted) {
        this.disclaimerAccepted = disclaimerAccepted;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getRegistrationIdGeneratedTime() {
        return registrationIdGeneratedTime;
    }

    public void setRegistrationIdGeneratedTime(String registrationIdGeneratedTime) {
        this.registrationIdGeneratedTime = registrationIdGeneratedTime;
    }

    public List<String> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<String> projectIds) {
        this.projectIds = projectIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserView userView = (UserView) o;
        return disclaimerAccepted == userView.disclaimerAccepted && Objects.equals(id, userView.id) && Objects.equals(city, userView.city) && Objects.equals(company, userView.company) && Objects.equals(country, userView.country) && Objects.equals(firstName, userView.firstName) && Objects.equals(lastName, userView.lastName) && Objects.equals(organizationType, userView.organizationType) && Objects.equals(phone, userView.phone) && Objects.equals(state, userView.state) && Objects.equals(zipCode, userView.zipCode) && Objects.equals(emailAddress, userView.emailAddress) && Objects.equals(languageCode, userView.languageCode) && Objects.equals(registrationId, userView.registrationId) && Objects.equals(registrationIdGeneratedTime, userView.registrationIdGeneratedTime) && Objects.equals(projectIds, userView.projectIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, company, country, firstName, lastName, organizationType, phone, state, zipCode, disclaimerAccepted, emailAddress, languageCode, registrationId, registrationIdGeneratedTime, projectIds);
    }
}
