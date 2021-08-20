package com.assignment.axientatestapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.assignment.axientatestapplication.data.TaskModel;
import com.assignment.axientatestapplication.data.UserModel;

import java.util.ArrayList;

public class DBUtils {
    private static db_manager db_manager;
    private static final String TAG = "DBUtils";

    public static boolean insert_users(UserModel data, Context mContext){
        boolean isSuccess = false;
        db_manager = new db_manager(mContext);
        try (SQLiteDatabase db = db_manager.getWritableDatabase()) {

            ContentValues cv = new ContentValues();
            cv.put(db_tables.USER_UN_ID, data.getId());
            cv.put(db_tables.USER_NAME, data.getName());
            cv.put(db_tables.USER_ACCESS_NAME, data.getUsername());
            cv.put(db_tables.USER_EMAIL, data.getEmail());
            cv.put(db_tables.USER_PHONE, data.getPhone());
            cv.put(db_tables.USER_WEBSITE, data.getWebsite());
            cv.put(db_tables.USER_STREET, data.getStreet());
            cv.put(db_tables.USER_SUIT, data.getSuite());
            cv.put(db_tables.USER_ZIPCODE, data.getZipcode());
            cv.put(db_tables.USER_LAT, data.getLat());
            cv.put(db_tables.USER_LNG, data.getLng());
            cv.put(db_tables.USER_COMPANY_NAME, data.getCompany_name());
            cv.put(db_tables.USER_COMPANY_CATCH_PHRASE, data.getCompany_catchPhrase());
            cv.put(db_tables.USER_COMPANY_BS, data.getCompany_bs());

            try{
                long rowCount = db.insertOrThrow(db_tables.TABLE_NAME_USER, null, cv);
                if (rowCount != -1){
                    Log.d(TAG, "insert_users " + " inserted");
                    isSuccess = true;
                }else{
                    Log.e(TAG, "insert_users " + " insert failed");
                    isSuccess = false;
                }
            }catch (Exception e){
                try{
                    long rowCount = db.replaceOrThrow(db_tables.TABLE_NAME_USER, null, cv);
                    if (rowCount != -1){
                        Log.d(TAG, "insert_users " + " replaced");
                        isSuccess = true;
                    }else{
                        Log.e(TAG, "insert_users " + " replaced failed");
                        isSuccess = false;
                    }
                }catch (Exception ie){
                    Log.e(TAG, "insert_users " + " inserted or replaced failed  :"+ ie.getMessage());
                    isSuccess = false;
                }
                isSuccess = false;
            }
        }
        return isSuccess;
    }

    public static boolean insert_Task(TaskModel data, Context mContext){
        boolean isSuccess = false;
        db_manager = new db_manager(mContext);
        try (SQLiteDatabase db = db_manager.getWritableDatabase()) {

            ContentValues cv = new ContentValues();

            cv.put(db_tables.TASK_NAME, data.getTaskName());
            cv.put(db_tables.TASK_USER_ID, data.getTaskUserID());
            cv.put(db_tables.TASK_DATE, data.getTaskDate());

            try{
                long rowCount = db.insertOrThrow(db_tables.TABLE_NAME_TASK, null, cv);
                if (rowCount != -1){
                    Log.d(TAG, "insert_Task " + " inserted");
                    isSuccess = true;
                }else{
                    Log.e(TAG, "insert_Task " + " insert failed");
                    isSuccess = false;
                }
            }catch (Exception e){
                try{
                    long rowCount = db.replaceOrThrow(db_tables.TABLE_NAME_USER, null, cv);
                    if (rowCount != -1){
                        Log.d(TAG, "insert_Task " + " replaced");
                        isSuccess = true;
                    }else{
                        Log.e(TAG, "insert_Task " + " replaced failed");
                        isSuccess = false;
                    }
                }catch (Exception ie){
                    Log.e(TAG, "insert_Task " + " inserted or replaced failed  :"+ ie.getMessage());
                    isSuccess = false;
                }
                isSuccess = false;
            }
        }
        return isSuccess;
    }


    public static ArrayList<UserModel> getAllUsers(Context mContext) {
        ArrayList<UserModel>  userModelArrayList = new ArrayList<>();
        db_manager = new db_manager(mContext);
        Cursor c;
        String qty = "0";

        StringBuilder query = new StringBuilder("SELECT * FROM " + db_tables.TABLE_NAME_USER
                + " ORDER BY " + db_tables.USER_NAME + " ASC"
        );

        try (SQLiteDatabase db = db_manager.getWritableDatabase()) {
            c = db.rawQuery(query.toString(), null);
            if (c.moveToFirst()) {
                do {
                    UserModel userModel = new UserModel();

                    userModel.setId(Integer.parseInt(c.getString(c.getColumnIndex(db_tables.USER_UN_ID))));
                    userModel.setName(c.getString(c.getColumnIndex(db_tables.USER_NAME)));
                    userModel.setUsername(c.getString(c.getColumnIndex(db_tables.USER_ACCESS_NAME)));
                    userModel.setEmail(c.getString(c.getColumnIndex(db_tables.USER_EMAIL)));
                    userModel.setPhone(c.getString(c.getColumnIndex(db_tables.USER_PHONE)));
                    userModel.setWebsite(c.getString(c.getColumnIndex(db_tables.USER_WEBSITE)));

                    userModel.setCompany_name(c.getString(c.getColumnIndex(db_tables.USER_ACCESS_NAME)));
                    userModel.setCompany_catchPhrase(c.getString(c.getColumnIndex(db_tables.USER_COMPANY_CATCH_PHRASE)));
                    userModel.setCompany_bs(c.getString(c.getColumnIndex(db_tables.USER_COMPANY_BS)));

                    userModel.setStreet(c.getString(c.getColumnIndex(db_tables.USER_STREET)));
                    userModel.setSuite(c.getString(c.getColumnIndex(db_tables.USER_SUIT)));
                    userModel.setZipcode(c.getString(c.getColumnIndex(db_tables.USER_ZIPCODE)));
                    userModel.setLat(c.getString(c.getColumnIndex(db_tables.USER_LAT)));
                    userModel.setLng(c.getString(c.getColumnIndex(db_tables.USER_LNG)));

                    userModelArrayList.add(userModel);
                } while (c.moveToNext());
            }
            return userModelArrayList;
        } catch (Exception e) {
            Log.e(TAG, "getAllUsers " + e.toString());
        }
        return userModelArrayList;
    }


    public static UserModel getUserByID(Context mContext,String userID) {
        db_manager = new db_manager(mContext);
        Cursor c;
        String qty = "0";
        UserModel userModel = new UserModel();

        StringBuilder query = new StringBuilder("SELECT * FROM " + db_tables.TABLE_NAME_USER
                + " where " + db_tables.USER_UN_ID + "='" + userID +"'"
                + " ORDER BY " + db_tables.USER_NAME + " ASC"
        );

        try (SQLiteDatabase db = db_manager.getWritableDatabase()) {
            c = db.rawQuery(query.toString(), null);
            if (c.moveToFirst()) {
                do {


                    userModel.setId(Integer.parseInt(c.getString(c.getColumnIndex(db_tables.USER_UN_ID))));
                    userModel.setName(c.getString(c.getColumnIndex(db_tables.USER_NAME)));
                    userModel.setUsername(c.getString(c.getColumnIndex(db_tables.USER_ACCESS_NAME)));
                    userModel.setEmail(c.getString(c.getColumnIndex(db_tables.USER_EMAIL)));
                    userModel.setPhone(c.getString(c.getColumnIndex(db_tables.USER_PHONE)));
                    userModel.setWebsite(c.getString(c.getColumnIndex(db_tables.USER_WEBSITE)));

                    userModel.setCompany_name(c.getString(c.getColumnIndex(db_tables.USER_ACCESS_NAME)));
                    userModel.setCompany_catchPhrase(c.getString(c.getColumnIndex(db_tables.USER_COMPANY_CATCH_PHRASE)));
                    userModel.setCompany_bs(c.getString(c.getColumnIndex(db_tables.USER_COMPANY_BS)));

                    userModel.setStreet(c.getString(c.getColumnIndex(db_tables.USER_STREET)));
                    userModel.setSuite(c.getString(c.getColumnIndex(db_tables.USER_SUIT)));
                    userModel.setZipcode(c.getString(c.getColumnIndex(db_tables.USER_ZIPCODE)));
                    userModel.setLat(c.getString(c.getColumnIndex(db_tables.USER_LAT)));
                    userModel.setLng(c.getString(c.getColumnIndex(db_tables.USER_LNG)));

                } while (c.moveToNext());
            }
            return userModel;
        } catch (Exception e) {
            Log.e(TAG, "getUserByID " + e.toString());
        }
        return userModel;
    }

    public static ArrayList<TaskModel> getTAskByID(Context mContext, String userID) {
        db_manager = new db_manager(mContext);
        Cursor c;
        String qty = "0";

        ArrayList<TaskModel> taskModelArrayList = new ArrayList<>();

        StringBuilder query = new StringBuilder("SELECT * FROM " + db_tables.TABLE_NAME_USER
                + " where " + db_tables.TASK_USER_ID + "='" + userID +"'"
                + " ORDER BY " + db_tables.TASK_DATE + " ASC"
        );

        try (SQLiteDatabase db = db_manager.getWritableDatabase()) {
            c = db.rawQuery(query.toString(), null);
            if (c.moveToFirst()) {
                do {

                    TaskModel taskModel = new TaskModel();
                    taskModel.setTaskID(c.getString(c.getColumnIndex(db_tables.TASK_ID)));
                    taskModel.setTaskUserID(c.getString(c.getColumnIndex(db_tables.TASK_USER_ID)));
                    taskModel.setTaskName(c.getString(c.getColumnIndex(db_tables.TASK_NAME)));
                    taskModel.setTaskDate(c.getString(c.getColumnIndex(db_tables.TASK_DATE)));

                    taskModelArrayList.add(taskModel);

                } while (c.moveToNext());
            }
            return taskModelArrayList;
        } catch (Exception e) {
            Log.e(TAG, "getTAskByID " + e.toString());
        }
        return taskModelArrayList;
    }


    public static void deleteAllUsers(Context context) {
        db_manager = new db_manager(context);
        SQLiteDatabase db = db_manager.getReadableDatabase();
        db.execSQL("delete from " + db_tables.TABLE_NAME_USER);
    }
}
