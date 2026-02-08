package com.example.malakfinal;

import static android.app.ProgressDialog.show;
import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.malakfinal.data.AppDataBase;
import com.example.malakfinal.data.MyAsthmaTable.AsthmaUser;
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

    /**
     * تُستدعى هذه الدالة عند إنشاء الصفحة.
     * تقوم بربط الواجهة الرسومية وإعداد زر الحفظ ومعالجة النقر عليه.
     *
     * @param savedInstanceState البيانات المحفوظة عند إعادة إنشاء الصفحة
     */
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_plant);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        save = findViewById(R.id.save);

        // عند الضغط على زر الحفظ
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFields()) {
                    Intent intent = new Intent(AddPlantActivity.this, ScanResult.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(AddPlantActivity.this, "Plant added successfully", Toast.LENGTH_SHORT).show();
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
        String titleString = String.valueOf(findViewById(R.id.title));
        String descriptionString = String.valueOf(findViewById(R.id.description));

        if (titleString.isEmpty()) {
            Toast.makeText(this, "Title is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (descriptionString.isEmpty()) {
            Toast.makeText(this, "Description is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        addPlant();
        return true;
    }

    /**
     * إنشاء كائن Plant جديد وحفظه داخل قاعدة البيانات.
     * يتم أخذ البيانات من الحقول الموجودة في الواجهة.
     */
    public void addPlant() {
        title = String.valueOf(findViewById(R.id.title));
        description = String.valueOf(findViewById(R.id.description));

        Plant plant = new Plant();
        plant.setTitle(title);
        plant.setDescription(description);

        AppDataBase.getDB(this).getMyPlantQuery().insertTask(plant);

        Toast.makeText(this, "Plant added successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
    public void saveUser(AsthmaUser user) {// الحصول على مرجع إلى عقدة "users" في قاعدة البيانات

        // تهيئة Firebase Realtime Database    //مؤشر لقاعدة البيانات
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
// ‏مؤشر لجدول المستعملين
        DatabaseReference usersRef = database.child("users");
        // إنشاء مفتاح فريد للمستخدم الجديد
        DatabaseReference newUserRef = usersRef.push();
        // تعيين معرف المستخدم في كائن MyUser
        user.setUserId(newUserRef.getKey());
        // حفظ بيانات المستخدم في قاعدة البيانات
        //اضافة كائن "لمجموعة" المستعملين ومعالج حدث لفحص نجاح المطلوب
        // حدث لفحص هل تم المطلوب من قاعدة البيانات معالج
        newUserRef.setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddPlantActivity.this, "Succeeded to add User",  Toast.LENGTH_SHORT).show();
                        finish();




                        // تم حفظ البيانات بنجاح
                        Log.d(TAG, "تم حفظ المستخدم بنجاح: " + user.getUserId());
                        // تحديث واجهة المستخدم أو تنفيذ إجراءات أخرى
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // معالجة الأخطاء
                        Log.e(TAG, "خطأ في حفظ المستخدم: " + e.getMessage(), e);
                        Toast.makeText(AddPlantActivity.this, "Failed to add User", Toast.LENGTH_SHORT).show();
                        // عرض رسالة خطأ للمستخدم
                    }
                });
    }

}
