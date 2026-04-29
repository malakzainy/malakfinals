package com.example.malakfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import com.example.malakfinal.data.MyAsthmaTable.AsthmaUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    /** زر للانتقال الى شاشة التصوير*/
    private Button btnTree;

    /**
     * يتم استدعاء هذه الدالة عند إنشاء الشاشة.
     * تقوم بربط عناصر الواجهة (XML) بالمتغيرات البرمجية
     * وإعداد أحداث النقر على الأزرار.
     *
     * @param savedInstanceState البيانات المحفوظة عند إعادة إنشاء الصفحة
     */
    @Override
    // זימון למחלקת ChildReport
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

        //الـ Listener “يستمع” للحدث، وعندما يحدث ينفذ الكود الذي حددته. زر الحفظ
        //setOnClickListener هي ميثود في Android تُستخدم لجعل عنصر واجهة المستخدم (مثل زر Button) يتفاعل عند الضغط عليه.
        //عندما تضغط على الزر، يتم تنفيذ الكود الموجود داخل Listener.
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        // read and validate all fields
        String name = etName.getText().toString().trim();
        if (name.isEmpty()) {
            etName.setError("Name is required");
            return;
        }

        String age = etAge.getText().toString().trim();
        if (age.isEmpty()) {
            etAge.setError("Age is required");
            return;
        }

        String patientId = etPatientId.getText().toString().trim();
        if (patientId.isEmpty()) {
            etPatientId.setError("Patient ID is required");
            return;
        }

        String diagnosis = etDiagnosis.getText().toString().trim();
        if (diagnosis.isEmpty()) {
            etDiagnosis.setError("Diagnosis is required");
            return;
        }

        String lastVisit = etLastVisit.getText().toString().trim();
        if (lastVisit.isEmpty()) {
            etLastVisit.setError("Last Visit is required");
            return;
        }

        String medications = etMedications.getText().toString().trim();
        if (medications.isEmpty()) {
            etMedications.setError("Medications is required");
            return;
        }

        String notes = etNotes.getText().toString().trim();
        if (notes.isEmpty()) {
            etNotes.setError("Notes is required");
            return;
        }

        String peakFlow = etPeakFlow.getText().toString().trim();
        if (peakFlow.isEmpty()) {
            etPeakFlow.setError("Peak Flow is required");
            return;
        }
            }
        });
    }

    /**
     * حفظ النبات في Firebase Realtime Database.
     */
    public void savePlants(AsthmaUser asthma) {
        /**
         * مرجع إلى الجذر (Root) في Firebase Realtime Database.
         *
         * <p>
         * يقوم هذا السطر بإنشاء كائن من نوع DatabaseReference
         * يشير إلى الجذر الأساسي لقاعدة البيانات باستخدام
         * FirebaseDatabase.getInstance().
         * </p>
         *
         * يمكن استخدام هذا المرجع لقراءة البيانات أو إضافتها
         * أو تعديلها أو حذفها من قاعدة البيانات.
         *
         * مثال:
         * database.child("users").setValue(userObject);
         */
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference plantsRef = database.child("asthmaUsers");
        DatabaseReference newPlantRef = plantsRef.push();
        asthma.setUserId(newPlantRef.getKey());
        plantsRef.child(asthma.getUserId()).setValue(asthma)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(ChildReport.this, "Succeeded to add User", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ChildReport.this, ScanResult.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChildReport.this, "Failed to add User", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

