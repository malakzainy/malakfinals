package com.example.malakfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChildReport extends AppCompatActivity {
    private TextView tvHeader;
    private TextView clinic;
    private TextView Name;
    private TextView etName;
    private TextView Age;
    private EditText etAge;
    private TextView Patient;
    private TextView etPatientId;
    private TextView Diagnosis;
    private TextView etDiagnosis;
    private TextView LastV;
    private TextView etLastVisit;
    private TextView Medications;
    private TextView etMedications;
    private TextView DN;
    private TextView etNotes;
    private TextView PeakFlow;
    private TextView etPeakFlow;
    private Button btnPrint;
    private Button btnSave;
    private Button btnClear;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_child_report);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvHeader = findViewById(R.id.tvHeader);
        clinic = findViewById(R.id.clinic);
        Name = findViewById(R.id.Name);
        etName = findViewById(R.id.etName);
        Age = findViewById(R.id.Age);
        etAge = findViewById(R.id.main);
        Patient = findViewById(R.id.Patient);
        etPatientId = findViewById(R.id.etPatientId);
        Diagnosis = findViewById(R.id.Diagnosis);
        etDiagnosis = findViewById(R.id.etDiagnosis);
        LastV = findViewById(R.id.LastV);
        etLastVisit = findViewById(R.id.etLastVisit);
        Medications = findViewById(R.id.Medications);
        etMedications = findViewById(R.id.etMedications);
        DN = findViewById(R.id.DN);
        etNotes = findViewById(R.id.etNotes);
        PeakFlow = findViewById(R.id.PeakFlow);
        etPeakFlow = findViewById(R.id.etPeakFlow);
        btnPrint = findViewById(R.id.btnPrint);
        btnSave = findViewById(R.id.btnSave);
        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(ChildReport.this, PlantScan.class);
               startActivity(intent);
            }
        });
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(ChildReport.this, ScanResult.class);
               startActivity(intent);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(ChildReport.this, PlantScan.class);
               startActivity(intent);
            }
        });
    }
}

