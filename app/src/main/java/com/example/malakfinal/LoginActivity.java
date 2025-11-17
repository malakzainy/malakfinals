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

public class LoginActivity extends AppCompatActivity {
    private TextView tvWelcome;
    private TextView tvSubtitle;
    private TextView IdNum;
    private TextView Email;
    private TextView Pass;
    private TextView tvForgotPassword;
    private TextView tvSignUp;
    private EditText etIdNumber;
    private EditText etMail1;
    private EditText etPass;
    private Button btnLogin;
    private Button SignUp;


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

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (readAndValidateFields()) {
                    Intent intent = new Intent(LoginActivity.this, RoleSelection.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
        SignUp = findViewById(R.id.SignUp);


        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (readAndValidateFields()) {
                    Intent intent = new Intent(LoginActivity.this, SignUp.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean readAndValidateFields() {
        boolean isValid = true;
        String idNumber = etIdNumber.getText().toString().trim();
        String password = etPass.getText().toString().trim();

        if (idNumber.isEmpty()) {
            etIdNumber.setError("Id number is required");
            isValid = false;
        }


        if (Email.isEmpty())
        {
            etMail1.setError("Email is required");
            isValid = false;
        }

        if (password.isEmpty()) {
            etPass.setError("Password is required");
            isValid = false;
        }

        if (isValid)
        {
            String IdNum = etIdNumber.getText().toString().trim();
            String Email = etMail1.getText().toString().trim();
            String pass = etPass.getText().toString().trim();

            AppDataBase.getDB(this).getMyUserQuery().insertUser(MyUser);
        }


        return isValid;
    }
}




