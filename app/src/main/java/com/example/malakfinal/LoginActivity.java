package com.example.malakfinal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.malakfinal.data.AppDataBase;
import com.example.malakfinal.data.MyUserTable.MyUser;
import com.example.malakfinal.data.MyUserTable.MyUserQuery;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private TextView tvSubtitle;
    private TextView Email;
    private TextView Pass;
    private TextView tvForgotPassword;
    private TextView tvSignUp;

    private EditText etIdNumber;
    private EditText etMail1;
    private EditText etPass;

    private Button btnLogin;
    private Button SignUp;

    // 🔥 Firebase
    private FirebaseAuth auth;

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
        Email = findViewById(R.id.Email);
        Pass = findViewById(R.id.Pass);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        etMail1 = findViewById(R.id.etMail1);
        etPass = findViewById(R.id.etPass);
        btnLogin = findViewById(R.id.btnLogin);
        SignUp = findViewById(R.id.SignUp);

        // Firebase init
        auth = FirebaseAuth.getInstance();

        // 🔥 دخول تلقائي إذا المستخدم مسجل مسبقًا
        if (auth.getCurrentUser() != null) {
            Intent intent = new Intent(LoginActivity.this, RoleSelection.class);
            startActivity(intent);
            finish();
        }

        // زر تسجيل الدخول
        btnLogin.setOnClickListener(v -> {

            if (!readAndValidateFields()) {
                Toast.makeText(LoginActivity.this,
                        "Please fix the errors",
                        Toast.LENGTH_SHORT).show();
                return;
            }




        });

        // زر الانتقال إلى صفحة التسجيل
        SignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUp.class);
            startActivity(intent);
        });
    }

    /**
     * التحقق من الحقول فقط (Validation)
     */
    public boolean readAndValidateFields() {

        boolean isValid = true;

        String email = etMail1.getText().toString().trim();
        String password = etPass.getText().toString().trim();

        if (email.isEmpty() ||
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etMail1.setError("Valid email is required");
            isValid = false;
        }

        if (password.isEmpty()) {
            etPass.setError("Password is required");
            isValid = false;
        }

        // تحقق محلي (Room) اختياري
        MyUserQuery userQuery = AppDataBase.getDB(this).getMyUserQuery();
        MyUser user = userQuery.getUserByEmail(email);

        if (user == null) {
            etMail1.setError("User not found");
            isValid = false;
        }
        if (isValid)
        {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, task -> {

                        if (task.isSuccessful()) {

                            Toast.makeText(LoginActivity.this,
                                    "Login successful",
                                    Toast.LENGTH_SHORT).show();


                            Intent intent =
                                    new Intent(LoginActivity.this, RoleSelection.class);
                            startActivity(intent);
                            finish();

                        } else {

                            Toast.makeText(LoginActivity.this,
                                    "Login failed",
                                    Toast.LENGTH_SHORT).show();

                            etMail1.setError(
                                    task.getException() != null
                                            ? task.getException().getMessage()
                                            : "Firebase error"
                            );
                        }
                    });
        }

        return isValid;
    }
}




