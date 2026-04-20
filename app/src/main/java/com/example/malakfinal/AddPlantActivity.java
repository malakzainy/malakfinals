package com.example.malakfinal;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.malakfinal.data.AppDataBase;
import com.example.malakfinal.data.MyTask.Plant;
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

    /** عنوان النبات */
    private String title;

    /** وصف النبات */
    private String description;

    /** زر حفظ النبات */
    private Button save;
    private EditText plantIdEditText,titleEditText,descriptionEditText;

    // مُشغّلات لطلب الأذونات
    private ActivityResultLauncher<String> requestReadMediaImagesPermission;
    private ActivityResultLauncher<String> requestReadMediaVideoPermission;
    private ActivityResultLauncher<String> requestReadExternalStoragePermission;
    private ImageView ivSelectedImage; //صفة كمؤشر لهذا الكائن
    private Uri selectedImageUri;//صفة لحفظ عنوان الصورة بعد اختيارها
    private ActivityResultLauncher<String> pickImage;// ‏كائن لطلب الصورة من الهاتف

    /**
     * تُستدعى هذه الدالة عند إنشاء الصفحة.
     * تقوم بربط الواجهة الرسومية وإعداد زر الحفظ ومعالجة النقر عليه.
     *
     * @param savedInstanceState البيانات المحفوظة عند إعادة إنشاء الصفحة
     */
    @SuppressLint({"WrongViewCast", "MissingInflatedId"}) //تجاهل تحذير معيّن صادر من نظام Lint.
    @Override
    // onCreate تتنفذ مرة واحدة عند فتح هذه الشاشة
    //AddPlantActivity זימון למחלקת
    protected void onCreate(Bundle savedInstanceState) {
        // * يستدعي onCreate للكلاس الأب لضبط إعدادات الـ Activity الأساسية.
        super.onCreate(savedInstanceState);
        // * يفعّل وضع Edge-to-Edge للـ Activity الحالي لتمديد المحتوى أسفل أشرطة النظام.
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_plant); //مسؤول عن ربط ملف التصميم xml بكود java الخاص بالنشاط


        // تسجيل مُشغّل لطلب إذن READ_MEDIA_IMAGES
        requestReadMediaImagesPermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                Log.d(TAG, "READ_MEDIA_IMAGES permission granted");
                Toast.makeText(this, "تم منح إذن قراءة الصور", Toast.LENGTH_SHORT).show();
                // يمكنك الآن المتابعة بالعملية التي تتطلب هذا الإذن
            } else {
                Log.d(TAG, "READ_MEDIA_IMAGES permission denied");
                Toast.makeText(this, "تم رفض إذن قراءة الصور", Toast.LENGTH_SHORT).show();
                // التعامل مع حالة رفض الإذن
            }
        });


// تسجيل مُشغّل لطلب إذن READ_MEDIA_VIDEO
        requestReadMediaVideoPermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                Log.d(TAG, "READ_MEDIA_VIDEO permission granted");
                Toast.makeText(this, "تم منح إذن قراءة الفيديو", Toast.LENGTH_SHORT).show();
                // يمكنك الآن المتابعة بالعملية التي تتطلب هذا الإذن
            } else {
                Log.d(TAG, "READ_MEDIA_VIDEO permission denied");
                Toast.makeText(this, "تم رفض إذن قراءة الفيديو", Toast.LENGTH_SHORT).show();
                // التعامل مع حالة رفض الإذن
            }
        });


// تسجيل مُشغّل لطلب إذن READ_EXTERNAL_STORAGE
        requestReadExternalStoragePermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                Log.d(TAG, "READ_EXTERNAL_STORAGE permission granted");
                Toast.makeText(this, "تم منح إذن قراءة التخزين الخارجي", Toast.LENGTH_SHORT).show();
                // يمكنك الآن المتابعة بالعملية التي تتطلب هذا الإذن
            } else {
                Log.d(TAG, "READ_EXTERNAL_STORAGE permission denied");
                Toast.makeText(this, "تم رفض إذن قراءة التخزين الخارجي", Toast.LENGTH_SHORT).show();
                // التعامل مع حالة رفض الإذن
            }
        });
//    تجهيز  وتسجيل الكائن لطلب الصورة  في العملية onCreate
// Initialize the ActivityResultLauncher for picking images
        pickImage = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    selectedImageUri = result;
                    ivSelectedImage.setImageURI(result);
                    ivSelectedImage.setVisibility(View.VISIBLE);
                }
            }
        });
        //  تجهيز حدث للضغط على الكائن الذي يمثل الصورة   ‏في العملية onCreate

        ivSelectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage.launch("image/*"); // Launch the image picker
            }
        });
        pickImage.launch("image/*");



//استدعاء دالة الفحص (سيتم تطبيقها لاحقا)
        checkAndRequestPermissions();

// * يضبط مستمع لتحديث المساحات (Insets) الخاصة بالنظام
// * على الـ View المحدد، مثل شريط الحالة وشريط التنقل.
// *
// * @param v الـ View المستهدف
// * @param insets معلومات المساحات التي يشغلها النظام
// *
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            //* @param insets كائن WindowInsetsCompat يحتوي معلومات الـ Insets
            // * @return Insets يمثل المسافات لشريط الحالة وشريط التنقل
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // * يضبط الـ padding للـ View بحيث لا تتداخل العناصر
            // * مع أشرطة النظام (Status Bar و Navigation Bar).
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

         titleEditText = findViewById(R.id.title); //  البحث عن العنصر الذي يحمل المعرفID المسمى title في ملف التصميم وربطه بالمتغير البرمجي  titleEditText
         descriptionEditText = findViewById(R.id.description);
        save = findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() { //واجهة تطبيف interface معالج حدث clickلا يمكن بناء كائن منه
            @Override
            public void onClick(View view) {
                if (validateFields()) {
                   // addPlant();
                    //استدعاء الداله
                    savePlants(new Plant(title,  description));
                    // مسح حقول الادخال
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

    // دالة لفحص وطلب الأذونات
    // AddPlantActivity זימון למחלקת
    private void checkAndRequestPermissions() {
        // فحص وطلب إذن READ_MEDIA_IMAGES (للإصدارات الحديثة)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // أندرويد 13+
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {
                requestReadMediaImagesPermission.launch(android.Manifest.permission.READ_MEDIA_IMAGES);
            } else {
                Log.d(TAG, "READ_MEDIA_IMAGES permission already granted");
                Toast.makeText(this, "إذن قراءة الصور ممنوح بالفعل", Toast.LENGTH_SHORT).show();
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { // أندرويد 10 و 11 و 12// على هذه الإصدارات، READ_EXTERNAL_STORAGE له سلوك مختلف
            // إذا كنت تستخدم Scoped Storage بشكل صحيح، قد لا تحتاج إلى هذا الإذن
            // ولكن إذا كنت تحتاج إلى الوصول إلى جميع الصور، فقد تحتاج إلى READ_EXTERNAL_STORAGE
            // في هذا المثال، سنفحص READ_EXTERNAL_STORAGE للإصدارات الأقدم من 13
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestReadExternalStoragePermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE);
            } else {
                Log.d(TAG, "READ_EXTERNAL_STORAGE permission already granted (for older versions)");
                Toast.makeText(this, "إذن قراءة التخزين ممنوح بالفعل (للإصدارات الأقدم)", Toast.LENGTH_SHORT).show();
            }
        } else { // أندرويد 9 والإصدارات الأقدم
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestReadExternalStoragePermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE);
            } else {
                Log.d(TAG, "READ_EXTERNAL_STORAGE permission already granted (for older versions)");
                Toast.makeText(this, "إذن قراءة التخزين ممنوح بالفعل (للإصدارات الأقدم)", Toast.LENGTH_SHORT).show();
            }
        }


        // فحص وطلب إذن READ_MEDIA_VIDEO (للإصدارات الحديثة)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // أندرويد 13+
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_VIDEO)
                    != PackageManager.PERMISSION_GRANTED) {
                requestReadMediaVideoPermission.launch(android.Manifest.permission.READ_MEDIA_VIDEO);
            } else {
                Log.d(TAG, "READ_MEDIA_VIDEO permission already granted");
                Toast.makeText(this, "إذن قراءة الفيديو ممنوح بالفعل", Toast.LENGTH_SHORT).show();
            }
        }// ملاحظة: إذن INTERNET لا يحتاج إلى فحص أو
    }





    /**
     * التحقق من أن جميع الحقول المطلوبة قد تم إدخالها.
     *
     * @return true إذا كانت الحقول صحيحة، false إذا كانت هناك حقول فارغة
     */
    //זימון למחלקת AddPlantActivity
    private boolean validateFields() {
        //  استخراج
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
        // * إدخال نبات جديد في قاعدة البيانات.
        // * <p>
        // * يقوم بالخطوات التالية:
        // * 1. الحصول على نسخة من قاعدة البيانات عبر AppDataBase.getDB(this).
        // * 2. استدعاء DAO الخاص بالنباتات (getMyPlantQuery()).
        // * 3. إدخال كائن Plant جديد يحتوي على title و description.
        // * </p>
        // * العملية تُسمّى: **Insert Operation** أو بالعربي:
        // * "إدخال سجل جديد في قاعدة البيانات".
        AppDataBase.getDB(this).getMyPlantQuery().insertPlant(new Plant(title, description));
    }

    /**
     * حفظ النبات في Firebase Realtime Database.
     */
    //זימון למחלקת AddPlantActivity
    public void savePlants(Plant plant) { // "plants"الحصول على مرجع الى عقدة البيانات في قاعدة
        /**
         * مرجع إلى الجذر (Root) في Firebase Realtime Database.
         * <p>
         * يقوم هذا السطر بإنشاء كائن من نوع DatabaseReference
         * يشير إلى الجذر الأساسي لقاعدة البيانات باستخدام
         * FirebaseDatabase.getInstance().
         * </p>
         * يمكن استخدام هذا المرجع لقراءة البيانات أو إضافتها
         * أو تعديلها أو حذفها من قاعدة البيانات.
         */
         //  شرح الاستاذ: مؤشر لقاعدة البيانات  Firebase Realtime Database
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        /**
         * مرجع إلى عقدة "plants" داخل Firebase Realtime Database.
         * <p>
         * يتم إنشاء مرجع فرعي (Child Reference) من المرجع الأساسي
         * للقاعدة، بحيث يشير هذا الكائن إلى المسار:
         * /plants
         * </p>
         * يمكن استخدام هذا المرجع لإضافة أو قراءة أو تعديل
         * أو حذف بيانات النباتات داخل قاعدة البيانات.
         */
        // شرح الاستاذ: مؤشر لجدول المستعملين
        DatabaseReference plantsRef = database.child("plants");
        // انشاء مفتاح فريد للمستخدم لجديد
        DatabaseReference newPlantRef = plantsRef.push();
        // تعيين معرف المستخدم في كائن Plant
        plant.setPlantId(newPlantRef.getKey());
        //حفظ بيانات المستخدم في قاعدة البيانات في قعدة البيانات
        // اضافة كائن لمجموعة المستعملين ومعالج حدث لفحص نجاح المطلوب معالج حدث لفحص هل تم المطلوب من قاعدة البيانات
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
// @Override
//@Override هي Annotation (تعليمة توضيحية) بنحطها فوق دالة لما نكون عم نعيد تعريف (Override) دالة موجودة أصلاً في كلاس أب (Superclass) أو Interface.
//تتأكد إنك فعلاً عم تعيد تعريف دالة موجودة
//إذا غلطت باسم الدالة أو نوع البراميترات، المترجم (Compiler) بيعطيك خطأ.
//توضيح للكود
//أي مبرمج يشوفها يعرف إن هاي الدالة جاية من كلاس أب أو Interface.
//
// @NonNull
//هذا المتغير أو البراميتر أو القيمة الراجعة لازم ما تكون null.
//منع NullPointerException
//تساعد Android Studio يعطيك تحذير إذا حاولت تمرر null
//توضح للمبرمجين إن القيمة إلزامية
//..