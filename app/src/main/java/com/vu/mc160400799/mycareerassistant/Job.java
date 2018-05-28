package com.vu.mc160400799.mycareerassistant;

public class Job {
    private String key;
    private String uID;
    private String jobTitle;
    private String jobType;
    private String education;
    private String age;
    private String gender;
    private String date;
    private String city;
    private String companyName;
    public Job(){
//        Empty constrator
    }

    public Job(String key, String uID, String jobTitle, String jobType, String education, String age, String gender, String date, String city, String companyName) {
        this.key = key;
        this.uID = uID;
        this.jobTitle = jobTitle;
        this.jobType = jobType;
        this.education = education;
        this.age = age;
        this.gender = gender;
        this.date = date;
        this.city = city;
        this.companyName = companyName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = this.companyName;
    }
}
