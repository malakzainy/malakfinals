package com.example.malakfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * ChildReport هي شاشة تعرض تقريرًا طبيًا لطفل.
 * <p>
 * تحتوي هذه الصفحة على معلومات مثل الاسم، العمر، رقم المريض،
 * التشخيص، آخر زيارة، الأدوية، الملاحظات، وقيمة Peak Flow.
 * كما تحتوي على أزرار للحفظ، الطباعة، ومسح البيانات.
 */
public class ChildReport extends AppCompatActivity {

    /** عنوان الصفحة */
    private TextView tvHeader;

    /** اسم العيادة */
    private TextView clinic;

    /** تسمية حقل الاسم */
    private TextView Name;

    /** حقل إدخال اسم الطفل */
    private TextView etName;

    /** تسمية حقل العمر */
    private TextView Age;

    /** حقل إدخال عمر الطفل */
    private EditText etAge;

    /** تسمية رقم المريض */
    private TextView Patient;

    /** حقل إدخال رقم المريض */
    private TextView etPatientId;

    /** تسمية التشخيص */
    private TextView Diagnosis;

    /** حقل إدخال التشخيص */
    private TextView etDiagnosis;

    /** تسمية آخر زيارة */
    private TextView LastV;

    /** حقل إدخال تاريخ آخر زيارة */
    private TextView etLastVisit;

    /** تسمية الأدوية */
    private TextView Medications;

    /** حقل إدخال الأدوية */
    private TextView etMedications;

    /** تسمية الملاحظات */
    private TextView DN;

    /** حقل إدخال الملاحظات */
    private TextView etNotes;

    /** تسمية Peak Flow */
    private TextView PeakFlow;

    /** حقل إدخال قيمة Peak Flow */
    private TextView etPeakFlow;



    /** زر حفظ التقرير */
    private Button btnSave;

    /** زر مسح البيانات */
    private Button btnTree;

    /**
     * يتم استدعاء هذه الدالة عند إنشاء الشاشة.
     * تقوم بربط عناصر الواجهة (XML) بالمتغيرات البرمجية
     * وإعداد أحداث النقر على الأزرار.
     *
     * @param savedInstanceState البيانات المحفوظة عند إعادة إنشاء الصفحة
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_child_report);

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

        // زر مسح البيانات
        btnTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChildReport.this, AddPlantActivity.class);
                startActivity(intent);
            }
        });

        // زر الطباعة

        // زر الحفظ
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChildReport.this, AddPlantActivity.class);
                startActivity(intent);
            }
        });
    }
}

