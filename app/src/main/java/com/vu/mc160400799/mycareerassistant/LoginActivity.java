package com.vu.mc160400799.mycareerassistant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class LoginActivity extends AppCompatActivity {
    public FirebaseAuth mAuth;
    public EditText mail, pass;
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mail = findViewById(R.id.mail);
        pass = findViewById(R.id.pass);
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    final String e_mail = user.getEmail();
                    String id = user.getUid();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User").child("Jobseeker");
                    reference.child(id).child("email").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String mail = (String) dataSnapshot.getValue();
                            if (e_mail != null) {
                                if (e_mail.equals(mail)) {
                                    Toast.makeText(LoginActivity.this, "Login As Job Seeker", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, MainActivityJobSeeker.class));
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Login asEmployer", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, MainActivityforEmployer.class));
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(LoginActivity.this, "Error" + databaseError.toException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            mAuth.signOut();
                        }
                    });
                }
            }
        });
    }

    public void login(View view) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Please Wait!");
        progressDialog.setCancelable(false);
        String email, password;
        email = mail.getText().toString().trim();
        password = pass.getText().toString();
        if (email.isEmpty()) {
            mail.setError("Enter E-mail");
            mail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mail.setError("Email is not valid");
            mail.requestFocus();
            return;
        }
        final int minPassword = 6;
        if (password.length() < minPassword
                && password.length() >= 1) {
            pass.setError("Password Should 6 or more Character ");
            pass.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            pass.setError("Please Enter Password");
            pass.requestFocus();
            return;
        }
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Error  " + Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void createAccount(View view){
        startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
        finish();
    }
}
