package com.example.prepup.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.prepup.R;
import com.example.prepup.adapter.QuestionPagerAdapter;
import com.example.prepup.model.Question;

import java.util.List;

public class InterviewActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private List<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview);

        // Assuming you passed the questions as an intent extra
        questions = getIntent().getParcelableArrayListExtra("questions");

        viewPager = findViewById(R.id.viewPager);
        QuestionPagerAdapter adapter = new QuestionPagerAdapter(this, questions);
        viewPager.setAdapter(adapter);
    }
}