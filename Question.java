package com.example.quizapppractice;

public class Question {

    //question model class

    // string resource id
    private int mStringResId;
    // boolean answer
    private boolean mAnswerTrue;

    public Question(int mStringResId, boolean mAnswerTrue) {
        this.mStringResId = mStringResId;
        this.mAnswerTrue = mAnswerTrue;
    }

    public int getStringResId() {
        return mStringResId;
    }

    public void setStringResId(int stringResId) {
        mStringResId = stringResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
