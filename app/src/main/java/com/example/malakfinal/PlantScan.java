package com.example.malakfinal;

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
 * PlantScan Activity
 *
 * <p>
 * تمثل هذه الشاشة واجهة فحص النبات، حيث تتيح للمستخدم
 * التقاط صورة للنبات والانتقال إلى شاشة عرض نتيجة الفحص.
 * </p>
 */
public class PlantScan extends AppCompatActivity {

    /**
     * عنصر نصي لعرض عنوان الشاشة.
     */
    private TextView tv_title;

    /**
     * زر مخصص لالتقاط صورة للنبات.
     */
    private Button btn_take_photo;

    /**
     * زر مخصص لبدء عملية فحص النبات.
     */
    private Button btn_scan_plant;

    /**
     * يتم استدعاء هذه الدالة عند إنشاء الـ Activity.
     *
     * <p>
     * تقوم بتهيئة واجهة المستخدم، ربط عناصر الواجهة،
     * وضبط أحداث النقر الخاصة بالأزرار.
     * </p>
     *
     * @param savedInstanceState الحالة المحفوظة سابقًا للنشاط (إن وُجدت)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * تفعيل وضع Edge-to-Edge لعرض المحتوى
         * بشكل متوافق مع شريط الحالة والتنقل.
         */
        EdgeToEdge.enable(this);

        /**
         * ربط الـ Activity مع ملف التصميم XML.
         */
        setContentView(R.layout.activity_plant_scan);

        /**
         * ضبط هوامش الواجهة تلقائيًا حسب أشرطة النظام.
         */
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /**
         * ربط عناصر الواجهة الرسومية مع الكود البرمجي.
         */
        tv_title = findViewById(R.id.tv_title);
        btn_take_photo = findViewById(R.id.btn_take_photo);
        btn_scan_plant = findViewById(R.id.btn_scan_plant);

        /**
         * إعداد حدث النقر على زر فحص النبات.
         */
        btn_scan_plant.setOnClickListener(new View.OnClickListener() {

            /**
             * يتم تنفيذ هذه الدالة عند نقر المستخدم على زر الفحص.
             *
             * <p>
             * تقوم بالانتقال إلى شاشة عرض نتيجة فحص النبات.
             * </p>
             *
             * @param view العنصر الذي تم النقر عليه
             */
            @Override
            public void onClick(View view) {

                /**
                 * إنشاء Intent للانتقال إلى شاشة النتائج.
                 */
                Intent intent = new Intent(PlantScan.this, ScanResult.class);

                /**
                 * بدء Activity شاشة النتائج.
                 */
                startActivity(intent);
            }
        });
    }
}
