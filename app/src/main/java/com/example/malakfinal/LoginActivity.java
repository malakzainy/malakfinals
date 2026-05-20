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

    /** يعرض رسالة الترحيب على الشاشة. */
    private TextView tvWelcome;

    /** يعرض النص الفرعي أسفل رسالة الترحيب. */
    private TextView tvSubtitle;

    /** يمثل حقل أو نص البريد الإلكتروني. */
    private TextView Email;

    /** يمثل حقل أو نص كلمة المرور. */
    private TextView Pass;

    /** يمثل خيار أو زر إنشاء حساب جديد. */
    private TextView tvSignUp;
    private EditText etMail1;  // ادخال الايميل
    private EditText etPass;// ادخال كلمة السر
    private Button btnLogin;// زر الانتقال الى الصفحة الرئيسية
    private Button SignUp;// زر النتقال الى صفحة تسجيل الدخول

    //تتيح هذه الشاشة للمستخدمين الذين يمتلكون حساباً مسبقاً تسجيل الدخول إلى التطبيق
    // عن طريق إدخال البريد الإلكتروني وكلمة المرور. تقوم الشاشة بفحص البيانات المدخلة محلياً،
    // ثم ترسلها إلى Firebase Authentication للتحقق من صحتها،
    // وفي حال النجاح تنقل المستخدم إلى شاشة اختيار الدور (RoleSelection).
    // 🔥 Firebase
    private FirebaseAuth auth;

    @SuppressLint("MissingInflatedId")
    @Override
    //LoginActivity זימון למחלקת
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // to Activity activity_login يحدد ملف التنسيق
        //هذه العملية تابعه للفئة Activity
        //يبني الكائن في واجهة التنسيقactivity_login
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
        etMail1 = findViewById(R.id.etMail1);
        etPass = findViewById(R.id.etPass);
        btnLogin = findViewById(R.id.btnLogin);
        SignUp = findViewById(R.id.SignUp);

        // Firebase init
        //هو سطر تهيئة (Initialization) لربط التطبيق بسيرفر FirebaseAuthentication لإدارة المستخدمين.
        //بدونه يكون المتغير فارغاً (null) وينهار التطبيق. بعده، يصبح التطبيق
        // جاهزاً لاستدعاء دوال مثل فحص المستخدم الحالي أو تسجيل الدخول.
        //لماذا .getInstance() وليس new؟
        //لأن الكلاس مصمم بنمط Singleton Pattern، والذي يضمن وجود نسخة واحدة فقط نشطة من
        // الخدمة داخل التطبيق لتوفير ذاكرة وموارد الهاتف ومنع التضارب.
        auth = FirebaseAuth.getInstance();

        // 🔥 دخول تلقائي إذا المستخدم مسجل مسبقًا
        if (auth.getCurrentUser() != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        // زر تسجيل الدخول
        btnLogin.setOnClickListener(v -> { //واجهة تطبيف interface معالج حدث clickلا يمكن بناء كائن منه

            if (!readAndValidateFields()) {
                Toast.makeText(LoginActivity.this,
                        "Please fix the errors",
                        Toast.LENGTH_SHORT).show();
                return;
            }

        });

        // زر الانتقال إلى صفحة التسجيل
        // View v ->
        //لما ينضغط الزر، رح ينادي دالة ويمرر فيها الزر نفسه كمتغير اسمه v
        //View: هاي نوع البيانات (Data Type)
        //بـ Android، أي زر أو عنصر على الشاشة هو كائن من نوع View
        //يعني الزر نفسه يعتبر View.
        //v: هذا اسم المتغير (Variable name)
        //هو بيمثل الزر اللي انضغط عليه.
        SignUp.setOnClickListener(v -> { //واجهة تطبيف interface معالج حدث clickلا يمكن بناء كائن منه
            Intent intent = new Intent(LoginActivity.this, SignUp.class);
            startActivity(intent);
        });
    }

    /**
     *      * التحقق من الحقول فقط (Validation)
     *      */
    //LoginActivity זימון למחלקת
    public boolean readAndValidateFields() {

        boolean isValid = true;

        //etMail1.getText(): تجلب النص الذي كتبه المستخدم داخل حقل البريد الإلكتروني.
        //.toString(): تحوّل النص المجلوب إلى نوع String (سلسلة نصية) لتتعامل معها الجافا.
        //.trim(): (مهمة جداً) تقوم بحذف أي فراغات زائدة وغير مقصودة قد يكتبها المستخدم بالخطأ قبل النص أو بعده
        // (مثال: إذا كتب المستخدم الفراغ بالمسطرة بعد الإيميل، يتم حذفه تلقائياً حتى لا يرفضه السيرفر).

        String email = etMail1.getText().toString().trim();
        String password = etPass.getText().toString().trim();


        //هذا الجزء من الكود مسؤول عن التحقق من صحة البريد الإلكتروني (Email Validation) قبل إرساله إلى السيرفر.
        //يقوم بفحص الحقل للتأكد من شرطين أساسيين، وإذا تحقق
        // أي منهما (بسبب وجود أداة الاختيار || التي تعني "أو")، يعتبر الإيميل غير صالح ويظهر خطأ للمستخدم:
        //الشرط الأول (email.isEmpty()): يفحص إذا كان حقل البريد الإلكتروني فارغاً تماماً ولم يكتب المستخدم فيه شيئاً.
        //
        //الشرط الثاني (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()): يفحص صيغة الإيميل. نستخدم أداة جاهزة بالنظام
        // (EMAIL_ADDRESS) لمطابقة النص المكتوب مع الصيغة العالمية للإيميلات (وجود @ ونقطة مثل name@email.com).
        // علامة التعجب ! في البداية تعني (النفي - NOT)، أي: "إذا كان الإيميل لا يطابق الصيغة الصحيحة".
        //2. ماذا يحدث إذا تحقق الخطأ؟
        //etMail1.setError(...): تظهر علامة تعجب حمراء داخل حقل الإدخال مع نص تنبيهي للمستخدم: "مطلوب بريد إلكتروني صالح".
        //
        //isValid = false;: نغير قيمة المتغير المنطقي إلى false لكي يعرف التطبيق لاحقاً أن هناك خطأ في الشاشة ويوقف عملية تسجيل الدخول.
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
        // يُستخدم عادةً في Room Database (Android) للحصول على كائن الـ DAO الذي تتعامل من خلاله مع قاعدة البيانات.
        //هذان السطران مسؤولان عن الفحص المحلي في قاعدة البيانات الداخلية للهاتف (Room Database)
        // للتأكد من أن هذا المستخدم مسجل مسبقاً في جداول التطبيق.

        //AppDataBase.getDB(this):
        // يقوم بفتح أو جلب نسخة نشطة من قاعدة البيانات المحلية للتطبيق (المبنية بنظام Room Database).
        //.getMyUserQuery():
        // استدعاء دالة الـ DAO (Data Access Object). الـ DAO هي الواجهة (Interface)
        // التي تحتوي على أوامر واستعلامات الـ SQL (مثل الحذف، الإضافة، أو البحث).
        //يتم تخزين هذا الرابط في المتغير userQuery لكي نتمكن من إرسال الاستعلامات من خلاله.
        MyUserQuery userQuery = AppDataBase.getDB(this).getMyUserQuery();

        //userQuery.getUserByEmail(email): إرسال أمر بحث (Query)
        // لقاعدة البيانات المحلية للبحث عن مستخدم يمتلك هذا البريد الإلكتروني (email) بالتحديد.
        //MyUser user: كائن (Object) يمثل جدول المستخدم؛ إذا وُجد الحساب في الهاتف، سيتم ملء
        // هذا الكائن ببيانات المستخدم (كالاسم، الدور، إلخ)، وإذا لم يُوجد، ستكون قيمته فارغة (null).
        MyUser user = userQuery.getUserByEmail(email);

        //إذا تبين بعد البحث في قاعدة البيانات المحلية (Room) أن هذا الإيميل
        // غير مسجل في جدول المستخدمين من قبل، نقوم بـ:
        //إظهار رسالة خطأ للمستخدم فوق حقل الإيميل: "User not found" (المستخدم غير موجود).
        //تحويل قيمة المتغير isValid إلى false لإيقاف عملية تسجيل الدخول.

        if (user == null) {
            etMail1.setError("User not found");
            isValid = false;
        }

        // //إذا مرّت كل الفحوصات السابقة بنجاح وظلت قيمة isValid تساوي true:
        //  //نستدعي الدالة الجاهزة signInWithEmailAndPassword
        // // لإرسال البريد وكلمة المرور إلى سيرفر الـ Firebase للتحقق الأمني منهما.
        //  //نربط العملية بـ المستمع addOnCompleteListener
        //  // والذي ينتظر نتيجة الطلب من السيرفر (سواء نجحت العملية أو فشلت).
        if (isValid)
        {
            auth.signInWithEmailAndPassword(email, password)
                    // هي تُنفَّذ عندما تنتهي العملية بالكامل سواء نجحت ✅ أو فشلت
                    .addOnCompleteListener(LoginActivity.this, task -> {

                        //تظهر رسالة للمستخدم (Toast) تخبره: "Login successful" (نجح تسجيل الدخول).
                        //يتم إنشاء Intent للانتقال فوراً إلى شاشة اختيار الدور (RoleSelection).
                        //يتم استدعاء دالة finish() لإغلاق شاشة تسجيل الدخول نهائياً من الذاكرة.

                        if (task.isSuccessful()) {

                            Toast.makeText(LoginActivity.this,
                                    "Login successful",
                                    Toast.LENGTH_SHORT).show();


                            Intent intent =
                                    new Intent(LoginActivity.this, RoleSelection.class);
                            startActivity(intent);
                            finish();
                        }
                        //   ظهر رسالة (Toast) مفادها "Login failed".
                        //السطر الذكي (? : - Ternary Operator): يقوم بجلب سبب الفشل الدقيق القادم من سيرفر Firebase
                        // (مثل: "كلمة المرور خاطئة" أو "الحساب محظور")
                        // ويعرضه كرسالة خطأ (setError) فوق حقل الإدخال.

                        else {

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




