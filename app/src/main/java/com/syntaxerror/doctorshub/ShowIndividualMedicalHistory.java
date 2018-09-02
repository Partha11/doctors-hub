package com.syntaxerror.doctorshub;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;

public class ShowIndividualMedicalHistory extends AppCompatActivity {

    private TextView showName;
    private TextView showDetails;
    private TextView showMail;
    private TextView showPhone;
    private TextView showAppointmentDate;

    private ImageView imageView;

    private MedicalHistory medicalHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_individual_medical_history);

        initFields();

        Gson gson = new Gson();

        String object = getIntent().getStringExtra("Medical History");

        medicalHistory = gson.fromJson(object, MedicalHistory.class);

        setFields();
    }

    private void initFields() {

        showName = findViewById(R.id.nameFieldHistoryIndividual);
        showAppointmentDate = findViewById(R.id.appointmentFieldHistoryIndividual);
        showDetails = findViewById(R.id.detailsFieldHistoryIndividual);

        imageView = findViewById(R.id.doctorLogoHistory);
    }

    private void setFields() {

        showName.setText(medicalHistory.getDoctorName());
        showAppointmentDate.setText(medicalHistory.getAppointmentDate());
        showDetails.setText(medicalHistory.getDoctorDetails());

        imageView.setImageBitmap(Image.decodeBase64(medicalHistory.getPrescription()));
    }
}
