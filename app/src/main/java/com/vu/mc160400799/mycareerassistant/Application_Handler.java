package com.vu.mc160400799.mycareerassistant;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Application_Handler extends RecyclerView.ViewHolder  {
    private TextView name;
    public ImageButton callBtn;
    public Button cvBtn;
    public Application_Handler(View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.tvNamw);
        callBtn=itemView.findViewById(R.id.call);
        cvBtn=itemView.findViewById(R.id.btn_cv);

    }

    public void setName(String s) {
        name.setText(s);
    }
}
