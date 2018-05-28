package com.vu.mc160400799.mycareerassistant;

public class Application {
    private String fullName;
    private String url;
    private String phoneNo;

    public Application() {
    }

    public Application(String fullName, String url, String phoneNo) {
        this.fullName = fullName;
        this.url = url;
        this.phoneNo = phoneNo;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
