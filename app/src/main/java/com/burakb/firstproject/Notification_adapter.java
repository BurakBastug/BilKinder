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

public class Notification_adapter extends RecyclerView.Adapter<Notification_adapter.ViewHolder>{
    Context context;
    ArrayList<Notification> list = new ArrayList<>();

    public Notification_adapter(Context context, ArrayList<Notification> list) {
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
        holder.notifName.setText(list.get(position).getNotifName());
        holder.notifDesc.setText(list.get(position).getNotifDetails());
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, list.get(position).getNotifName(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //ImageView image;
        TextView notifName;
        TextView notifDesc;
        LinearLayout parent_layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //image = itemView.findViewById(R.id.event_image);
            notifName = itemView.findViewById(R.id.notif_name);
            notifDesc = itemView.findViewById(R.id.notif_desc);
            parent_layout = itemView.findViewById(R.id.notification_parent_layout);
        }
    }
}
