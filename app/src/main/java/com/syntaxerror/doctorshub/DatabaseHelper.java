package com.syntaxerror.doctorshub;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 2;

    public static final String DB_NAME = "doctors_db";
    public static final String TABLE_NAME = "doctors";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "doctor_name";
    public static final String KEY_NUMBER = "doctor_number";
    public static final String KEY_EMAIL = "doctor_email";
    public static final String KEY_DETAILS = "doctor_details";
    public static final String KEY_APPOINTMENT = "doctor_appointment";

    public static final String TABLE_NAME_MEDICAL = "medical_history";
    public static final String KEY_ID_MEDICAL = "id";
    public static final String KEY_NAME_MEDICAL = "doctor_name";
    public static final String KEY_DETAILS_MEDICAL = "doctor_details";
    public static final String KEY_APPOINTMENT_MEDICAL = "doctor_appointment";
    public static final String KEY_IMAGE_MEDICAL = "prescription";

    private static String query = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME + " TEXT, " + KEY_NUMBER + " TEXT, " + KEY_EMAIL + " TEXT, " + KEY_DETAILS + " TEXT, "
            + KEY_APPOINTMENT + " TEXT)";

    private static String historyQuery = "CREATE TABLE " + TABLE_NAME_MEDICAL + "(" + KEY_ID_MEDICAL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME_MEDICAL + " TEXT, " + KEY_DETAILS_MEDICAL + " TEXT, " + KEY_APPOINTMENT_MEDICAL + " TEXT, "
            + KEY_IMAGE_MEDICAL + " TEXT)";

    public DatabaseHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.execSQL(historyQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}