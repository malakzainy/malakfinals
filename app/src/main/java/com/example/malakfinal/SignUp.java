package com.example.malakfinal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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

//import com.example.malakfinal.data.AppDataBaseT.AppDataBase;
import com.example.malakfinal.data.AppDataBase;
import com.example.malakfinal.data.MyUserTable.MyUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
        //Realtime Database

        // زر إنشاء الحساب
        btnRegister.setOnClickListener(v -> { //واجهة تطبيف interface معالج حدث clickلا يمكن بناء كائن منه
            validateAndSignUp();
        });
    }

    private void validateAndSignUp() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();




        if (name.isEmpty()) {
            etName.setError("Name is required");
            return;
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Valid email is required");
            return;
        }

        if (password.isEmpty() || password.length() < 8) {
            etPassword.setError("Password must be at least 8 characters");
            return;
        }

        if (!confirmPassword.equals(password)) {
            etConfirmPassword.setError("Passwords don't match");
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Firebase user created successfully, now save to local database
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            if (firebaseUser != null) {
                                saveUserToLocalStorage(firebaseUser.getUid(), name, email);
                            }

                            Toast.makeText(SignUp.this,
                                    "Signing up Succeeded",
                                    Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(SignUp.this, RoleSelection.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SignUp.this,
                                    "Signing up failed: " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();

                            etEmail.setError(task.getException().getMessage());
                        }
                    }
                });

    }

    private void saveUserToLocalStorage(String userId, String name, String email) {
        MyUser myUser = new MyUser(userId, name, email);
        AppDataBase.getDB(this).getMyUserQuery().insertUser(myUser);
    }
}
