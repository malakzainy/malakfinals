package com.example.malakfinal;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.malakfinal.data.AppDataBaseT.AppDataBase;
import com.example.malakfinal.data.MyTaskTable.Plant;

public class Add extends AppCompatActivity
{
    private int plantId;
    private String title;
    private String description;


    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });
        View PlantId = findViewById(R.id.PlantId);
        title = String.valueOf(findViewById(R.id.title));
        description = String.valueOf(findViewById(R.id.description));
    }
    public void addPlant(View view) {
        Plant plant = new Plant();
        plant.setPlantId(plantId);
        plant.setTitle(title);
        plant.setDescription(description);
        AppDataBase.getDB(this).getMyPlantQuery().insertTask(plant);
        Toast.makeText(this, "Plant added successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}