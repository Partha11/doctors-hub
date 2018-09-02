package com.syntaxerror.doctorshub;

import android.Manifest;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class AddMedicalHistoryActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText doctorName;
    private EditText doctorDetails;
    private TextView appointmentDate;
    private TextView takePhoto;

    private ImageView mImageView;

    private Calendar calendar;

    private String creationTime;
    private String mPhotoData;

    private DatabaseManager dbManager;

    private DatePickerDialog datePickerDialog;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PERMISSION_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medical_history);

        initFields();

        ActionBar actionBar = getActionBar();

        if (actionBar != null)

            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void initFields() {

        doctorName = findViewById(R.id.doctorNameHistory);
        doctorDetails = findViewById(R.id.doctorDetailsHistory);
        appointmentDate = findViewById(R.id.appointmentDateHistory);
        takePhoto = findViewById(R.id.takePhoto);
        mImageView = findViewById(R.id.medicalHistoryImageView);

        dbManager = new DatabaseManager(this);

        appointmentDate.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.appointmentDateHistory:

                datePicker();
                break;

            case R.id.takePhoto:

                takePhotoClicked();
                break;
        }
    }

    private void datePicker() {

        calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(AddMedicalHistoryActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                creationTime = day + "-" + (month + 1) + "-" + year;

                appointmentDate.setText(creationTime);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            mPhotoData = Image.encodeToBase64(imageBitmap,Bitmap.CompressFormat.JPEG, 90);
            mImageView.setImageBitmap(imageBitmap);
        }
    }

    private void takePhotoClicked() {

        if (checkPermission())

            dispatchTakePictureIntent();

        else

            requestPermission();
    }

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private boolean checkPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            return false;
        }

        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {

            case PERMISSION_REQUEST_CODE:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    dispatchTakePictureIntent();
                    return;
                }

                else {

                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",

                                    new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                        requestPermission();
                                    }
                                }
                            });
                        }
                    }
                }

            break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void saveMedicalHistory() {

        String name = doctorName.getText().toString().trim();
        String details = doctorDetails.getText().toString().trim();

        if (name.length() == 0 || details.length() == 0 || mPhotoData.length() == 0) {

            if (name.length() == 0)

                doctorName.setError("Please Enter Doctor's Name!!");

            else if (details.length() == 0)

                doctorName.setError("Please Enter Doctor's Details!!");

            else if (creationTime.length() == 0)

                appointmentDate.setError("Please Select Date!!");

            return;
        }

        MedicalHistory medicalHistory = new MedicalHistory(name, details, creationTime, mPhotoData);

        boolean isInserted = dbManager.insertHistory(medicalHistory);

        if (isInserted)

            Toast.makeText(this, "Inserted!!", Toast.LENGTH_SHORT).show();

        else

            Toast.makeText(this, "Failed!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.save_action, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_save) {

            saveMedicalHistory();
        }

        else {

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivityForResult(intent, 0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
