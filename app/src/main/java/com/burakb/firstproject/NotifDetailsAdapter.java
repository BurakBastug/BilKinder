package com.burakb.firstproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotifDetailsAdapter extends RecyclerView.Adapter<NotifDetailsAdapter.ViewHolder>{
    Context context;
    ArrayList<Child> list = new ArrayList<>();

    public NotifDetailsAdapter(Context context,ArrayList<Child> list) {
        this.context=context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notif_detils_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.image.setImageResource(list.get(position).getImg());
        holder.name.setText(list.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //ImageView image;
        TextView name;
        LinearLayout parent_layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //image = itemView.findViewById(R.id.event_image);
            name = itemView.findViewById(R.id.student_name_notif_details);
            parent_layout = itemView.findViewById(R.id.notification_parent_layout);
        }
    }
}
