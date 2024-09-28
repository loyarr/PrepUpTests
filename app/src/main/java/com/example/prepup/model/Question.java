package com.example.prepup.model;

public class Question {
    private int questionNumber;
    private String category;
    private String questionText;

    // Constructor
    public Question(int questionNumber, String category, String questionText) {
        this.questionNumber = questionNumber;
        this.category = category;
        this.questionText = questionText;
    }

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
}

