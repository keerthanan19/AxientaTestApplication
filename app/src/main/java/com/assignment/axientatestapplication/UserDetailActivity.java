package com.assignment.axientatestapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.assignment.axientatestapplication.adapter.TaskListAdapter;
import com.assignment.axientatestapplication.adapter.UserListAdapter;
import com.assignment.axientatestapplication.data.TaskModel;
import com.assignment.axientatestapplication.data.UserModel;
import com.assignment.axientatestapplication.database.DBUtils;

import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UserDetailActivity extends AppCompatActivity {
    UserModel userModel ;
    TaskListAdapter taskListAdapter;
    RecyclerView TaskList;
    Context mContext;
    ArrayList<TaskModel> taskModelArrayList ;
    TextView warning;
    String userID = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_deatil_activity);

        userID = getIntent().getStringExtra("USER_IDge");

        TaskList = (RecyclerView) findViewById(R.id.TaskList);
        warning = (TextView) findViewById(R.id.warning);
        mContext = UserDetailActivity.this;

         taskModelArrayList = DBUtils.getTAskByID(getApplicationContext(),userID);

         if(!taskModelArrayList.isEmpty()) {
             warning.setVisibility(View.GONE);
             TaskList.setVisibility(View.VISIBLE);
             LinearLayoutManager TaskListRecyclerView = new LinearLayoutManager(mContext); // (Context context)
             taskListAdapter = new TaskListAdapter(mContext, taskModelArrayList);
             TaskList.setAdapter(taskListAdapter);
             TaskList.setLayoutManager(TaskListRecyclerView);
             TaskListRecyclerView.setOrientation(LinearLayoutManager.VERTICAL);
         }else {
             warning.setVisibility(View.VISIBLE);
             TaskList.setVisibility(View.GONE);
         }
        
        userModel = DBUtils.getUserByID(this,userID);

         TextView id = findViewById(R.id.id);
         TextView name = findViewById(R.id.name);
         TextView username = findViewById(R.id.username);
         TextView email = findViewById(R.id.email);
         TextView phone = findViewById(R.id.phone);
         TextView website = findViewById(R.id.website);
         TextView companyName = findViewById(R.id.companyName);
         TextView companyCatchPhrase = findViewById(R.id.companyCatchPhrase);
         TextView companyBs = findViewById(R.id.companyBs);
         TextView street = findViewById(R.id.street);
         TextView suite = findViewById(R.id.suite);
         TextView zipcode = findViewById(R.id.zipcode);
         TextView lat = findViewById(R.id.lat);
         TextView lng = findViewById(R.id.lng);

         if(userModel != null) {

             id.setText(userModel.getId());
             name.setText(userModel.getName());
             username.setText(userModel.getUsername());
             email.setText(userModel.getEmail());
             phone.setText(userModel.getPhone());
             website.setText(userModel.getWebsite());
             companyName.setText(userModel.getCompany_name());
             companyCatchPhrase.setText(userModel.getCompany_catchPhrase());
             companyBs.setText(userModel.getCompany_bs());
             street.setText(userModel.getStreet());
             suite.setText(userModel.getSuite());
             zipcode.setText(userModel.getZipcode());
             lat.setText(userModel.getLat());
             lng.setText(userModel.getLng());
         }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dash_bord_menu, menu);

        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_add_task){
            addTask();
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addTask(){
        android.app.AlertDialog.Builder dialogInfo = new android.app.AlertDialog.Builder(mContext);
        dialogInfo.setView(R.layout.add_task_layout);
        dialogInfo.setTitle("Task");
        dialogInfo.setMessage("add user Task");
        dialogInfo.setCancelable(false);

        final android.app.AlertDialog dialogBoxInfoNew = dialogInfo.create();
        dialogBoxInfoNew
                .setOnCancelListener(
                        new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                            }
                        }
                );

        dialogBoxInfoNew.show();

        TextView mNegativeBtn;
        TextView mPositiveBtn;
        EditText task_text;
        mNegativeBtn = dialogBoxInfoNew.findViewById(R.id.close_btn);
        mPositiveBtn = dialogBoxInfoNew.findViewById(R.id.add_btn);
        task_text = dialogBoxInfoNew.findViewById(R.id.task_text);

        mPositiveBtn.setText("ADD");
        mNegativeBtn.setText(getString(R.string.exit));
        mPositiveBtn
                .setOnClickListener(v -> {
                    dialogBoxInfoNew.dismiss();
                    TaskModel taskModel = new TaskModel();
                    if(!task_text.getText().toString().isEmpty()) {
                        taskModel.setTaskName(task_text.getText().toString());
                        taskModel.setTaskUserID(userID);
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();
                        System.out.println(dtf.format(now));
                        taskModel.setTaskDate(dtf.format(now));

                        DBUtils.insert_Task(taskModel,mContext);
                        dialogBoxInfoNew.dismiss();
                        finish();

                    }else {
                        Toast.makeText(mContext,"Task Can't be Empty",Toast.LENGTH_LONG).show();
                    }
                });

        mNegativeBtn
                .setOnClickListener(v -> {
                    dialogBoxInfoNew.dismiss();
                    finish();

                });
    }

}