package com.vu.mc160400799.mycareerassistant;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class PostJobForm extends AppCompatActivity {
    public ProgressDialog progressDialog;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Jobs");

    //view
    public Button jobPost;
    public EditText edJobTitle;
    public Spinner spJobType;
    public Spinner spEducation;
    public Spinner spAge;
    public Spinner spGender;
    //strings
    String key;
    String uId;
    String jobTitle;
    String jobType;
    String education;
    String age;
    String gender;
    String date;
    String city;
    String companyName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job_form);
        edJobTitle=findViewById(R.id.jobTitle);
        spJobType=findViewById(R.id.jobType);
        spEducation=findViewById(R.id.education);
        spAge=findViewById(R.id.age);
        spGender=findViewById(R.id.gender);
        jobPost=findViewById(R.id.postjob);
        jobPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postJob();
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");



        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        Date d = new Date();
        date= dateFormat.format(d);
        key= mDatabase.push().getKey();
        uId= Objects.requireNonNull(user).getUid();

        ref.child("Employer").child(uId).child("city").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                city=dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PostJobForm.this, "City Error"+databaseError.toException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        ref.child("Employer").child(uId).child("companyName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                companyName=dataSnapshot.getValue(String.class);
                Toast.makeText(PostJobForm.this, companyName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PostJobForm.this, "Error"+databaseError.toException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void postJob(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Posting Job");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");



        jobTitle=edJobTitle.getText().toString();
        jobType=spJobType.getSelectedItem().toString();
        education=spEducation.getSelectedItem().toString();
        age=spAge.getSelectedItem().toString();
        gender=spGender.getSelectedItem().toString();
        if(jobTitle.isEmpty()){
            edJobTitle.setError("Please Give Jobe Title");
            edJobTitle.requestFocus();
            return;
        }
        if (jobType.equals("Select Job Type")){
            Toast.makeText(this, "Please Select Job Type", Toast.LENGTH_SHORT).show();
            return;
        }
        if (education.equals("Select Education")){
            Toast.makeText(this, "Please Select Education", Toast.LENGTH_LONG).show();
            return;
        }
        if (age.equals("Select Age")){
            Toast.makeText(this, "Please Select age", Toast.LENGTH_LONG).show();
            return;
        }
        if (gender.equals("Select Gender")){
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.show();
        Job job=new Job(key,uId,jobTitle,jobType,education, age, gender,date,city,companyName);
        mDatabase = FirebaseDatabase.getInstance().getReference("Jobs");

        mDatabase.child(key).setValue(job)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(PostJobForm.this, "Job Posted Successfully", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                startActivity(new Intent(PostJobForm.this,MainActivityforEmployer.class));
                finish();

            }
        })
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(PostJobForm.this, "Error"+e, Toast.LENGTH_SHORT).show();
            }
        });

    }

}
