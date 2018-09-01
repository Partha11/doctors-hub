package com.syntaxerror.doctorshub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

public class ListDoctorActivity extends AppCompatActivity {

    private ListView mListView;
    private DoctorViewAdapter doctorViewAdapter;

    private DatabaseManager dbManager;

    private List<Doctor> doctorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_doctor);

        initAll();

        mListView.setAdapter(doctorViewAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(ListDoctorActivity.this, ShowIndividualDoctor.class);

                Gson gson = new Gson();

                String object = gson.toJson(doctorList.get(i));

                intent.putExtra("Doctor", object);

                startActivity(intent);
            }
        });
    }

    private void initAll() {

        mListView = findViewById(R.id.doctorListView);

        dbManager = new DatabaseManager(ListDoctorActivity.this);

        doctorList = dbManager.getAllData();

        if (dbManager.getAllData().isEmpty())

            Toast.makeText(this, "There are no doctors in the list!", Toast.LENGTH_SHORT).show();

        doctorViewAdapter = new DoctorViewAdapter(ListDoctorActivity.this, R.layout.doctor_model, doctorList);
    }
}
