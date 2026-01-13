package com.example.malakfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.malakfinal.data.AppDataBaseT.AppDataBase;
import com.example.malakfinal.data.MyTaskTable.MyPlantAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView lstvTasks;
    private MyPlantAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        lstvTasks = findViewById(R.id.lstvTasks);
        FloatingActionButton fab = findViewById(R.id.main);

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddPlantActivity.class);
            startActivity(intent);
        });


        adapter = new MyPlantAdapter(this, R.layout.task_item_layout, new ArrayList<>());
        lstvTasks.setAdapter(adapter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        adapter.clear();
        adapter.addAll(AppDataBase.getDB(this).getMyPlantQuery().getAllPlants());
        adapter.notifyDataSetChanged();
    }
}