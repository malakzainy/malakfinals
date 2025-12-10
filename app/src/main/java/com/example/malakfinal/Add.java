package com.example.malakfinal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
private Button save;

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
        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFields()) {
                    Intent intent = new Intent(Add.this, ScanResult.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(Add.this, "Plant added successfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Add.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateFields() {
        String plantIdString = String.valueOf(findViewById(R.id.PlantId));
        String titleString = String.valueOf(findViewById(R.id.title));
        String descriptionString = String.valueOf(findViewById(R.id.description));

        if (plantIdString.isEmpty()) {
            Toast.makeText(this, "Plant ID is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            plantId = Integer.parseInt(plantIdString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Plant ID must be a number", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (titleString.isEmpty()) {
            Toast.makeText(this, "Title is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (descriptionString.isEmpty()) {
            Toast.makeText(this, "Description is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    public void addPlant(View view){
        Plant plant = new Plant();
        plant.setPlantId(plantId);
        plant.setTitle(title);
        plant.setDescription(description);
        AppDataBase.getDB(this).getMyPlantQuery().insertTask(plant);
        Toast.makeText(this, "Plant added successfully", Toast.LENGTH_SHORT).show();
        finish();

    }
}