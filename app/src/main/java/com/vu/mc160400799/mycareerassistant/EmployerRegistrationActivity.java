package com.vu.mc160400799.mycareerassistant;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class EmployerRegistrationActivity extends AppCompatActivity {
    // view
    private EditText mail;
    private EditText pass;
    private EditText cPass;
    private EditText oName;
    private EditText cAddr;
    private EditText no;
    private Spinner spCity;
    private Spinner spIndustry;
    //strings
    private String companyName;
    private String industry;
    private String city;
    private String address;
    private String phoneNo;
    //firebase
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_registration);
        //geting view referance
        mail = findViewById(R.id.mail);
        pass = findViewById(R.id.pass);
        cPass = findViewById(R.id.cPass);
        oName = findViewById(R.id.oName);
        cAddr = findViewById(R.id.cAddress);
        no = findViewById(R.id.phoneNo);
        spCity = findViewById(R.id.city);
        spIndustry = findViewById(R.id.industry);
        //geting firebase instance
        mAuth = FirebaseAuth.getInstance();
    }
    //registration button click lisner
    public void registration(View view){
        //progressdialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Registration");
        progressDialog.setMessage("Please Wait!!");
        progressDialog.setCancelable(false);
        //geting data from edit text
        String email = mail.getText().toString().trim();
        String password = pass.getText().toString();
        String cPassword = cPass.getText().toString();
        companyName=oName.getText().toString();
        industry = spIndustry.getSelectedItem().toString();
        city=spCity.getSelectedItem().toString();
        address=cAddr.getText().toString();
        phoneNo=no.getText().toString();

        //validation of data
        if (email.isEmpty()){
            mail.setError("Enter E-mail");
            mail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mail.setError("Email is not valid");
            mail.requestFocus();
            return;
        }
        final int minPassword=6;
        if (password.length()<minPassword
                && password.length()>=1){
            pass.setError("Password Should 6 or more Character ");
            pass.requestFocus();
            return;
        }
        if (password.isEmpty()){
            pass.setError("Please Enter Password");
            pass.requestFocus();
            return;
        }
        if (cPassword.isEmpty()){
            cPass.setError("Please enter Conform Password");
            cPass.requestFocus();
            cPass.selectAll();
            return;
        }
        if (!password.equals(cPassword)){
            cPass.setError("Conform Password not match");
            cPass.requestFocus();
            cPass.selectAll();
            return;
        }
        if (companyName.isEmpty()){
            oName.setError("Enter name");
            oName.requestFocus();
            return;
        }
        if (spIndustry.getSelectedItem().toString().equals("Select Industry")){
            Toast.makeText(this, "Please Select Industry first ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (spCity.getSelectedItem().toString().equals("Select City")){
            Toast.makeText(this, "Please Select City First", Toast.LENGTH_SHORT).show();
            return;
        }
        if (address.isEmpty()){
            cAddr.setError("Enter Address");
            cAddr.requestFocus();
            return;
        }
        if (phoneNo.isEmpty()){
            no.setError("Enter Phone No");
            no.requestFocus();
            return;
        }
        progressDialog.show();
        //creating a user with Firebase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //if user registerd success call th datatodb funcation to enter data
                            dataToDb();
                            progressDialog.dismiss();
                        }//if user registration is not success
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(EmployerRegistrationActivity.this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void dataToDb(){
        String uId= Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        String email=mAuth.getCurrentUser().getEmail().toString();
        //geting an object of employer class
        Employer employer=
                new Employer(email,companyName,industry,city,address,phoneNo);
        //geting firebasedatabase reference
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
        //add data to database
        databaseReference.
                child("Employer").child(uId).setValue(employer);
    }

}
