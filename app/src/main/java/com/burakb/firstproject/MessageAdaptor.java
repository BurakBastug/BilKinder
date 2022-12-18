package com.burakb.firstproject;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessageAdaptor extends RecyclerView.Adapter<MessageAdaptor.ViewHolder>{
    Context context;
    ArrayList<Message> list = new ArrayList<>();

    public MessageAdaptor(Context context,ArrayList<Message> list) {
        this.context=context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if() {
            holder.myMessageTextView.setText(list.get(position).getContent());
            holder.myMessageTextView.setBackground(Drawable.createFromPath("app/src/main/res/drawable/my_message_background.xml"));
        }
        else {
            holder.otherMessageTextView.setText(list.get(position).getContent());
            holder.myMessageTextView.setBackground(Drawable.createFromPath("app/src/main/res/drawable/other_message_background.xml"));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myMessageTextView;
        TextView otherMessageTextView;
        LinearLayout parent_layout;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myMessageTextView = itemView.findViewById(R.id.my_message_textview);
            otherMessageTextView = itemView.findViewById(R.id.other_message_textview);
            parent_layout = itemView.findViewById(R.id.message_parent_layout);
        }
    }
}
