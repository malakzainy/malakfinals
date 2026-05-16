package com.example.malakfinal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * SplashScreen Activity
 *
 * <p>
 * تمثل شاشة البداية (Splash Screen) للتطبيق.
 * تعرض شعار التطبيق واسم التطبيق وشعار فرعي، مع إمكانية الانتقال إلى الشاشات التالية.
 * </p>
 *
 * <p>
 * تحتوي على زر للتخطي المباشر إلى الشاشة التالية،
 * بالإضافة إلى انتقال تلقائي بعد فترة زمنية محددة.
 * </p>
 */
public class SplashScreen extends AppCompatActivity {

    /**
     * زر الانتقال عند الضغط عليه.
     */
    private Button btnGo;

    /**
     * صورة شعار التطبيق (وردة).
     */
    private ImageView rose;

    /**
     * نص اسم التطبيق.
     */
    private TextView app_name;

    /**
     * نص الشعار الفرعي للتطبيق.
     */
    private TextView app_tagline;

    /**
     * يتم استدعاء هذه الدالة عند إنشاء الـ Activity.
     *
     * <p>
     * تقوم بتهيئة واجهة المستخدم، ضبط هوامش الشاشة،
     * وربط الأزرار مع الأحداث المناسبة.
     * كما تضبط الانتقال التلقائي إلى الشاشة التالية بعد 3 ثواني.
     * </p>
     *
     * @param savedInstanceState الحالة المحفوظة سابقًا للنشاط (إن وُجدت)
     */
      //كلمة Lint في أندرويد ستوديو هي أداة ذكية تقوم بفحص الكود وتنبيهك إذا كان هناك احتمالية لوجود خطأ أو تحذير (Warning)، وتضع تحت الكود خطاً أصفر أو أحمر.
    //عندما تكتب @SuppressLint، أنت تقول للبرنامج: "أنا أعلم ماذا أفعل، من فضلك تجاهل هذا التحذير ولا تظهره لي".
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    //זימון למחלקת MainActivity
    //تتنفذ مره واحدة عند فتح هذه الشاشه
    protected void onCreate(Bundle savedInstanceState) {
        //كلمة super في لغة الجافا تُستخدم لاستدعاء الدالة من الفئة الأم (Parent Class / Superclass).
        //، الشاشة SplashScreen ترث (extends) من الفئة الأم AppCompatActivity.
        super.onCreate(savedInstanceState);

        /**
         * تفعيل وضع Edge-to-Edge لعرض المحتوى بشكل متوافق مع شريط الحالة وشريط التنقل.
         */
        EdgeToEdge.enable(this);

        /**
         * ربط الـ Activity مع ملف التصميم XML.
         */
        setContentView(R.layout.activity_main_splash_screen);

        /**
         * ربط زر الانتقال مع الكود.
         */
        btnGo = findViewById(R.id.btnGo);

        /**
         * ضبط هوامش الواجهة تلقائيًا حسب أشرطة النظام.
         */
          //"هذا الكود مرتبط بميزة الـ Edge-to-Edge.
        // وظيفته هي حساب أبعاد أشرطة النظام (مثل شريط الحالة العلوي وشريط التنقل السفلي) وتطبيقها كـ Padding (هوامش داخلية) على الحاوية الرئيسية للتطبيق.
        // هذا يضمن عدم تداخل أزرار ونصوص التطبيق مع عناصر النظام أو النوتش (Notch) الخاص بالهاتف،
        // مما يجعل التصميم متناسقاً على جميع أنواع الشاشات."
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /**
         * إعداد حدث النقر على الزر للتنقل إلى شاشة تسجيل الدخول.
         */
        btnGo.setOnClickListener(new View.OnClickListener() {
            /**
             * يتم استدعاؤه عند نقر المستخدم على الزر.
             *
             * @param view العنصر الذي تم النقر عليه
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        /**
         * الانتقال التلقائي إلى شاشة تسجيل الدخول بعد 3 ثواني.
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            //الدالة run() تابعة لواجهة الـ Runnable المفعلة داخل الـ Handler.
            // وظيفتها البرمجية هي احتواء الأوامر التي نريد تنفيذها
            // بعد انتهاء وقت الانتظار (الـ Delay) المحدد بـ 3 ثوانٍ
            public void run() {
                // 1. تحديد خط السير (من شاشة السبراش إلى شاشة تسجيل الدخول)
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                // 2. أمر الانتقال الفعلي وتشغيل الشاشة الجديدة
                startActivity(intent);
                // 3. إنهاء وإغلاق الشاشة الحالية من الذاكرة
                finish();
            }
        }, 3000);

        /**
         * إعداد حدث النقر على الزر للتنقل إلى شاشة اختيار الدور.
         */
        btnGo.setOnClickListener(new View.OnClickListener() {
            /**
             * يتم استدعاؤه عند نقر المستخدم على الزر.
             *
             * @param view العنصر الذي تم النقر عليه
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashScreen.this, RoleSelection.class);
                startActivity(intent);
            }
        });




//        btnGo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent= new Intent(SplashScreen.this, LoginActivity.class);
//                startActivity(intent);
//            }
//        });
    }

}
// في Android، الدالة setError() تُستخدم لإظهار رسالة خطأ على عنصر واجهة المستخدم، عادةً على EditText، لإبلاغ المستخدم أن البيانات المدخلة غير صحيحة.
//
//بمعنى آخر:
//
//عندما تريد إعلام المستخدم أن هناك خطأ في الإدخال، يمكنك استدعاء setError() وسيظهر نص صغير بجانب الحقل.
