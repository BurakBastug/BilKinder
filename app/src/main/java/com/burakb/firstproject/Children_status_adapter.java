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

public class Children_status_adapter extends RecyclerView.Adapter<Children_status_adapter.ViewHolder>{
    Context context;
    ArrayList<Child> list = new ArrayList<>();

    public Children_status_adapter(Context context, ArrayList<Child> list) {
        this.context=context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.children_status_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //holder.image.setImageResource(list.get(position).getImg());
        holder.name.setText(list.get(position).getUsername());
        holder.condition.setText(list.get(position).getSickOrNot());
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, list.get(position).getUsername(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
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
