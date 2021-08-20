package com.assignment.axientatestapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.assignment.axientatestapplication.Helper.Utils;
import com.assignment.axientatestapplication.data.UserModel;
import com.assignment.axientatestapplication.database.DBUtils;
import com.assignment.axientatestapplication.network.HandleApiResponse;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etUserName,etPassword;
    LinearLayout networkLayout;
    RelativeLayout dataLayout ;
    Button btnSignIn;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;

        etUserName = findViewById(R.id.et_stUserName);
        etPassword = findViewById(R.id.et_stUsePassword);

        btnSignIn = findViewById(R.id.cirLoginButton);
        btnSignIn.setOnClickListener(this);
        DBUtils.deleteAllUsers(mContext);


    }

    private void customToast(String toastMessage,String type) {
        LayoutInflater inflater = getLayoutInflater();

        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_layout_id));

// set a message
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(toastMessage);

        if(type.equalsIgnoreCase("error")){
            text.setBackgroundColor(mContext.getResources().getColor(R.color.red));
        }else {
            text.setBackgroundColor(mContext.getResources().getColor(R.color.grea));
        }
// Toast...
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    @Override
    public void onClick(View v) {
        LoginProcess();
    }

    private void LoginProcess() {
        try{
            if (Utils.isConnected(mContext)) {
                final String username = etUserName.getText().toString().trim();
                final String password = etPassword.getText().toString().trim();

                if (TextUtils.isEmpty(username)) {
                    etUserName.setError(getString(R.string.userName_required));
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    etPassword.setError(getString(R.string.password_required));
                    return;
                }

                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wInfo = wifiManager.getConnectionInfo();
                @SuppressLint("HardwareIds") String macID = wInfo.getMacAddress();
                int versionNo = Build.VERSION.SDK_INT;
                @SuppressLint("HardwareIds") String deviceID = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                loginService(username,password,macID,String.valueOf(versionNo),deviceID);

            }else {
                NetworkConnectionSettings();
            }
        }catch (Exception e){

            e.printStackTrace();
        }

    }

    void loginService(String userName, String password, String macID , String versionNo , String deviceID){
        HandleApiResponse handleApiResponse = new HandleApiResponse(this,"http://www.axienta.lk/VantageCoreWebAPI/api/");
        handleApiResponse.login(userName, password, macID, versionNo, deviceID ,new HandleApiResponse.CallBackDelegate() {
            @Override
            public void onResponseSuccess(String result) {
                Log.e("login", "result "+result);
                if(result.equalsIgnoreCase("Active for another device")){
                    customToast(result,"error");
                   // userService();
                }else {
                    userService();
                }

            }

            @Override
            public void onFailure(String error) {
                customToast(error,"error");
            }
        });
    }

    void userService(){
        HandleApiResponse handleApiResponse = new HandleApiResponse(this,"https://jsonplaceholder.typicode.com/");
        handleApiResponse.getAllUsers(new HandleApiResponse.CallBackUsersDelegate() {
            @Override
            public void onResponseSuccess(List<UserModel> userModelList) {
                Log.e("getAllUsers", "result "+userModelList.size());
                int i = 0;
                for (UserModel userModel : userModelList){
                    i++;
                    Log.e("getAllUsers", "userModel "+userModel.getName());
                    DBUtils.insert_users(userModel,mContext);
                }
                if(i== userModelList.size()) {

                    startActivity(new Intent(MainActivity.this, UserListActivity.class));
                }
            }

            @Override
            public void onFailure(String error) {
                customToast(error,"error");
            }
        });
    }

    private void NetworkConnectionSettings(){
        android.app.AlertDialog.Builder dialogInfo = new android.app.AlertDialog.Builder(mContext);
        dialogInfo.setView(R.layout.alert_dialog_network);
        dialogInfo.setTitle(getString(R.string.internet_connection));
        dialogInfo.setMessage(getString(R.string.your_not_connect_with_internet));
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
        mNegativeBtn = dialogBoxInfoNew.findViewById(R.id.close_btn);
        mPositiveBtn = dialogBoxInfoNew.findViewById(R.id.add_btn);

        mPositiveBtn.setText(getString(R.string.setting));
        mNegativeBtn.setText(getString(R.string.exit));
        mPositiveBtn
                .setOnClickListener(v -> {
                    dialogBoxInfoNew.dismiss();
                    startActivity(new Intent(
                            Settings.ACTION_WIFI_SETTINGS));
                });

        mNegativeBtn
                .setOnClickListener(v -> {
                    dialogBoxInfoNew.dismiss();
                    finish();

                });
    }


}