package com.example.mynotesapplication;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<noteHolder> {

    private ArrayList<Note> nList;
    private MainActivity mainAct;

    public adapter(MainActivity mainAct, ArrayList<Note> nList) {
        this.mainAct = mainAct;
        this.nList = nList;

    }

    @NonNull
    @Override
    public noteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_view, parent, false);
        itemView.setOnClickListener(mainAct);
        itemView.setOnLongClickListener(mainAct);
        return new noteHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull noteHolder holder, int position) {
        Note n = nList.get(position);
        holder.title.setText(n.getTitle());
        holder.date.setText(n.getDate());
        String sub = n.getDesc();
        if (sub.length() >= 80) {
            String temp = sub.substring(0,80);
            holder.desc.setText(temp + "...");
        }
        else
            holder.desc.setText(n.getDesc());

    }

    @Override
    public int getItemCount() {
        return nList.size();
    }
}
