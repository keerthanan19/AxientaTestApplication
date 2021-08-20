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
import com.assignment.axientatestapplication.data.UserModel;
import com.assignment.axientatestapplication.listners.UserOnClick;

import java.util.ArrayList;


public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.RecycleViewHolder>{
    Context mContext;
    ArrayList<UserModel> userModelArrayList;
    private final LayoutInflater inflater;
    UserOnClick userOnClick;

    public UserListAdapter(Context context, ArrayList<UserModel> userModels, UserOnClick userOnClick) {
        this.mContext = context;
        this.userModelArrayList = userModels;
        inflater = LayoutInflater.from(context);
        this.userOnClick = userOnClick;
    }

    @NonNull
    @Override
    public UserListAdapter.RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.user_list_item, parent, false);
        return new UserListAdapter.RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.RecycleViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.Name.setText(userModelArrayList.get(position).getName());
        holder.Email.setText(userModelArrayList.get(position).getEmail());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userOnClick.onUserOnClick(String.valueOf(userModelArrayList.get(position).getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return userModelArrayList.size();
    }

    protected class RecycleViewHolder extends RecyclerView.ViewHolder {

        TextView Name, Email;

        public RecycleViewHolder(View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.Name);
            Email = itemView.findViewById(R.id.Email);

        }
    }
}
