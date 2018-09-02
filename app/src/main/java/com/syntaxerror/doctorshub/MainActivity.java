package com.syntaxerror.doctorshub;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout mAddDoctor;
    private LinearLayout mUpdateDoctor;
    private LinearLayout mViewDoctor;
    private LinearLayout mAddMedicalHistory;
    private LinearLayout mViewMedicalHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initButtons();
    }

    private void initButtons() {

        mAddDoctor = findViewById(R.id.addDoctorLayout);
        mUpdateDoctor = findViewById(R.id.updateDoctorLayout);
        mViewDoctor = findViewById(R.id.viewDoctorLayout);
        mAddMedicalHistory = findViewById(R.id.addMedicalHistory);
        mViewMedicalHistory = findViewById(R.id.viewMedicalHistory);

        mAddDoctor.setOnClickListener(this);
        mUpdateDoctor.setOnClickListener(this);
        mViewDoctor.setOnClickListener(this);
        mAddMedicalHistory.setOnClickListener(this);
        mViewMedicalHistory.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.addDoctorLayout:

                addDoctor();
                break;

            case R.id.updateDoctorLayout:

                updateDoctor();
                break;

            case R.id.viewDoctorLayout:

                viewDoctors();
                break;

            case R.id.addMedicalHistory:

                addMedicalHistory();
                break;

            case R.id.viewMedicalHistory:

                viewMedicalHistory();
                break;
        }
    }

    private void addDoctor() {

        Intent intent = new Intent(MainActivity.this, AddDoctor.class);

        startActivity(intent);
    }

    private void updateDoctor() {

        Intent intent = new Intent(MainActivity.this, UpdateDoctor.class);
        String fromStr = "MainActivity";

        intent.putExtra("Data", fromStr);
        startActivity(intent);
    }

    private void viewDoctors() {

        Intent intent = new Intent(MainActivity.this, ListDoctorActivity.class);

        startActivity(intent);
    }

    private void addMedicalHistory() {

        Intent intent = new Intent(MainActivity.this, AddMedicalHistoryActivity.class);

        startActivity(intent);
    }

    private void viewMedicalHistory() {

        Intent intent = new Intent(MainActivity.this, ViewMedicalHistoryActivity.class);

        startActivity(intent);
    }
}
