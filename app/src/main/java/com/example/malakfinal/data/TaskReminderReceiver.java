package com.example.malakfinal.data;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.malakfinal.AddPlantActivity;
import com.example.malakfinal.MainActivity;

/**
 * BroadcastReceiver مسؤول عن استقبال حدث تذكير بالمهمة
 * وعرض إشعار (Notification) للمستخدم.
 *
 * يتم استدعاؤه عادةً من خلال AlarmManager عند وقت محدد،
 * حيث يحتوي الـ Intent على بيانات المهمة مثل العنوان والوصف.
 */
public class TaskReminderReceiver extends BroadcastReceiver {

    /** معرف قناة الإشعارات الخاصة بتذكير المهام */
    private static final String CHANNEL_ID = "TASK_REMINDER_CHANNEL";

    /**
     * يتم استدعاء هذه الدالة عند استقبال Broadcast.
     * تقوم بقراءة بيانات المهمة من الـ Intent ثم إنشاء وعرض إشعار.
     *
     * @param context سياق التطبيق للوصول إلى خدمات النظام
     * @param intent  يحتوي على بيانات المهمة (title و text)
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        // استخراج عنوان ونص المهمة من الـ Intent
        String title = intent.getStringExtra("title");
        String text = intent.getStringExtra("text");

        // التأكد من إنشاء قناة الإشعارات (مطلوبة من Android 8+)
        createNotificationChannel(context);

        // Intent لفتح AddPlantActivity عند الضغط على الإشعار
        Intent resultIntent = new Intent(context, AddPlantActivity.class);

        // PendingIntent يسمح للنظام بتنفيذ الـ Intent لاحقًا
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // بناء الإشعار
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm) // أيقونة الإشعار
                .setContentTitle("Task Reminder: " + title) // عنوان الإشعار
                .setContentText(text) // نص الإشعار
                .setPriority(NotificationCompat.PRIORITY_HIGH) // أولوية عالية
                .setContentIntent(pendingIntent) // ماذا يحدث عند الضغط
                .setAutoCancel(true); // إخفاء الإشعار بعد الضغط

        // الحصول على مدير الإشعارات من النظام
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // عرض الإشعار باستخدام ID فريد (الوقت الحالي)
        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }

    /**
     * إنشاء قناة إشعارات للأجهزة التي تعمل بنظام Android 8.0 (API 26) فما فوق.
     * بدون هذه القناة، لن تظهر الإشعارات على هذه الإصدارات.
     *
     * @param context سياق التطبيق
     */
    private void createNotificationChannel(Context context) {

        // التحقق من إصدار النظام
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // إعدادات القناة
            CharSequence name = "Task Reminders"; // اسم القناة
            String description = "Channel for Task Reminder Notifications"; // وصف القناة
            int importance = NotificationManager.IMPORTANCE_HIGH; // مستوى الأهمية

            // إنشاء القناة
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // تسجيل القناة في النظام
            NotificationManager notificationManager =
                    context.getSystemService(NotificationManager.class);

            notificationManager.createNotificationChannel(channel);
        }
    }
}