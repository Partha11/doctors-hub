package com.syntaxerror.doctorshub;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class AddDoctor extends AppCompatActivity implements View.OnClickListener {

    private EditText inputFirstName;
    private EditText inputLastName;
    private EditText inputDetails;
    private EditText inputMail;
    private EditText inputPhone;
    private EditText inputAppointmentDate;

    private Button mSaveButton;

    private DatePickerDialog datePickerDialog;

    private Calendar calender;

    private String creationTime;

    private DatabaseManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);

        initFields();
    }

    private void initFields() {

        inputFirstName = findViewById(R.id.inputFirstNameField);
        inputLastName = findViewById(R.id.inputLastNameField);
        inputAppointmentDate = findViewById(R.id.inputAppointmentField);
        inputMail = findViewById(R.id.inputEmailField);
        inputPhone = findViewById(R.id.inputPhoneField);
        inputDetails = findViewById(R.id.inputDetailsField);

        mSaveButton = findViewById(R.id.saveDoctor);

        dbManager = new DatabaseManager(AddDoctor.this);

        mSaveButton.setOnClickListener(this);
        inputAppointmentDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.saveDoctor:

                saveDoctor();
                break;

            case R.id.inputAppointmentField:

                appointmentDateSelection();
                break;
        }
    }

    private void saveDoctor() {

        String firstName = inputFirstName.getText().toString().trim();
        String lastName = inputLastName.getText().toString().trim();
        String number = inputPhone.getText().toString().trim();
        String appointment = creationTime;
        String email = inputMail.getText().toString().trim();
        String details = inputDetails.getText().toString().trim();


        if (firstName.length() == 0 || lastName.length() == 0 || number.length() == 0 || email.length() == 0 || details.length() == 0 || appointment.length() == 0) {

            if (firstName.length() == 0)

                inputFirstName.setError("Please Enter First Name!!");

            else if (lastName.length() == 0)

                inputLastName.setError("Please Enter Last Name!!");

            else if (number.length() == 0)

                inputPhone.setError("Please Enter Phone Number!!");

            else if (email.length() == 0)

                inputMail.setError("Please Enter Email Address!!");

            else if (details.length() == 0)

                inputDetails.setError("Please Enter Details!!");

            else if (appointment.length() == 0)

                inputAppointmentDate.setError("Please Select Date!!");

            return;
        }

        String name = firstName + " " + lastName;

        Doctor doctor = new Doctor(name, number, details, appointment, email);

        boolean isInserted = dbManager.insertData(doctor);

        if (isInserted)

            Toast.makeText(this, "Inserted!!", Toast.LENGTH_SHORT).show();

        else

            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
    }

    private void appointmentDateSelection() {

        calender = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(AddDoctor.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                creationTime = day + "-" + (month + 1) + "-" + year;

                inputAppointmentDate.setText(creationTime);
            }
        }, 2018, 7, 29);

        datePickerDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.save_action, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_save) {

            saveDoctor();
        }

        return super.onOptionsItemSelected(item);
    }
}
