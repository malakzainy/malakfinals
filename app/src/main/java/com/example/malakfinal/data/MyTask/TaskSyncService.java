package com.example.malakfinal.data.MyTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//الـ Service هي عبارة عن مكون بيشتغل في الخلفية (Background).
//
// يعني بتنفذ عمليات طويلة عريضة بدون ما تضطر تعرض واجهة مستخدم (UI) للمستخدم
// ، وبتضل شغالة حتى لو المستخدم طلع من التطبيق أو فتح تطبيق ثاني.
public class TaskSyncService extends Service
{
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 1. التأكد إن الـ Intent مش فاضي وبيحتوي على البيانات المطلوبة
        if (intent != null && intent.hasExtra("task_extra")) {
            // 2. استخراج كائن النبتة (Plant) المرسل من الـ Activity
            Plant p = (Plant) intent.getSerializableExtra("task_extra");
            // 3. استدعاء الدالة المسؤولة عن الرفع لـ Firebase
            saveMyTaskToFirebase((Plant) p);
        }
        // 4. تحديد سلوك النظام إذا ماتت الخدمة
        return START_NOT_STICKY;
    }
    private void saveMyTaskToFirebase(Plant p) {
        // 1. الاتصال بـ Firebase وتحديد المسار (Node) واسمه "plants"
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("plants");

        // 2. إنشاء مفتاح (ID) فريد وتلقائي للنبتة الجديدة داخل الـ Database
        String key = myRef.push().getKey();
        p.setKey(key); // تخزين هذا المفتاح داخل كائن النبتة نفسه

        // 3. رفع البيانات إلى Firebase
        myRef.child(key).setValue(p).addOnCompleteListener(fbTask -> {
            if (fbTask.isSuccessful()) {
                // إذا نجح الرفع، بنظهر رسالة Toast للمستخدم
                Toast.makeText(getApplicationContext(), "Sync Successful", Toast.LENGTH_SHORT).show();
            }
            // 4. الخطوة الأهم: التدمير الذاتي!
            stopSelf();
        });
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null; // We are using a Started Service, not a Bound Service
        //دالة onBind إجبارية في أي Service. بما إنك مرجع null، هاد معناه إنك عم تستخدم نوع اسمه Started Service (خدمة بتبدأ وبتروح تعمل شغلها)
        // Bound Service (خدمة برتبط فيها الـ Activity عشان يحكوا مع بعض).
    }
}



