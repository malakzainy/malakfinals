
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
    private GenerativeModelFutures gemini;
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
    public void sendMessage(String prompt, ResponseCallback callback) {
        // Create a Content object with the prompt
        com.google.ai.client.generativeai.type.Content content = new com.google.ai.client.generativeai.type.Content.Builder()
                .addText(prompt)
                .build();
                
        ListenableFuture<GenerateContentResponse> response = gemini.generateContent(content);
        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                if (result != null) {
                    try {
                        callback.onResponse(result.getText());
                    } catch (InvalidStateException e) {
                        callback.onError(e);
                    }
                } else {
                    callback.onError(new Throwable("Response is null"));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onError(t);
            }
        }, executor);
    }
}
