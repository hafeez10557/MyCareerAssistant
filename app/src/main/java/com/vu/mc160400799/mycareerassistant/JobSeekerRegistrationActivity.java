package com.vu.mc160400799.mycareerassistant;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class JobSeekerRegistrationActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 234;
    public static final String STORAGE_PATH_UPLOADS = "CV/";
    public Uri filePath;
    User user;
    String url;
    public ProgressDialog progressDialog;
    public String uId;
    public TextView status;
    public EditText mail, pass, cPass, editTextname, no;
    public FirebaseAuth mAuth;
    public StorageReference storageReference;
    public DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_seeker_registration);
        mail = findViewById(R.id.mail);
        pass = findViewById(R.id.password);
        cPass = findViewById(R.id.cPassword);
        editTextname = findViewById(R.id.name);
        no = findViewById(R.id.phoneNo);
        status = findViewById(R.id.cv);
        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
    }
    public void dataToDb() {
        String fullName = editTextname.getText().toString();
        String phoneNo = no.getText().toString();
        String email= mAuth.getCurrentUser().getEmail();
        mDatabase = FirebaseDatabase.getInstance().getReference("User");
        user = new User(email,fullName,phoneNo,url);
        uId = mAuth.getCurrentUser().getUid();
        mDatabase.child("Jobseeker").child(uId).setValue(user);
    }
    public void registration(View view) {
        final String email = mail.getText().toString();
        final String password = pass.getText().toString();
        final String cPassword = cPass.getText().toString();
        if (!password.equals(cPassword)) {
            cPass.setError("Password not matchh");
            cPass.requestFocus();
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Registration");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            uploadFile();
                            progressDialog.show();
                            dataToDb();
                            progressDialog.dismiss();
                            Toast.makeText(JobSeekerRegistrationActivity.this, "complate", Toast.LENGTH_SHORT).show();

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(JobSeekerRegistrationActivity.this, "Some thing Wrong", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
//       Select Cv button Click Lisner
        public void selectCV(View view) {
            showFileChooser();
        }
//       funcation for chosing CV file from internal Storage
        public  void showFileChooser() {
            Intent intent = new Intent();
            intent.setType("application/pdf");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }
//        Funcation for Get extension from selected file
    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
//    getin the file path frpom activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            if (filePath != null) {
                status.setText("Cv Selected");
            }
        }
    }
    private void uploadFile() {
        //checking if file is available
        if (filePath != null) {
            //getting the storage reference
            StorageReference sRef = storageReference.child(STORAGE_PATH_UPLOADS + uId + "." + getFileExtension(filePath));
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();
            //adding the file to reference
            sRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            //dismissing the progress dialog
                            progressDialog.dismiss();

                            //displaying success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();

                            //creating the upload object to store uploaded image details
                            url=taskSnapshot.getDownloadUrl().toString();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();

                            Toast.makeText(getApplicationContext(), exception.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }

                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    //displaying the upload progress
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                }
            });

        }
}

    private static class User{
        private String email;
        private String fullName;
        private String phoneNo;
        private String url;

        public User() {
            //empty constructor
        }

        public User(String email, String fullName, String phoneNo, String url) {
            this.email = email;
            this.fullName = fullName;
            this.phoneNo = phoneNo;
            this.url = url;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }


        public void setUrl(String url) {
            this.url = url;
        }

        public String getFullName() {
            return fullName;
        }

        public String getPhoneNo() {
            return phoneNo;
        }


        public String getUrl() {
            return url;
        }
    }


}

