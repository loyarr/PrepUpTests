package com.example.prepup.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Question implements Parcelable {
    private int questionNumber;
    private String category;
    private String questionText;

    // Constructor
    public Question(int questionNumber, String category, String questionText) {
        this.questionNumber = questionNumber;
        this.category = category;
        this.questionText = questionText;
    }

    protected Question(Parcel in) {
        questionNumber = in.readInt();
        category = in.readString();
        questionText = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    // Getters and setters
    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(questionNumber);
        parcel.writeString(category);
        parcel.writeString(questionText);
    }
}

