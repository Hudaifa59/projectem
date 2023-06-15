package com.example.projectem;

import android.content.Context;
import android.graphics.Bitmap;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectem.R;
import com.example.projectem.users.FirebaseServices;
import com.example.projectem.users.Profile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;

import java.util.List;


public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder> {
    private Bitmap bitmap1;
    private FirebaseServices fbs;
    private List<Profile> data;

    private Context context;
    public LeaderBoardAdapter(List<Profile> data, Context context) {
        this.data = data;
        this.context=context;
    }

    public void setData(List<Profile> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Profile p =data.get(position);
        fbs =FirebaseServices.getInstance();
        holder.name.setText(p.getUsername());
        holder.number.setText(p.getPoint());
        StorageReference storageRef= fbs.getStorage().getInstance().getReference().child(p.getImage());

        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context)
                        .load(uri)
                        .into(holder.check);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle any errors that occur when downloading the image
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name , number ;
        ImageView check;

        public ViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.TVName);
            number=itemView.findViewById(R.id.TVNumber);
            check=itemView.findViewById(R.id.IVItem);
        }
    }
}

