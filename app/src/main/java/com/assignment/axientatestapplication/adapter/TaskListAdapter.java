package com.assignment.axientatestapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.axientatestapplication.R;
import com.assignment.axientatestapplication.data.TaskModel;
import com.assignment.axientatestapplication.data.UserModel;
import com.assignment.axientatestapplication.listners.UserOnClick;

import java.util.ArrayList;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.RecycleViewHolder>{
    Context mContext;
    ArrayList<TaskModel> taskModelArrayList;
    private final LayoutInflater inflater;

    public TaskListAdapter(Context context, ArrayList<TaskModel> taskModels) {
        this.mContext = context;
        this.taskModelArrayList = taskModels;
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public TaskListAdapter.RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.task_list_item, parent, false);
        return new TaskListAdapter.RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListAdapter.RecycleViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.Name.setText(taskModelArrayList.get(position).getTaskName());

    }

    @Override
    public int getItemCount() {
        return taskModelArrayList.size();
    }

    protected class RecycleViewHolder extends RecyclerView.ViewHolder {

        TextView Name, Email;

        public RecycleViewHolder(View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.Name);

        }
    }
}