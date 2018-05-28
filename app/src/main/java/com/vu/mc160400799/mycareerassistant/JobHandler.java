package com.vu.mc160400799.mycareerassistant;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class JobHandler extends RecyclerView.ViewHolder {
    private TextView jobTitle,jobType,gender,education,age,careerLevel,date;
    public  Button application,delete;
    public JobHandler(View itemView) {
        super(itemView);
        jobTitle=itemView.findViewById(R.id.titlejob);
        jobType=itemView.findViewById(R.id.typejob);
        gender=itemView.findViewById(R.id.gender);
        education=itemView.findViewById(R.id.edu);
        age=itemView.findViewById(R.id.age);
        careerLevel=itemView.findViewById(R.id.levelcareer);
        date=itemView.findViewById(R.id.date);
        application=itemView.findViewById(R.id.application);
        delete=itemView.findViewById(R.id.delete);

    }

    public void setDate(String s) {
        date.setText(s);
    }

    public void setJobTitle(String s) {
        jobTitle.setText(s);
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
        age .setText(s);
    }

    public void setCareerLevel(String s) {
        careerLevel.setText(s);
    }
}
