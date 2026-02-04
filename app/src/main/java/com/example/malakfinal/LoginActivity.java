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
import com.example.malakfinal.data.MyUserTable.MyUserQuery;

import java.text.BreakIterator;

/**
 * LoginActivity هي شاشة تسجيل الدخول للتطبيق.
 * <p>
 * تتيح هذه الصفحة للمستخدم إدخال رقم الهوية، البريد الإلكتروني،
 * وكلمة المرور، ثم التحقق من صحة البيانات وتسجيل الدخول.
 */
public class LoginActivity extends AppCompatActivity {

    /** نص الترحيب */
    private TextView tvWelcome;

    /** النص الفرعي أسفل الترحيب */
    private TextView tvSubtitle;

    /** تسمية حقل رقم الهوية */
    private TextView IdNum;

    /** تسمية حقل البريد الإلكتروني */
    private TextView Email;

    /** تسمية حقل كلمة المرور */
    private TextView Pass;

    /** رابط "نسيت كلمة المرور" */
    private TextView tvForgotPassword;

    /** رابط الانتقال إلى صفحة التسجيل */
    private TextView tvSignUp;

    /** حقل إدخال رقم الهوية */
    private EditText etIdNumber;

    /** حقل إدخال البريد الإلكتروني */
    private EditText etMail1;

    /** حقل إدخال كلمة المرور */
    /** زر تسجيل الدخول */
    private Button btnLogin;


    /** زر الانتقال إلى صفحة التسجيل */

    private Button SignUp;


    /**
     * تُستدعى هذه الدالة عند إنشاء الصفحة.
     * تقوم بتهيئة الواجهة وربط عناصر XML بالمتغيرات البرمجية،
     * وإعداد أحداث النقر على الأزرار.
     *
     * @param savedInstanceState البيانات المحفوظة عند إعادة إنشاء الصفحة
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ربط عناصر الواجهة
        tvWelcome = findViewById(R.id.tvWelcome);
        tvSubtitle = findViewById(R.id.tvSubtitle);
        IdNum = findViewById(R.id.IdNum);
        Email = findViewById(R.id.Email);
        Pass = findViewById(R.id.Pass);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        etIdNumber = findViewById(R.id.etIdNumber);
        etMail1 = findViewById(R.id.etMail1);
        etPass = findViewById(R.id.etPass);
        btnLogin = findViewById(R.id.btnLogin);
        SignUp = findViewById(R.id.SignUp);

        // زر تسجيل الدخول
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (readAndValidateFields()) {
                    Intent intent = new Intent(LoginActivity.this, RoleSelection.class);
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // زر الانتقال إلى صفحة التسجيل
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUp.class);
                startActivity(intent);
            }
        });
    }

    /**
     * قراءة القيم التي أدخلها المستخدم والتحقق من صحتها.
     * يتم التأكد من أن الحقول غير فارغة
     * وأن البريد الإلكتروني موجود في قاعدة البيانات.
     *
     * @return true إذا كانت البيانات صحيحة، false إذا كانت غير صحيحة
     */
    public boolean readAndValidateFields() {
        boolean isValid = true;

        String idNumber = etIdNumber.getText().toString().trim();
        String password = etPass.getText().toString().trim();
        String email = etMail1.getText().toString().trim();

        // التحقق من كلمة المرور
        if (password.isEmpty()) {
            etPass.setError("Password is required");
            isValid = false;
        }

        // التحقق من البريد الإلكتروني
        if (email.isEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(idNumber).matches()) {
            etMail1.setError("Valid id number is required");
            isValid = false;
        }

        // التحقق من وجود المستخدم في قاعدة البيانات
        MyUserQuery userQuery = AppDataBase.getDB(this).getMyUserQuery();
        MyUser user = userQuery.getUserByEmail(email);

        if (user == null) {
            etMail1.setError("Invalid email");
            Toast.makeText(LoginActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }
}




