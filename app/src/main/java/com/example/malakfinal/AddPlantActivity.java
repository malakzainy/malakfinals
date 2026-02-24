package com.example.malakfinal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.malakfinal.data.AppDataBase;
import com.example.malakfinal.data.MyTaskTable.Plant;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * AddPlantActivity هي شاشة تُستخدم لإضافة نبات جديد إلى قاعدة البيانات.
 * <p>
 * تتيح هذه الصفحة للمستخدم إدخال عنوان ووصف النبات
 * ثم حفظه في قاعدة البيانات عند الضغط على زر الحفظ.
 */
public class AddPlantActivity extends AppCompatActivity {

    /** رقم تعريف النبات */
    private int plantId;

    /** عنوان النبات */
    private String title;

    /** وصف النبات */
    private String description;

    /** زر حفظ النبات */
    private Button save;
    private EditText plantIdEditText,titleEditText,descriptionEditText;


    /**
     * تُستدعى هذه الدالة عند إنشاء الصفحة.
     * تقوم بربط الواجهة الرسومية وإعداد زر الحفظ ومعالجة النقر عليه.
     *
     * @param savedInstanceState البيانات المحفوظة عند إعادة إنشاء الصفحة
     */
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    // onCreate تتنفذ مرة واحدة عند فتح هذه الشاشة
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_plant);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
         plantIdEditText = findViewById(R.id.PlantId);
         titleEditText = findViewById(R.id.title); //  البحث عن العنصر الذي يحمل المعرفID المسمى title في ملف التصميم وربطه بالمتغير البرمجي  titleEditText
         descriptionEditText = findViewById(R.id.description);
        save = findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFields()) {
                   // addPlant();
                    savePlants(new Plant(title,  description));
                    titleEditText.setText("");
                    descriptionEditText.setText("");
                //    plantIdEditText.setText("");
//                    startActivity(new Intent(AddPlantActivity.this, ScanResult.class));
//                    finish();
                } else {
                    Toast.makeText(AddPlantActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * التحقق من أن جميع الحقول المطلوبة قد تم إدخالها.
     *
     * @return true إذا كانت الحقول صحيحة، false إذا كانت هناك حقول فارغة
     */
    private boolean validateFields() {
         title = titleEditText.getText().toString();
        description = descriptionEditText.getText().toString();

        if (title.isEmpty()) {
            Toast.makeText(this, "Title is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (description.isEmpty()) {
            Toast.makeText(this, "Description is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /**
     * إضافة النبات إلى قاعدة البيانات.
     */
    public void addPlant() {
        title = titleEditText.getText().toString();
        description = descriptionEditText.getText().toString();

        AppDataBase.getDB(this).getMyPlantQuery().insertPlant(new Plant(title, description));
    }

    /**
     * حفظ النبات في Firebase Realtime Database.
     */
    public void savePlants(Plant plant) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference plantsRef = database.child("plants");
        DatabaseReference newPlantRef = plantsRef.push();
        plant.setPlantId(newPlantRef.getKey());
        plantsRef.child(plant.getPlantId()).setValue(plant)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        addPlant();
                        Toast.makeText(AddPlantActivity.this, "Succeeded to add User", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddPlantActivity.this, ScanResult.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddPlantActivity.this, "Failed to add User", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}