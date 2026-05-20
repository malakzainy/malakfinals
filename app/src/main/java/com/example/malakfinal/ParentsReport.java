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
public class ParentsReport extends AppCompatActivity {

    /** عنوان الصفحة */
    private TextView tvHeader;


    /** حقل إدخال اسم الطفل */
    private TextView etName;

    /** حقل إدخال عمر الطفل */
    private EditText etAge;


    /** حقل إدخال رقم المريض */
    private TextView etPatientId;

    /** حقل إدخال التشخيص */
    private TextView etDiagnosis;

    /** حقل إدخال تاريخ آخر زيارة */
    private TextView etLastVisit;


    /** حقل إدخال الأدوية */
    private TextView etMedications;

    /** حقل إدخال الملاحظات */
    private TextView etNotes;




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
    @SuppressLint("MissingInflatedId")
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
        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etPatientId = findViewById(R.id.etPatientId);
        etDiagnosis = findViewById(R.id.etDiagnosis);
        etLastVisit = findViewById(R.id.etLastVisit);
        etMedications = findViewById(R.id.etMedications);
        etNotes = findViewById(R.id.etNotes);


    }

}

