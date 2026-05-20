package com.example.malakfinal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.malakfinal.data.AppDataBase;
import com.example.malakfinal.data.MyTask.MyPlantAdapter;
import com.example.malakfinal.data.MyTask.Plant;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private FloatingActionButton fab;

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
        fab = findViewById(R.id.fabAdd);
        // تهيئة الـ Adapter وربطه مع الـ ListView
        adapter = new MyPlantAdapter(this, R.layout.task_item_layout, new ArrayList<>());
        lstvTasks.setAdapter(adapter);


        // زر الإضافة (Floating Action Button)
         FloatingActionButton fab = findViewById(R.id.main);

        // عند الضغط على زر الإضافة يتم فتح شاشة إضافة نبات
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddPlantActivity.class);
            startActivity(intent);
        });

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
        adapter.addAll(AppDataBase.getDB(this).getMyPlantQuery().getAllPlants());
        //تنظيف المنسق من جميع المعطيات السابقه
        adapter.clear();
        //اضافة المعطيات الجديدة
        getAllFromFirebase(adapter);
        //تحديث المنسق
        adapter.notifyDataSetChanged();

    }


    // notification
    //طلب اذن الاشعارات

    private final ActivityResultLauncher<String> requestNotificationPermissionLauncher = registerForActivityResult( new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (!isGranted) {
                    Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show();
                }
            }
    );

    private void getAllFromFirebase(MyPlantAdapter adapter) {
        //عنوان قاعدة البيانات
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // عنوان مجموعة المعطيات داخل قاعدة البيانات
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference myRef = database.getReference("plants").child(uid);
//إضافة listener مما يسبب الإصغاء لكل تغيير حتلنة عرض المعطيات//
        myRef.addValueEventListener(new ValueEventListener() {
            @Override//دالة معالج حدث تقوم بتلقى نسخة عن كل المعطيات عند أي تغيير
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adapter.clear();//حذف كل المعطيات بالوسيط
                for (DataSnapshot taskSnapshot : snapshot.getChildren()) {
                    //  استخراج كل المعطيات على وتحويلها لكائن ملائم//
                    Plant plant = taskSnapshot.getValue(Plant.class);
                    adapter.add(plant);
                }
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Data fetched successfully", Toast.LENGTH_SHORT).show();


            }


            @Override//بحالة فشل استخراج المعطيات
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

