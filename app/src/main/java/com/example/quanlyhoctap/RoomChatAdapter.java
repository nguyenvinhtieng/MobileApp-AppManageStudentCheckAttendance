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

public class RoomChatAdapter extends ArrayAdapter<RoomChat> {

    private Context context;
    private int layoutToBeInflated;
    private List<RoomChat> chats;


    public RoomChatAdapter(@NonNull Context context, int resource, @NonNull List<RoomChat> chats) {
        super(context, resource, chats);
        this.chats = chats;
        this.layoutToBeInflated = resource;
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ChatViewHolder holder;
        View row = convertView;

        if(row == null){
            LayoutInflater inflater =((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutToBeInflated, null);
            holder = new ChatViewHolder();
            holder.sender = row.findViewById(R.id.sender);
            holder.time = row.findViewById(R.id.time);
            holder.content = row.findViewById(R.id.content);
            row.setTag(holder);
        }
        else{
            holder =(ChatViewHolder) row.getTag();
        }
        RoomChat room = chats.get(position);
        holder.sender.setText(room.getsender());
        holder.time.setText("(" + room.getThoigian() + ")");
        holder.content.setText(room.getNoidung());

        return row;
    }

    private class ChatViewHolder{
        TextView sender,content,time;
    }
}
