package com.burakb.firstproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Notification_adapter extends RecyclerView.Adapter<Notification_adapter.ViewHolder> implements NotificationItemListener{
    Context context;
    ArrayList<Notification> list = new ArrayList<>();
    private final NotificationItemListener itemListener;

    public Notification_adapter(Context context, ArrayList<Notification> list,NotificationItemListener itemListener) {
        this.context=context;
        this.list = list;
        this.itemListener = itemListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notificaton_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.notifName.setText(list.get(position).getNotifName());
        holder.notifDesc.setText(list.get(position).getNotifDetails());
        holder.itemView.setOnClickListener(view -> {
            itemListener.onItemClick(list.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onItemClick(Notification notif) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView notifName;
        TextView notifDesc;
        LinearLayout parent_layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notifName = itemView.findViewById(R.id.notif_name);
            notifDesc = itemView.findViewById(R.id.notif_desc);
            parent_layout = itemView.findViewById(R.id.notification_parent_layout);
        }
    }
}
