package com.example.projectem.users;

import android.content.Context;
import android.graphics.Bitmap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projectem.R;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private Bitmap bitmap1;
    private FirebaseServices fbs;
    private List<Task> data;

    private Context context;
    public TaskAdapter(List<Task> data, Context context) {
        this.data = data;
        this.context=context;
    }

    public void setData(List<Task> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Task task=data.get(position);
        fbs =FirebaseServices.getInstance();
        holder.ttask.setText(task.getTask());
        holder.tpoint.setText(""+task.getPoints());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tpoint,ttask;
        ImageView check;

        public ViewHolder(View itemView) {
            super(itemView);
            ttask=itemView.findViewById(R.id.checktv);
            tpoint=itemView.findViewById(R.id.checkpoint);
            check=itemView.findViewById(R.id.checkmark);
        }
    }
}

