package com.assignment.axientatestapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.assignment.axientatestapplication.Helper.Utils;
import com.assignment.axientatestapplication.adapter.UserListAdapter;
import com.assignment.axientatestapplication.data.UserModel;
import com.assignment.axientatestapplication.database.DBUtils;
import com.assignment.axientatestapplication.listners.UserOnClick;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity implements UserOnClick {
    UserListAdapter userListAdapter;
    RecyclerView userList;
    Context mContext;
    UserOnClick uploadOnClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        userList = (RecyclerView) findViewById(R.id.userList);
        mContext = UserListActivity.this;
        uploadOnClick = this;

        String tittle = Utils.getLoginName(mContext);
        setTitle(tittle);

        ArrayList<UserModel > userModelArrayList = DBUtils.getAllUsers(getApplicationContext());

        LinearLayoutManager userListRecyclerView = new LinearLayoutManager(mContext); // (Context context)
        userListAdapter = new UserListAdapter(mContext, userModelArrayList, uploadOnClick);
        userList.setAdapter(userListAdapter);
        userList.setLayoutManager(userListRecyclerView);
        userListRecyclerView.setOrientation(LinearLayoutManager.VERTICAL);
    }

    @Override
    public void onUserOnClick(String userID) {
        Intent intent = new Intent(getBaseContext(), UserDetailActivity.class);
        intent.putExtra("USER_ID", userID);
        startActivity(intent);
    }
}