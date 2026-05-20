package com.example.malakfinal;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.malakfinal.data.AppDataBase;
import com.example.malakfinal.data.MyTask.MyPlantAdapter;
import com.example.malakfinal.data.MyTask.Plant;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
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
 * لإضافة نبات جديد وشريط تنقل سفلي.
 */
public class MainActivity extends AppCompatActivity {


    /** ListView لعرض قائمة النباتات */
    private ListView lstvTasks;

    /** Adapter مخصص لعرض كائنات Plant داخل ListView */
    private MyPlantAdapter adapter;
    private FloatingActionButton fabAdd;
    private BottomNavigationView bottomNavigationView;

    /**
     * تُستدعى هذه الدالة عند إنشاء الصفحة.
     * تقوم بتهيئة الواجهة وربط عناصرها،
     * وإعداد زر الإضافة وقائمة النباتات وشريط التنقل.
     *
     * @param savedInstanceState البيانات المحفوظة عند إعادة إنشاء الصفحة
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        // ربط ListView
        lstvTasks = findViewById(R.id.lstvTasks);
        fabAdd = findViewById(R.id.fabAdd);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddPlantActivity.class);
                startActivity(intent);
            }
        });

        // تهيئة الـ Adapter وربطه مع الـ ListView
        adapter = new MyPlantAdapter(this, R.layout.plant_item_layout, new ArrayList<>());
        lstvTasks.setAdapter(adapter);

        // إعداد المستمع لنقرات شريط التنقل السفلي
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    // نحن بالفعل في الشاشة الرئيسية
                    return true;
                } else if (id == R.id.nav_child_report) {
                    Intent intent = new Intent(MainActivity.this, ChildReport.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.nav_parents_report) {
                    Intent intent = new Intent(MainActivity.this, ParentsReport.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.nav_logout) {
                    showLogoutDialog();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * عرض ديالوج لتأكيد تسجيل الخروج
     */
    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Log Out");
        builder.setMessage("Are you sure you want to log out?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * تُستدعى هذه الدالة عند الرجوع إلى الصفحة.
     * يتم فيها تحديث قائمة النباتات من قاعدة البيانات
     * حتى تظهر أحدث البيانات.
     */
    @Override
    protected void onResume() { // تتنفذ دائما عند فتح هذه الشاشة
        super.onResume();
        // تأكد من أن أيقونة Home هي المختارة عند العودة
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        
        adapter.clear();
        getAllFromFirebase(adapter);
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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) return;
        
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference myRef = database.getReference("plants").child(uid);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adapter.clear();
                for (DataSnapshot taskSnapshot : snapshot.getChildren()) {
                    Plant plant = taskSnapshot.getValue(Plant.class);
                    adapter.add(plant);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
