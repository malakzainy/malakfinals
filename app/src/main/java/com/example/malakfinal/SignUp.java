package com.example.malakfinal;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.malakfinal.data.AppDataBase;
import com.example.malakfinal.data.MyAsthmaTable.AsthmaUser;
import com.example.malakfinal.data.MyUserTable.MyUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private TextView tvCreateAccount;
    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnRegister;

    // 🔥 Firebase
    private FirebaseAuth auth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ربط عناصر الواجهة
        tvCreateAccount = findViewById(R.id.tvCreateAccount);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        // Firebase init
        auth = FirebaseAuth.getInstance();

        // زر إنشاء الحساب
        btnRegister.setOnClickListener(v -> {

            if (!readAndValidateFields()) {
                Toast.makeText(SignUp.this,
                        "Please fix the errors",
                        Toast.LENGTH_SHORT).show();
                return;
            }


        });
        btnRegister.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            String confirmPassword = etConfirmPassword.getText().toString();


            if (!name.isEmpty() && !email.isEmpty()) {
                AsthmaUser newUser = new AsthmaUser(name, email,password,confirmPassword);
                saveUser(newUser);



                // مسح حقول الإدخال
                etName.setText("");
                etEmail.setText("");
                etPassword.setText("");
                etConfirmPassword.setText("");
            } else {
                Log.w(TAG, "الرجاء إدخال الاسم والبريد الإلكتروني.");
            }
        });


    }

    /**
     * Validation فقط (بدون تخزين)
     */
    public boolean readAndValidateFields() {

        boolean isValid = true;

        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (name.isEmpty()) {
            etName.setError("Name is required");
            isValid = false;
        }

        if (email.isEmpty() ||
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Valid email is required");
            isValid = false;
        }

        if (password.isEmpty() || password.length() < 8) {
            etPassword.setError("Password must be at least 8 characters");
            isValid = false;
        }

        if (!confirmPassword.equals(password)) {
            etConfirmPassword.setError("Passwords don't match");
            isValid = false;
        }

        // تحقق محلي (Room)
        MyUser existingUser =
                AppDataBase.getDB(this).getMyUserQuery().getUserByEmail(email);

        if (existingUser != null) {
            etEmail.setError("Email already exists");
            //


            // 🔥 إنشاء حساب Firebase
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(SignUp.this, task -> {

                        if (task.isSuccessful()) {

                            // ✅ Firebase نجح → خزّن في Room
                            MyUser myUser = new MyUser();
                            myUser.setFullName(name);
                            myUser.setEmail(email);
                            myUser.setPassword(password);

                            AppDataBase.getDB(SignUp.this)
                                    .getMyUserQuery()
                                    .insertUser(myUser);

                            Toast.makeText(SignUp.this,
                                    "Account created successfully",
                                    Toast.LENGTH_SHORT).show();

                            Intent intent =
                                    new Intent(SignUp.this, RoleSelection.class);
                            startActivity(intent);
                            finish();

                        } else {

                            Toast.makeText(SignUp.this,
                                    "Sign up failed",
                                    Toast.LENGTH_SHORT).show();

                            etEmail.setError(
                                    task.getException() != null
                                            ? task.getException().getMessage()
                                            : "Firebase error"
                            );
                        }
                    });
            isValid = false;
        }

        return isValid;
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
                        Toast.makeText(SignUp.this, "Succeeded to add User",  Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(SignUp.this, "Failed to add User", Toast.LENGTH_SHORT).show();
                        // عرض رسالة خطأ للمستخدم
                    }
                });

    }



}
