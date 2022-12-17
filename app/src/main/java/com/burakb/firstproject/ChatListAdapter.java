package com.burakb.firstproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder>{
    Context context;
    ArrayList<Child> chatList = new ArrayList<>();

    public ChatListAdapter(Context context, ArrayList list){
        this.context = context;
        this.chatList = list;
    }

    @NonNull
    @Override
    public ChatListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_row,parent,false);
        ChatListAdapter.ViewHolder viewHolder = new ChatListAdapter.ViewHolder(view);

        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //holder.image.setImageResource(list.get(position).getImg());
        holder.name.setText(chatList.get(position).getUsername());
        holder.condition.setText(chatList.get(position).getMedicalCondition());
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, chatList.get(position).getUsername(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //ImageView image;
        TextView name;
        TextView condition;
        LinearLayout parent_layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //image = itemView.findViewById(R.id.event_image);
            name = itemView.findViewById(R.id.student_name);
            condition = itemView.findViewById(R.id.health_condition);
            parent_layout = itemView.findViewById(R.id.children_status_parent_layout);
        }
    }
}
