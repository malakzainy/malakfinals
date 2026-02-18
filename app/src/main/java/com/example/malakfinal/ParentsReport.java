package com.example.malakfinal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * ParentsReport Activity
 *
 * هذا الـ Activity مسؤول عن عرض تقرير خاص بالأهل،
 * ويحتوي على معلومات المريض الطبية مثل:
 * الاسم، العمر، رقم المريض، التشخيص،
 * آخر زيارة، الأدوية، الملاحظات و Peak Flow.
 *
 * كما يحتوي على أزرار للحفظ، الطباعة، والانتقال لشاشة أخرى.
 */
public class ParentsReport extends AppCompatActivity {

    /** عنوان الصفحة */
    private TextView tvHeader;

    /** اسم العيادة */
    private TextView clinic;

    /** نص ثابت لاسم المريض */
    private TextView Name;

    /** حقل إدخال اسم المريض */
    private TextView etName;

    /** حقل عرض عمر المريض */
    private TextView Age;

    /** نص ثابت لرقم المريض */
    private TextView Patient;

    /** حقل إدخال رقم المريض */
    private TextView etPatientId;

    /** نص ثابت للتشخيص */
    private TextView Diagnosis;

    /** حقل إدخال التشخيص */
    private TextView etDiagnosis;

    /** نص ثابت لآخر زيارة */
    private TextView LastV;

    /** حقل إدخال تاريخ آخر زيارة */
    private TextView etLastVisit;

    /** نص ثابت للأدوية */
    private TextView Medications;

    /** حقل إدخال الأدوية */
    private TextView etMedications;

    /** نص ثابت للملاحظات */
    private TextView DN;

    /** حقل إدخال الملاحظات */
    private TextView etNotes;

    /** نص ثابت لقيمة Peak Flow */
    private TextView PeakFlow;

    /** حقل إدخال قيمة Peak Flow */
    private TextView etPeakFlow;


    /** زر حفظ التقرير */
    private Button btnSave;

    /** زر مسح / الانتقال لشاشة أخرى */
    private Button btnTree;

    /**
     * دالة onCreate
     *
     * يتم استدعاؤها عند إنشاء الـ Activity،
     * وتقوم بربط عناصر الواجهة من ملف XML
     * وإعداد مستمعي الأحداث للأزرار.
     *
     * @param savedInstanceState حالة محفوظة للنشاط (إن وجدت)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_child_report);

        // التعامل مع هوامش النظام (Status Bar / Navigation Bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ربط عناصر الواجهة
        tvHeader = findViewById(R.id.tvHeader);
        clinic = findViewById(R.id.clinic);
        Name = findViewById(R.id.Name);
        etName = findViewById(R.id.etName);
        Age = findViewById(R.id.Age);
        Patient = findViewById(R.id.Patient);
        etPatientId = findViewById(R.id.etPatientId);
        Diagnosis = findViewById(R.id.Diagnosis);
        etDiagnosis = findViewById(R.id.etDiagnosis);
        LastV = findViewById(R.id.LastV);
        etLastVisit = findViewById(R.id.etLastVisit);
        Medications = findViewById(R.id.Medications);
        etMedications = findViewById(R.id.etMedications);
        DN = findViewById(R.id.DN);
        etNotes = findViewById(R.id.etNotes);
        PeakFlow = findViewById(R.id.PeakFlow);
        etPeakFlow = findViewById(R.id.etPeakFlow);
        btnSave = findViewById(R.id.btnSave);
        btnTree = findViewById(R.id.btnTree);


        /**
         * حدث زر Clear
         *
         * عند الضغط على الزر يتم الانتقال
         * إلى شاشة PlantScan.
         */
        btnTree.setOnClickListener(v -> {
            Intent intent = new Intent(ParentsReport.this, AddPlantActivity.class);
            startActivity(intent);


        });
    }
}
