package com.syntaxerror.doctorshub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ViewMedicalHistoryActivity extends AppCompatActivity {

    private ListView mListView;
    private MedicalHistoryAdapter medicalHistoryAdapter;

    private DatabaseManager dbManager;

    private List<MedicalHistory> medicalHistoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_medical_history);

        initAll();

        mListView.setAdapter(medicalHistoryAdapter);

        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(ViewMedicalHistoryActivity.this, ShowIndividualMedicalHistory.class);

                Gson gson = new Gson();

                String object = gson.toJson(medicalHistoryList.get(i));

                intent.putExtra("Medical History", object);

                startActivity(intent);

            }
        });
    }

    private void initAll() {

        mListView = findViewById(R.id.medicalHistoryListView);

        dbManager = new DatabaseManager(ViewMedicalHistoryActivity.this);

        medicalHistoryList = dbManager.getAllMedicalHistory();

        if (dbManager.getAllData().isEmpty())

            Toast.makeText(this, "There are no prescriptions in the list!", Toast.LENGTH_SHORT).show();

        medicalHistoryAdapter = new MedicalHistoryAdapter(ViewMedicalHistoryActivity.this, R.layout.history_model, medicalHistoryList);
    }
}
