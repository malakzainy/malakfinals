package com.example.malakfinal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.malakfinal.data.AppDataBase;
import com.example.malakfinal.data.MyTask.MyPlantAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * MainActivity هي الصفحة الرئيسية في التطبيق.
 * <p>
 * تقوم بعرض قائمة النباتات المخزنة في قاعدة البيانات
 * باستخدام ListView و MyPlantAdapter، كما توفر زرًا
 * لإضافة نبات جديد.
 */
public class MainActivity extends AppCompatActivity {

    /** ListView لعرض قائمة النباتات */
    private ListView lstvTasks;

    /** Adapter مخصص لعرض كائنات Plant داخل ListView */
    private MyPlantAdapter adapter;

    /**
     * تُستدعى هذه الدالة عند إنشاء الصفحة.
     * تقوم بتهيئة الواجهة وربط عناصرها،
     * وإعداد زر الإضافة وقائمة النباتات.
     *
     * @param savedInstanceState البيانات المحفوظة عند إعادة إنشاء الصفحة
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // ربط ListView
        lstvTasks = findViewById(R.id.lstvTasks);

        // زر الإضافة (Floating Action Button)
        FloatingActionButton fab = findViewById(R.id.main);

        // عند الضغط على زر الإضافة يتم فتح شاشة إضافة نبات
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddPlantActivity.class);
            startActivity(intent);
        });

        // تهيئة الـ Adapter وربطه مع الـ ListView
        adapter = new MyPlantAdapter(this, R.layout.task_item_layout, new ArrayList<>());
        lstvTasks.setAdapter(adapter);

        // التعامل مع هوامش الشاشة
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * تُستدعى هذه الدالة عند الرجوع إلى الصفحة.
     * يتم فيها تحديث قائمة النباتات من قاعدة البيانات
     * حتى تظهر أحدث البيانات.
     */
    @Override
    //MainActivity זימון למחלקת
    protected void onResume() { // تتنفذ دائما عند فتح هذه الشاشة
        super.onResume();
        adapter.clear();
        adapter.addAll(AppDataBase.getDB(this).getMyPlantQuery().getAllPlants());
        adapter.notifyDataSetChanged();
    }
}

