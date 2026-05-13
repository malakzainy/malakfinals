package com.example.malakfinal;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.malakfinal.network.GeminiHelper;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.ai.FirebaseAI;
import com.google.firebase.ai.type.Content;
import com.google.firebase.ai.type.GenerateContentResponse;

import com.google.firebase.ai.GenerativeModel;
import com.google.firebase.ai.java.GenerativeModelFutures;
import com.google.firebase.ai.type.GenerativeBackend;

import java.util.concurrent.Executor;

public class SmartTaskAssistant extends AppCompatActivity {
    /**
     * كائن مساعد للتعامل مع Gemini API
     */
    private GeminiHelper geminiHelper;

    /**
     * TextView لعرض عنوان أو وصف المهمة الذكية
     */
    private TextView tvSmartTask;

    /**
     * EditText لإدخال موضوع المهمة من قبل المستخدم
     */
    private EditText etPlantTopic;

    /**
     * زر لطلب اقتراح خطوات من الذكاء الاصطناعي
     */
    private Button btnSuggestSteps;

    /**
     * شريط تحميل يظهر أثناء معالجة الطلب
     */
    private ProgressBar pbLoading;

    /**
     * TextView لعرض رد الذكاء الاصطناعي
     */
    private TextView tvAiResponse;

    /**
     * كائن يمثل نموذج Gemini المستخدم لإرسال الطلبات
     */
    private GenerativeModelFutures model;


    @SuppressLint("MissingInflatedId")
    @Override
    // SmartTaskAssistant זימון למחלקת
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_smart_task_assistant);
        EdgeToEdge.enable(this);
        etPlantTopic= findViewById(R.id.etPlantTopic);
        btnSuggestSteps= findViewById(R.id.btnSuggestSteps);
        pbLoading= findViewById(R.id.pbLoading);
        tvAiResponse= findViewById(R.id.tvAiResponse);

        // Initialize the Gemini Developer API backend service
        // Create a `GenerativeModel` instance with a model that supports your use case
        String apiKey = "YOUR_API_KEY"; // Replace with your actual API key




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     *
     * @param topic
     */
    private void askFirebaseAiGeminiForSteps(String topic) {
        GenerativeModel ai = FirebaseAI.getInstance(GenerativeBackend.googleAI())
                .generativeModel("gemini-3-flash-preview");


// Use the GenerativeModelFutures Java compatibility layer which offers
// support for ListenableFuture and Publisher APIs
        model = GenerativeModelFutures.from(ai);

        pbLoading.setVisibility(View.VISIBLE);
        tvAiResponse.setText("");
        btnSuggestSteps.setEnabled(false);


        String promptStr = "I want to perform the following task: '" + topic + "'. " +
                "Can you suggest a clear, step-by-step checklist to complete this task effectively?";


        Content prompt = new Content.Builder()
                .addText(promptStr)
                .build();


        ListenableFuture<GenerateContentResponse> response = model.generateContent(prompt);
        Executor executor = this::runOnUiThread;
        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                pbLoading.setVisibility(View.GONE);
                btnSuggestSteps.setEnabled(true);
                tvAiResponse.setText(result.getText());
            }


            @Override
            public void onFailure(Throwable t) {
                pbLoading.setVisibility(View.GONE);
                btnSuggestSteps.setEnabled(true);
                Toast.makeText(SmartTaskAssistant.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, executor);
    }

}