package com.syntaxerror.doctorshub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private DatabaseHelper helper;
    private SQLiteDatabase database;

    public DatabaseManager(Context context) {

        helper = new DatabaseHelper(context);
    }

    public boolean insertData(Doctor doctor) {

        database = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseHelper.KEY_NAME, doctor.getDoctorName());
        contentValues.put(DatabaseHelper.KEY_NUMBER, doctor.getDoctorNumber());
        contentValues.put(DatabaseHelper.KEY_EMAIL, doctor.getDoctorEmail());
        contentValues.put(DatabaseHelper.KEY_DETAILS, doctor.getDoctorDetails());
        contentValues.put(DatabaseHelper.KEY_APPOINTMENT, doctor.getDoctorAppointment());

        long isInserted = database.insert(DatabaseHelper.TABLE_NAME, null, contentValues);

        database.close();

        if (isInserted > 0)

            return true;

        else

            return false;
    }

    public boolean insertHistory(MedicalHistory history) {

        database = helper.getWritableDatabase();

        if (history == null)

            return false;

        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseHelper.KEY_NAME_MEDICAL, history.getDoctorName());
        contentValues.put(DatabaseHelper.KEY_DETAILS_MEDICAL, history.getDoctorDetails());
        contentValues.put(DatabaseHelper.KEY_APPOINTMENT_MEDICAL, history.getAppointmentDate());
        contentValues.put(DatabaseHelper.KEY_IMAGE_MEDICAL, history.getPrescription());

        long isInserted = database.insert(DatabaseHelper.TABLE_NAME_MEDICAL, null, contentValues);

        database.close();

        if (isInserted > 0)

            return true;

        else

            return false;
    }

    public List<Doctor> getAllData() {

        database = helper.getReadableDatabase();

        List<Doctor> doctorList = new ArrayList<>();

        Cursor cursor;

        String query = "SELECT * FROM " + DatabaseHelper.TABLE_NAME;

        cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {

            do {

                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_ID));

                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_NUMBER));
                String email = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_EMAIL));
                String details = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_DETAILS));
                String appointment = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_APPOINTMENT));

                doctorList.add(new Doctor(id, name, number, details, appointment, email));

            } while (cursor.moveToNext());

            database.close();
        }

        return doctorList;
    }

    public List<MedicalHistory> getAllMedicalHistory() {

        database = helper.getReadableDatabase();

        List<MedicalHistory> prescriptionList = new ArrayList<>();

        Cursor cursor;

        String query = "SELECT * FROM " + DatabaseHelper.TABLE_NAME_MEDICAL + ";";

        cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {

            do {

                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_ID_MEDICAL));

                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_NAME_MEDICAL));
                String details = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_DETAILS_MEDICAL));
                String appointment = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_APPOINTMENT_MEDICAL));
                String prescription = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_IMAGE_MEDICAL));

                prescriptionList.add(new MedicalHistory(id, name, details, appointment, prescription));

            } while (cursor.moveToNext());

            database.close();
        }

        return prescriptionList;
    }

    public Doctor getDataByName(String name) {

        database = helper.getReadableDatabase();

        Cursor cursor;

        Doctor Doctor = new Doctor();

        String query = "SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.KEY_NAME +
                        " LIKE '%" + name + "%';";

        cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {

            int doctorId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_ID));

            String doctorName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_NAME));
            String doctorNumb = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_NUMBER));
            String doctorMail = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_EMAIL));
            String doctorDetails = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_DETAILS));
            String doctorAppointment = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_APPOINTMENT));


            Doctor = new Doctor(doctorId, doctorName, doctorNumb, doctorDetails, doctorAppointment, doctorMail);

            database.close();
        }

        return Doctor;
    }

    public boolean updateData(Doctor doctor) {

        database = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseHelper.KEY_NAME, doctor.getDoctorName());
        contentValues.put(DatabaseHelper.KEY_NUMBER, doctor.getDoctorNumber());
        contentValues.put(DatabaseHelper.KEY_EMAIL, doctor.getDoctorEmail());
        contentValues.put(DatabaseHelper.KEY_DETAILS, doctor.getDoctorDetails());
        contentValues.put(DatabaseHelper.KEY_APPOINTMENT, doctor.getDoctorAppointment());

        String whereClause = DatabaseHelper.KEY_ID + " = ?";
        String[] whereArgs = {String.valueOf(doctor.getDoctorId())};

        int isUpdated = database.update(DatabaseHelper.TABLE_NAME, contentValues, whereClause, whereArgs);

        database.close();

        if (isUpdated > 0)

            return true;

        else

            return false;
    }

    public boolean deleteData(Doctor doctor) {

        database = helper.getWritableDatabase();

        String whereClause = DatabaseHelper.KEY_ID + " = ?";
        String[] whereArgs = {String.valueOf(doctor.getDoctorId())};

        int isDeleted = database.delete(DatabaseHelper.TABLE_NAME, whereClause, whereArgs);

        if (isDeleted > 0)

            return true;

        else

            return false;
    }
}