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
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.Executor;

public class SmartTaskAssistant extends AppCompatActivity {
    private GeminiHelper geminiHelper;
    private TextView tvSmartTask;
    private EditText etPlantTopic;
    private Button btnSuggestSteps;
    private ProgressBar pbLoading;
    private TextView tvAiResponse;
    private GenerativeModelFutures model;



    @SuppressLint("MissingInflatedId")
    @Override
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
        GenerativeModel ai = new GenerativeModel("gemini-2.0-flash", apiKey);

        // Use the GenerativeModelFutures Java compatibility layer which offers
        // support for ListenableFuture and Publisher APIs
        model = GenerativeModelFutures.from(ai);



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