package com.example.mynotesapplication;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class noteHolder extends RecyclerView.ViewHolder {

    TextView title;
    TextView desc;
    TextView date;

    public noteHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.Title);
        desc = itemView.findViewById(R.id.desc);
        date = itemView.findViewById(R.id.date);
    }


}
