
package com.example.malakfinal.network;

import com.example.malakfinal.utils.ResponseCallback;
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.ai.client.generativeai.type.InvalidStateException;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * فئة مساعدة للتواصل مع خدمة الذكاء الاصطناعي التابعة google
 * Gemini
 */
public class GeminiHelper {
    public static final String GEMINI_Version = "gemini-2.0-flash";    // ‏إصدار ال gemini الذي يمكن استعماله
    private static String GEMINI_API_KEY = "YOUR_API_KEY";   // مفتاح التطبيق الذي نسخه من الموقع التابع gemini
    private static GeminiHelper instance;    // كائن وحيد الذي يساعدنا على عدم بناء أكثر من كائن لهذه الخدمة ويسمى singleton
    /**
     * كائن يمثل نموذج Gemini ويُستخدم لإرسال الطلبات
     * واستقبال الردود بشكل غير متزامن.
     */
    private GenerativeModelFutures gemini;

    /**
     * مسؤول عن تشغيل المهام في الخلفية بخيط واحد
     * حتى لا تتوقف واجهة المستخدم.
     */
    private final Executor executor = Executors.newSingleThreadExecutor();

    // دالة بنائيه لبناء الموديل التابع gemini
    // ‏تحتاج دراع رقم النسخة أو الإصدار ومفتاح التطبيق للاستعمال
    private GeminiHelper() {
        GenerativeModel generativeModel = new GenerativeModel(
                GEMINI_Version,
                GEMINI_API_KEY
        );
        gemini = GenerativeModelFutures.from(generativeModel);
    }

    // ‏هذه العملية تساعد على عدم بناء أكثر من كائن لهذه الفئة بإرجاع مؤشر واحد
    public static GeminiHelper getInstance() {
        if (null == instance) {
            instance = new GeminiHelper();
        }
        return instance;
    }

    /*** ‏هذه العملية تتلقى جملة لإ بإرسالها لخدمة الذكاء الاصطناعي Gemini وتنتظر الرد
     * @param prompt   Geminiجملة الاستعلام أو الطلب من الذكاء الاصطناعي
     * @param callback Gemini كائن لمعالجة الرد */
    /**
     * يقوم بإرسال رسالة (prompt) إلى نموذج Gemini واستقبال الرد بشكل غير متزامن (Asynchronous).
     *
     * يتم تحويل النص إلى كائن Content، ثم يتم إرسال الطلب باستخدام generateContent.
     * النتيجة تُرجع عبر Callback:
     * - onResponse: عند نجاح العملية
     * - onError: عند حدوث خطأ
     *
     * @param prompt   النص الذي سيتم إرساله إلى النموذج
     * @param callback كائن Callback للتعامل مع النتيجة أو الخطأ
     */
    public void sendMessage(String prompt, ResponseCallback callback) {

        // إنشاء كائن Content يحتوي على النص المُدخل (prompt)
        com.google.ai.client.generativeai.type.Content content =
                new com.google.ai.client.generativeai.type.Content.Builder()
                        .addText(prompt)
                        .build();

        // إرسال الطلب إلى Gemini واستلام النتيجة كـ Future (عملية غير متزامنة)
        ListenableFuture<GenerateContentResponse> response = gemini.generateContent(content);

        // إضافة Callback للتعامل مع نتيجة العملية عند اكتمالها
        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {

            /**
             * يتم استدعاؤها عند نجاح الطلب
             *
             * @param result نتيجة الاستجابة من النموذج
             */
            @Override
            public void onSuccess(GenerateContentResponse result) {

                // التحقق من أن النتيجة ليست null
                if (result != null) {
                    try {
                        // إرسال النص الناتج إلى الـ callback
                        callback.onResponse(result.getText());

                    } catch (InvalidStateException e) {
                        // في حال حدث خطأ أثناء قراءة النتيجة
                        callback.onError(e);
                    }

                } else {
                    // في حال كانت النتيجة فارغة
                    callback.onError(new Throwable("Response is null"));
                }
            }

            /**
             * يتم استدعاؤها عند فشل الطلب
             *
             * @param t الخطأ الذي حدث أثناء التنفيذ
             */
            @Override
            public void onFailure(Throwable t) {
                // تمرير الخطأ إلى الـ callback
                callback.onError(t);
            }

        }, executor); // executor مسؤول عن تشغيل الـ callback في thread مناسب
    }
}
