package com.example.rdcruley.quiz1;

public class Question {

        private int mTextResId;
        private boolean mAnswerTrue;
        private int mHintResId;

        public Question(int textResId, boolean answerTrue, int hintResId){
            mTextResId=textResId;
            mAnswerTrue=answerTrue;
            mHintResId=hintResId;
        }

    public int getTextResId() {
        return mTextResId;
    }
    public int getHintResId() {
        return mHintResId;
    }
    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }
    public void setHintResId(int hintResId) {
        mHintResId = hintResId;
    }
    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }


    @Override
    public String toString() {
        return "Question{" +
                "mTextResId=" + mTextResId +
                ", mAnswerTrue=" + mAnswerTrue +
                '}';
    }



}
