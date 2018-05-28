package com.vu.mc160400799.mycareerassistant;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;



public class SearchFragment extends Fragment {
    FirebaseRecyclerAdapter<Job,RecyclerViewSearch> adapter;
    FirebaseRecyclerAdapter<Job,RecyclerViewSearch> adapter1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView=inflater.inflate(R.layout.fragment_search, container, false);

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loding");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");

        Spinner selectedCity = rootView.findViewById(R.id.city);
        final String city= selectedCity.getSelectedItem().toString();

        RecyclerView recyclerView=rootView.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        final DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Jobs");
//        Query query=ref.orderByKey();
        Query query=ref.orderByChild("city").equalTo("Gujranwala");

        FirebaseRecyclerOptions<Job> options= new FirebaseRecyclerOptions.Builder<Job>().setQuery(query,Job.class).build();
        adapter=new FirebaseRecyclerAdapter<Job, RecyclerViewSearch>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RecyclerViewSearch holder, int position, @NonNull Job model) {
                holder.setJobTitle(model.getJobTitle());
                holder.setCompany(model.getJobType());
                holder.setGender(model.getGender());
                holder.setEducation(model.getEducation());
                holder.setAge(model.getAge());
                holder.setDate(model.getDate());
                holder.setCompany(model.getCompanyName());
                final String key=model.getKey();

                holder.apply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), "Apply", Toast.LENGTH_SHORT).show();
                    }
                });
                holder.favorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), "favorite", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @NonNull
            @Override
            public RecyclerViewSearch onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View v= inflater.inflate(R.layout.search_recycler, parent, false);
                return new RecyclerViewSearch(v);
            }
        };
        recyclerView.setAdapter(adapter);
        Button search = rootView.findViewById(R.id.searchbtn);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.stopListening();
                final DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Jobs");
                Query query=ref.orderByChild("city").equalTo(city);

                FirebaseRecyclerOptions<Job> options= new FirebaseRecyclerOptions.Builder<Job>().setQuery(query,Job.class).build();
                adapter1=new FirebaseRecyclerAdapter<Job, RecyclerViewSearch>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull RecyclerViewSearch holder, int position, @NonNull Job model) {
                        holder.setJobTitle(model.getJobTitle());
                        holder.setCompany(model.getJobType());
                        holder.setGender(model.getGender());
                        holder.setEducation(model.getEducation());
                        holder.setAge(model.getAge());
                        holder.setDate(model.getDate());
                        holder.setCompany(model.getCompanyName());
                        final String key=model.getKey();

                        holder.apply.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getActivity(), "Apply", Toast.LENGTH_SHORT).show();
                            }
                        });
                        holder.favorite.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getActivity(), "favorite", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public RecyclerViewSearch onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                        View v= inflater.inflate(R.layout.search_recycler, parent, false);
                        return new RecyclerViewSearch(v);
                    }
                };
            }
        });

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
        adapter1.stopListening();
    }

}
