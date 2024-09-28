package com.example.prepup.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.prepup.fragment.QuestionFragment;
import com.example.prepup.model.Question;

import java.util.List;

public class QuestionPagerAdapter extends FragmentStateAdapter {

    private List<Question> questions;

    public QuestionPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Question> questions) {
        super(fragmentActivity);
        this.questions = questions;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return QuestionFragment.newInstance(questions.get(position));
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
