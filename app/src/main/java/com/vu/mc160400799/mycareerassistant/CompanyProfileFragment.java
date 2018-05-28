package com.vu.mc160400799.mycareerassistant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CompanyProfileFragment extends Fragment {
    public Button button;
    public EditText name,address,no;
    public Spinner spIndustry,spCity;
    //Database variables
    DatabaseReference mDatabase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_company_profile, container, false);
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        name=v.findViewById(R.id.oName);
        address=v.findViewById(R.id.cAddress);
        no=v.findViewById(R.id.phoneNo);
        spIndustry=v.findViewById(R.id.industry);
        spCity=v.findViewById(R.id.city);
        button=v.findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    public void DBtoApp(){
        mDatabase= FirebaseDatabase.getInstance().getReference();
    }

}
