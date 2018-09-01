package com.syntaxerror.doctorshub;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateDoctor extends AppCompatActivity implements View.OnClickListener, UpdateDoctorFragment.OnFragmentInteractionListener, DemoDoctorUpdateFragment.OnFragmentInteractionListener {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private UpdateDoctorFragment updateDoctorFragment;
    private DemoDoctorUpdateFragment demoDoctorUpdateFragment;

    private EditText searchName;

    private Button mSearchNameButton;

    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_doctor);

        demoDoctorUpdateFragment = new DemoDoctorUpdateFragment();
        updateDoctorFragment = new UpdateDoctorFragment();

        initFields();

        defaultFragment();
    }

    private void initFields() {

        searchName = findViewById(R.id.searchDoctorName);

        mSearchNameButton = findViewById(R.id.searchNameButton);

        mSearchNameButton.setOnClickListener(UpdateDoctor.this);

        dbManager = new DatabaseManager(UpdateDoctor.this);

        fragmentManager = getSupportFragmentManager();
    }

    private void defaultFragment() {

        fragmentTransaction = fragmentManager.beginTransaction().replace(R.id.fragmentLayout, demoDoctorUpdateFragment);

        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.searchNameButton:

                searchDoctor();
                break;
        }
    }

    private void searchDoctor() {

        String name = searchName.getText().toString();

        Doctor doctor = dbManager.getDataByName(name);

        if (doctor.getDoctorId() != 0) {

            Bundle bundle = new Bundle();

            bundle.putInt("ID", doctor.getDoctorId());
            bundle.putString("Name", doctor.getDoctorName());
            bundle.putString("Details", doctor.getDoctorDetails());
            bundle.putString("Appointment", doctor.getDoctorAppointment());
            bundle.putString("Number", doctor.getDoctorNumber());
            bundle.putString("Email", doctor.getDoctorEmail());

            updateDoctorFragment.setArguments(bundle);

            fragmentTransaction = fragmentManager.beginTransaction().replace(R.id.fragmentLayout, updateDoctorFragment);
            fragmentTransaction.commit();
        }

        else {

            defaultFragment();

            Toast.makeText(UpdateDoctor.this, "No Information Found About " + name, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void updateFields(Doctor doctor) {

        boolean isUpdated = dbManager.updateData(doctor);

        if (isUpdated)

            Toast.makeText(this, "Successfully Updated!!", Toast.LENGTH_SHORT).show();

        else

            Toast.makeText(this, "Failed to Update!!", Toast.LENGTH_SHORT).show();
    }
}
