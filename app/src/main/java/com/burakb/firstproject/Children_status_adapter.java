package com.burakb.firstproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Children_status_adapter extends RecyclerView.Adapter<Children_status_adapter.ViewHolder> implements ChildrenStatusItemListener {
    Context context;
    ArrayList<Child> list = new ArrayList<>();
    private final ChildrenStatusItemListener itemListener;

    @Override
    public void onItemClick(Child child) {

    }


    public interface RecyclerViewInterface{
        void onItemClick(int position);
    }

    public Children_status_adapter(Context context, ArrayList<Child> list, ChildrenStatusItemListener itemListener) {
        this.context=context;
        this.list = list;
        this.itemListener = itemListener;
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
        holder.name.setText(list.get(position).getUsername());
        holder.condition.setText(list.get(position).getMedicalCondition());

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference ref = storage.getReference().child("images/" + list.get(position).getImageDestination() + ".jpg");
        try {
            final File localFile = File.createTempFile(list.get(position).getImageDestination(), "jpg");
            ref.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    holder.studentProfilePicture.setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(view -> {
            itemListener.onItemClick(list.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView studentProfilePicture;
        TextView name;
        TextView condition;
        LinearLayout parent_layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.student_name);
            condition = itemView.findViewById(R.id.health_condition);
            studentProfilePicture = itemView.findViewById(R.id.studentPic);
            parent_layout = itemView.findViewById(R.id.children_status_parent_layout);
        }
    }
}
