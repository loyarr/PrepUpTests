package com.example.prepup.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.prepup.R;
import com.example.prepup.model.Question;

public class QuestionFragment extends Fragment {

    private static final String ARG_QUESTION = "question";

    private Question question;

    public static QuestionFragment newInstance(Question question) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_QUESTION, question);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            question = getArguments().getParcelable(ARG_QUESTION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        TextView questionNumber = view.findViewById(R.id.questionNumber);
        TextView questionCategory = view.findViewById(R.id.questionCategory);
        TextView questionText = view.findViewById(R.id.questionText);

        // Set the question details
        questionNumber.setText("Question " + question.getQuestionNumber());
        questionCategory.setText("Category: " + question.getCategory());
        questionText.setText(question.getQuestionText());

        return view;
    }
}