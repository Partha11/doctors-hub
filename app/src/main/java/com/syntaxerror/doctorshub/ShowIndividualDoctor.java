package com.syntaxerror.doctorshub;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

public class ShowIndividualDoctor extends AppCompatActivity implements View.OnClickListener {

    private TextView showName;
    private TextView showDetails;
    private TextView showMail;
    private TextView showPhone;
    private TextView showAppointmentDate;

    private Button mUpdateDoctorButton;

    private ImageView imageView;

    private Doctor doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_individual_doctor);

        initFields();

        Gson gson = new Gson();

        String object = getIntent().getStringExtra("Doctor");
        doctor = gson.fromJson(object, Doctor.class);

        setFields();

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                call(doctor.getDoctorNumber());
            }
        });
    }

    public void call(String number) {

        Intent intent = new Intent(Intent.ACTION_CALL);

        intent.setData(Uri.parse("tel:" + number));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},400);
            return;
        }

        startActivity(intent);
    }

    private void initFields() {

        showName = findViewById(R.id.nameFieldIndividual);
        showAppointmentDate = findViewById(R.id.appointmentFieldIndividual);
        showMail = findViewById(R.id.emailFieldIndividual);
        showPhone = findViewById(R.id.phoneFieldIndividual);
        showDetails = findViewById(R.id.detailsFieldIndividual);

        mUpdateDoctorButton = findViewById(R.id.openUpdateDoctorButton);

        imageView = findViewById(R.id.doctorLogo);

        mUpdateDoctorButton.setOnClickListener(this);
    }

    @SuppressLint("ResourceType")
    private void setFields() {

        showName.setText(doctor.getDoctorName());
        showAppointmentDate.setText(doctor.getDoctorAppointment());
        showMail.setText(doctor.getDoctorEmail());
        showPhone.setText(doctor.getDoctorNumber());
        showDetails.setText(doctor.getDoctorDetails());

        imageView.setImageResource(R.raw.doctor_logo);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.openUpdateDoctorButton) {

            Intent intent = new Intent(ShowIndividualDoctor.this, UpdateDoctor.class);
            Gson gson = new Gson();
            String object = gson.toJson(doctor);

            intent.putExtra("Data", object);

            startActivity(intent);
            finish();
        }
    }
}
