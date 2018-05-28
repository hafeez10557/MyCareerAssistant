package com.vu.mc160400799.mycareerassistant;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class RecyclerViewSearch extends RecyclerView.ViewHolder {
    private TextView jobTitle,jobType,gender,education,age,city,date,company;
    public ImageButton apply,favorite;
    public RecyclerViewSearch(View view) {
        super(view);
        jobTitle=view.findViewById(R.id.titlejob1);
        jobType=view.findViewById(R.id.typejob1);
        gender=view.findViewById(R.id.gender1);
        education=view.findViewById(R.id.edu1);
        age=view.findViewById(R.id.age1);
        city=view.findViewById(R.id.city1);
        date=view.findViewById(R.id.date1);
        apply=view.findViewById(R.id.apply);
        company=view.findViewById(R.id.company1);
        favorite=view.findViewById(R.id.favorite);
    }

    public void setJobTitle(String s) {
        jobTitle.setText(s);
    }

    public void setCompany(String s){
        company.setText(s);
    }

    public void setJobType(String s) {
        jobType.setText(s);
    }

    public void setGender(String s) {
        gender.setText(s);
    }

    public void setEducation(String s) {
        education.setText(s);
    }

    public void setAge(String s) {
        age.setText(s);
    }

    public void setCity(String s) {
        city.setText(s);
    }

    public void setDate(String s) {
        date.setText(s);
    }
}
