package com.example.malakfinal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.malakfinal.data.AppDataBaseT.AppDataBase;
import com.example.malakfinal.data.MyUserTable.MyUser;

/**
 * SignUp Activity
 *
 * هذا الـ Activity مسؤول عن إنشاء حساب جديد للمستخدم.
 * يتيح للمستخدم إدخال:
 * الاسم، البريد الإلكتروني، كلمة المرور، وتأكيد كلمة المرور.
 *
 * بعد التحقق من صحة البيانات، يتم حفظ المستخدم في قاعدة البيانات
 * والانتقال إلى شاشة اختيار الدور (RoleSelection).
 */
public class SignUp extends AppCompatActivity {

    /** نص عنوان إنشاء حساب */
    private TextView tvCreateAccount;

    /** حقل إدخال اسم المستخدم */
    private EditText etName;

    /** حقل إدخال البريد الإلكتروني */
    private EditText etEmail;

    /** حقل إدخال كلمة المرور */
    private EditText etPassword;

    /** حقل تأكيد كلمة المرور */
    private EditText etConfirmPassword;

    /** زر إنشاء الحساب */
    private Button btnRegister;

    /**
     * دالة onCreate
     *
     * يتم استدعاؤها عند إنشاء الـ Activity،
     * وتقوم بربط عناصر الواجهة من ملف XML
     * وإعداد حدث الضغط على زر التسجيل.
     *
     * @param savedInstanceState حالة محفوظة للنشاط (إن وجدت)
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        // ضبط هوامش النظام (شريط الحالة والتنقل)
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

        /**
         * حدث زر التسجيل
         *
         * عند الضغط على الزر يتم التحقق من صحة البيانات المدخلة،
         * وإذا كانت صحيحة يتم إنشاء الحساب والانتقال
         * إلى شاشة اختيار الدور.
         */
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (readAndValidateFields()) {
                    Intent intent = new Intent(SignUp.this, RoleSelection.class);
                    startActivity(intent);
                    Toast.makeText(SignUp.this,
                            "Account created successfully",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignUp.this,
                            "Please fill all fields",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * دالة قراءة والتحقق من الحقول
     *
     * تقوم هذه الدالة بقراءة القيم المدخلة في نموذج التسجيل
     * والتحقق من صحتها وفق الشروط التالية:
     * - الاسم غير فارغ
     * - البريد الإلكتروني صحيح وغير مكرر
     * - كلمة المرور لا تقل عن 8 أحرف
     * - تأكيد كلمة المرور يطابق كلمة المرور
     *
     * في حال كانت جميع البيانات صحيحة،
     * يتم إنشاء كائن مستخدم جديد
     * وحفظه في قاعدة البيانات.
     *
     * @return true إذا كانت جميع الحقول صحيحة،
     *         false إذا وُجد أي خطأ في الإدخال
     */
    public boolean readAndValidateFields() {
        boolean isValid = true;

        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        // التحقق من الاسم
        if (name.isEmpty()) {
            etName.setError("Name is required");
            isValid = false;
        }

        // التحقق من البريد الإلكتروني
        if (email.isEmpty() ||
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Valid email is required");
            isValid = false;
        }

        // التحقق من كلمة المرور
        if (password.isEmpty() || password.length() < 8) {
            etPassword.setError("Password must be at least 8 characters");
            isValid = false;
        }

        // التحقق من تطابق كلمات المرور
        if (!confirmPassword.equals(password)) {
            etConfirmPassword.setError("Passwords don't match");
            isValid = false;
        }

        // التحقق من وجود البريد الإلكتروني مسبقًا
        MyUser existingUser =
                AppDataBase.getDB(this).getMyUserQuery().getUserByEmail(email);

        if (existingUser != null) {
            etEmail.setError("Email already exists");
            Toast.makeText(SignUp.this,
                    "Email already exists",
                    Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        // حفظ المستخدم في قاعدة البيانات إذا كانت البيانات صحيحة
        if (isValid) {
            MyUser myUser = new MyUser();
            myUser.setFullName(name);
            myUser.setEmail(email);
            myUser.setPassword(password);

            AppDataBase.getDB(this)
                    .getMyUserQuery()
                    .insertUser(myUser);
        }

        return isValid;
    }
}
