package com.vu.mc160400799.mycareerassistant;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.support.annotation.NonNull;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import static android.support.v7.widget.RecyclerView.*;
import static com.vu.mc160400799.mycareerassistant.R.layout.fragment_home;

public class HomeFragment extends Fragment  {
    Button postJob;

    FirebaseRecyclerAdapter<Job,JobHandler> adapter;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =inflater.inflate(fragment_home, container, false);
        postJob= rootView.findViewById(R.id.postjob);
        postJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),PostJobForm.class));
            }
        });
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loding");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        RecyclerView recyclerView=rootView.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uID=user.getUid();
        final DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Jobs");
        Query query=ref.orderByChild("uID").equalTo(uID);

        FirebaseRecyclerOptions<Job> options=new FirebaseRecyclerOptions.Builder<Job>().setQuery(query,Job.class).build();
        adapter=new FirebaseRecyclerAdapter<Job, JobHandler>(options) {

            @NonNull
            @Override
            public JobHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View v= inflater.inflate(R.layout.job_recyclerview, parent, false);
                return new JobHandler(v);
            }

            @Override
            protected void onBindViewHolder(@NonNull final JobHandler holder, final int position, @NonNull final Job model) {
                holder.setJobTitle(model.getJobTitle());
                holder.setJobType(model.getJobType());
                holder.setGender(model.getGender());
                holder.setEducation(model.getEducation());
                holder.setAge(model.getAge());
                holder.setDate(model.getDate());
                final String key=model.getKey();
                holder.application.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(getActivity(),Application_Activity.class);
                        intent.putExtra("KEY",key);
                        startActivity(intent);
                    }
                });
                holder.delete.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Remove Job")
                                .setMessage("Do you really want to Remove Job?")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        ref.child(key).removeValue();
                                    }})
                                .setNegativeButton(android.R.string.no, null).show();
                    }
                });

            }
            @Override
            public void onDataChanged() {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        };

        recyclerView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
