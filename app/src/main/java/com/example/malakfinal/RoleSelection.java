package com.example.malakfinal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * RoleSelection Activity
 *
 * هذا الـ Activity يسمح للمستخدم باختيار دوره
 * (أهل أو طفل) عند تشغيل التطبيق.
 *
 * بناءً على الاختيار يتم الانتقال
 * إلى الشاشة المناسبة:
 * - ParentsReport للأهل
 * - ChildReport للطفل
 */
public class RoleSelection extends AppCompatActivity {

    /** نص العنوان في أعلى الصفحة */
    private TextView tV;

    /** زر اختيار دور الأهل */
    private Button parents;

    /** زر اختيار دور الطفل */
    private Button child;

    /**
     * دالة onCreate
     *
     * يتم استدعاؤها عند إنشاء الـ Activity،
     * وتقوم بربط عناصر الواجهة (XML)
     * وإعداد أحداث الضغط على الأزرار.
     *
     * @param savedInstanceState حالة محفوظة للنشاط (إن وجدت)
     */
    @SuppressLint("MissingInflatedId")
    @Override
    //RoleSelection זימון למחלקת
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_role_selection);

        // ضبط هوامش النظام (شريط الحالة والتنقل)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ربط عناصر الواجهة
        tV = findViewById(R.id.tV);
        parents = findViewById(R.id.parents);
        child = findViewById(R.id.child);

        /**
         * حدث زر الأهل
         *
         * عند الضغط على الزر يتم الانتقال
         * إلى شاشة تقرير الأهل (ParentsReport).
         */
        parents.setOnClickListener(new View.OnClickListener() { //واجهة تطبيف interface معالج حدث clickلا يمكن بناء كائن منه
            @Override
            public void onClick(View view) {
                // ويبني كائنا من نوع intent يستعمل للانتقال من شاشة الى اخرى
                Intent intent = new Intent(RoleSelection.this, ParentsReport.class);
                startActivity(intent);
            }
        });

        /**
         * حدث زر الطفل
         *
         * عند الضغط على الزر يتم الانتقال
         * إلى شاشة تقرير الطفل (ChildReport).
         */
        child.setOnClickListener(new View.OnClickListener()//واجهة تطبيف interface معالج حدث clickلا يمكن بناء كائن منه
        {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RoleSelection.this, ChildReport.class);
                startActivity(intent);
            }
        });
    }
}
