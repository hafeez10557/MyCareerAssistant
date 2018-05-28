package com.vu.mc160400799.mycareerassistant;

public class Employer {
    private String email;
    private String companyName;
    private String industry;
    private String city;
    private String address;
    private String phoneNo;

    public Employer() {

    }

    public Employer(String email, String companyName, String industry, String city, String address, String phoneNo) {
        this.email = email;
        this.companyName = companyName;
        this.industry = industry;
        this.city = city;
        this.address = address;
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
