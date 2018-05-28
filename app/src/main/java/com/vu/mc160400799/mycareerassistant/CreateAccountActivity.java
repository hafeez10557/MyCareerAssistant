package com.vu.mc160400799.mycareerassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }
    public void work(View view){
        startActivity(new Intent(CreateAccountActivity.this,JobSeekerRegistrationActivity.class));
    }
    public void hire(View view){
        startActivity(new Intent(CreateAccountActivity.this,EmployerRegistrationActivity.class));

    }
}
