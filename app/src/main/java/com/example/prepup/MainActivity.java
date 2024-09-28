package com.example.prepup;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prepup.model.Question;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText interviewName = findViewById(R.id.interviewName);
        EditText companyName = findViewById(R.id.companyName);
        EditText positionName = findViewById(R.id.positionName);
        EditText vacancyRequirements = findViewById(R.id.vacancyRequirements);
        EditText questionPreferences = findViewById(R.id.questionPreferences);
        Button submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(v -> {
            String interview = interviewName.getText().toString();
            String company = companyName.getText().toString();
            String position = positionName.getText().toString();
            String requirements = vacancyRequirements.getText().toString();
            String preferences = questionPreferences.getText().toString();

            submitForm(interview, company, position, requirements, preferences);
        });
    }

    private void submitForm(String interview, String company, String position, String requirements, String preferences) {
        String prompt = "Interview Name: " + interview +
                "\nCompany: " + company +
                "\nPosition: " + position +
                "\nRequirements: " + requirements +
                "\nPreferences: " + preferences;
        fetchQuestionsFromOpenAI(prompt);
    }

    private void fetchQuestionsFromOpenAI(String prompt) {
        new Thread(() -> {
            try {
                URL url = new URL("https://api.openai.com/v1/chat/completions");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Authorization", "Bearer " + BuildConfig.OpenAI_ApiKey);
                conn.setDoOutput(true);

                // chat model payload
                String jsonInputString = "{\"model\": \"gpt-4o-mini\", \"messages\": [" +
                        "{\"role\": \"system\", \"content\": \"Generate 25 interview questions in JSON format. Each question should have the following fields: int questionNumber, String category, and String questionText. The questions should be categorized as follows: 1. Introduction, 2. Background, 3. Behavioral, 4. Core, 5. Closing.\"}," +
                        "{\"role\": \"user\", \"content\": \"" + escapeJson(prompt) + "\"}]," +
                        "\"max_tokens\": 1500}"; // MAX TOKEN JANGAN DIUBAH BIAR GA BOROS

                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    try (BufferedReader br = new BufferedReader(
                            new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        Log.i("API Response", response.toString());
                        parseJson(response.toString());
                    }
                } else {
                    try (BufferedReader br = new BufferedReader(
                            new InputStreamReader(conn.getErrorStream(), "utf-8"))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        Log.e("API Error", response.toString());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    // escape JSON special characters in prompt
    private String escapeJson(String data) {
        return data.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\b", "\\b")
                .replace("\f", "\\f")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    private void parseJson(String json) {
        runOnUiThread(() -> {
            try {
                Gson gson = new Gson();

                // first, parse the root response as a JSON object
                Type rootType = new TypeToken<Object>(){}.getType();
                Map<String, Object> rootResponse = gson.fromJson(json, rootType);

                // extract "choices" field, which contains the actual content
                List<Map<String, Object>> choices = (List<Map<String, Object>>) rootResponse.get("choices");
                if (choices != null && !choices.isEmpty()) {
                    // get first choice and extract its "message" -> "content"
                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                    String content = (String) message.get("content");

                    // log raw content to see how it's structured
                    Log.i("Generated Questions", content);

                    // parse content into a list
                    Type questionListType = new TypeToken<List<Question>>(){}.getType();
                    List<Question> questionList = gson.fromJson(content, questionListType);

                    // log for now, change later
                    for (Question question : questionList) {
                        Log.i("Question", "Number: " + question.getQuestionNumber() +
                                ", Category: " + question.getCategory() +
                                ", Question: " + question.getQuestionText());
                    }
                }
            } catch (JsonSyntaxException | ClassCastException e) {
                Log.e("JSON Parsing", "Error parsing JSON", e);
            }
        });
    }

}