package com.vu.mc160400799.mycareerassistant;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Application_Activity extends AppCompatActivity {
    FirebaseRecyclerAdapter<Application, Application_Handler> adapter;
    ProgressDialog progressDialog;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            key = extras.getString("KEY");
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loding");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        RecyclerView recyclerView = findViewById(R.id.application_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());




        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Application");
        Query query = ref.orderByKey();
        FirebaseRecyclerOptions<Application> options = new FirebaseRecyclerOptions.Builder<Application>().setQuery(query, Application.class).build();
        adapter = new FirebaseRecyclerAdapter<Application, Application_Handler>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Application_Handler holder, int position, @NonNull Application model) {
                holder.setName(model.getFullName());
                final String url = model.getUrl();
                final String phoneNo = model.getPhoneNo();
                holder.callBtn.setOnClickListener(new OnClickListener() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNo));
                        startActivity(intent);
                    }
                });
                holder.cvBtn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(url), "application/pdf");
                        startActivity(Intent.createChooser(intent, "Choose an Application:"));
                    }
                });
            }

            @NonNull
            @Override
            public Application_Handler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View v= inflater.inflate(R.layout.job_recyclerview, parent, false);
                return new Application_Handler(v);
            }
        };
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
