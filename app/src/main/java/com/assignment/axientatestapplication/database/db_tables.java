package com.assignment.axientatestapplication.database;

public class db_tables {

    static final String TABLE_NAME_USER = "USER";

    public  static final  String USER_ID                = "_id";
    static  final  String USER_UN_ID                = "user_id";
    static  final  String USER_NAME                     = "user_name";
    static  final  String USER_ACCESS_NAME              = "user_access_name";
    static  final  String USER_EMAIL                    = "user_emil";
    static  final  String USER_STREET                   = "user_street";
    static  final  String USER_SUIT                     = "user_suite";
    static  final  String USER_CITY                     = "user_city";
    static  final  String USER_ZIPCODE                  = "user_zipcode";
    static  final  String USER_LAT                      = "user_lat";
    static  final  String USER_LNG                      = "user_lng";
    static  final  String USER_PHONE                    = "user_phone";
    static  final  String USER_WEBSITE                  = "user_website";
    static  final  String USER_COMPANY_NAME             = "user_company_name";
    static  final  String USER_COMPANY_CATCH_PHRASE     = "user_company_catch_phrase";
    static  final  String USER_COMPANY_BS               = "user_company_bs";

    static final String TABLE_NAME_TASK = "TASK";

    public  static final  String TASK_ID                = "_id";
    static  final  String TASK_USER_ID                  = "user_id";
    static  final  String TASK_NAME                     = "task_name";
    static  final  String TASK_DATE                     = "task_date";

    static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_USER
            + " ( " +
            USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            USER_UN_ID + " TEXT, " +
            USER_NAME + " TEXT, " +
            USER_ACCESS_NAME + " TEXT, " +
            USER_EMAIL + " TEXT, " +
            USER_STREET + " TEXT, " +
            USER_SUIT + " TEXT, " +
            USER_CITY + " TEXT, " +
            USER_ZIPCODE + " TEXT, " +
            USER_LAT + " TEXT, " +
            USER_LNG + " TEXT, " +
            USER_PHONE + " TEXT, " +
            USER_WEBSITE + " TEXT, " +
            USER_COMPANY_NAME + " TEXT, " +
            USER_COMPANY_CATCH_PHRASE + " TEXT, " +
            USER_COMPANY_BS + " TEXT" +
            ");";

    static final String CREATE_TABLE_TASK = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TASK
            + " ( " +
            TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TASK_USER_ID + " TEXT, " +
            TASK_DATE + " TEXT, " +
            TASK_NAME + " TEXT" +
            ");";

}
