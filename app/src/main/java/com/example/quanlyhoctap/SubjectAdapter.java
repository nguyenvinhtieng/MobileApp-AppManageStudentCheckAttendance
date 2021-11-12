package com.example.quanlyhoctap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class SubjectAdapter extends ArrayAdapter<Subject> {

    private Context context;
    private int layoutToBeInflated;
    private List<Subject> subjects;

    public SubjectAdapter(@NonNull Context context, int resource, @NonNull List<Subject> subjects) {
        super(context, resource, subjects);
        this.subjects = subjects;
        this.layoutToBeInflated = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SubjectViewHolder holder;
        View row = convertView;

        if(row == null){
            LayoutInflater inflater =((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutToBeInflated, null);
            holder = new SubjectViewHolder();
            holder.tvName = row.findViewById(R.id.name);
            holder.tvNote = row.findViewById(R.id.note);
            holder.tvUsercreate = row.findViewById(R.id.usercreate);
            row.setTag(holder);
        }
        else{
            holder =(SubjectViewHolder) row.getTag();
        }
        Subject subject = subjects.get(position);
        holder.tvName.setText(subject.getName());
        holder.tvUsercreate.setText(subject.getUsercreate());
        holder.tvNote.setText(subject.getNote());
       // Animation animation = AnimationUtils.loadAnimation(context, R.anim.zoomout_animation);
       // row.startAnimation(animation);
        return row;
    }


    private class SubjectViewHolder {
        TextView tvName,tvNote,tvUsercreate;
    }
}
