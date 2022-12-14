package com.burakb.firstproject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        FirebaseAuth mAuth;
        DatabaseReference mData;

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser().getEmail().equals(list.get(position).getSenderMail())) {
            holder.myMessageTextView.setText(list.get(position).getMessage());
            holder.otherMessageTextView.setText(list.get(position).getDateTime().substring(11,16));
            holder.otherMessageTextView.setTextColor(Color.LTGRAY);
            int RGB = android.graphics.Color.rgb(140, 230, 221);
            holder.myMessageTextView.setBackgroundColor(RGB);
            holder.otherMessageTextView.setBackgroundColor(Color.WHITE);


        }
        else {
            holder.otherMessageTextView.setText(list.get(position).getMessage());
            holder.myMessageTextView.setText(list.get(position).getDateTime().substring(11,16));
            holder.myMessageTextView.setTextColor(Color.LTGRAY);
            holder.otherMessageTextView.setBackgroundColor(Color.LTGRAY);
            holder.myMessageTextView.setBackgroundColor(Color.WHITE);

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
