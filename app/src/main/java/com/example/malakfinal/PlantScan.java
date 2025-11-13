package com.example.malakfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PlantScan extends AppCompatActivity
{
    private TextView tv_title;
    private Button btn_take_photo;
    private Button btn_upload;
    private Button btn_scan_plant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_plant_scan);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tv_title = findViewById(R.id.tv_title);
        btn_take_photo = findViewById(R.id.btn_take_photo);
        btn_upload = findViewById(R.id.btn_upload);
        btn_scan_plant = findViewById(R.id.btn_scan_plant);
        btn_scan_plant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlantScan.this, ParentsReport.class);
                startActivity(intent);
            }
        });
    }
}