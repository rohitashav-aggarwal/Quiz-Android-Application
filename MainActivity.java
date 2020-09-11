package com.example.quizapppractice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    // controller class

    // user-defined integer
    private static final int REQUEST_CODE_CHEAT = 0;

    // Buttons (with m prefix android naming convention)
    private Button mTrueButton, mFalseButton, mNextButton, mCheatButton;
    private TextView mQuestionTextView;

    // array of question class objects
    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_canada, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    // mQuestionBank array index
    private int mCurrentIndex = 0;

    // log tag
    private static final String TAG = "MainActivity";

    // constant key value for saving state while rotation
    private static final String KEY_INDEX = "index";

    // number of correct answers
    int mCountCorrect = 0;

    // did user cheat
    private boolean mIsCheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: called");

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        // referencing buttons with Resource Id's
        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mNextButton = findViewById(R.id.next_button);
        mCheatButton = findViewById(R.id.cheat_button);


        // referencing textView
        mQuestionTextView = findViewById(R.id.question_text_view);
        updateQuestion();

        // setting View.OnClickListener on true button
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // compare answer with question class array
                checkAnswer(true);
            }
        });

        // setting View.OnClickListener on false button
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // compare answer with question class array
                checkAnswer(false);
            }
        });

        // setting View.OnClickListener on Next Button
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsCheater = false;
                if(mCurrentIndex == 0){
                    int scorePercentage = (mCountCorrect * 100)/mQuestionBank.length;
                    Toast.makeText(MainActivity.this, "Percentage: " + scorePercentage, Toast.LENGTH_LONG).show();
                    mCountCorrect = 0;
                }
                updateQuestion();
            }
        });

        // setting View.OnClickListener on cheat button
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(MainActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getStringResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean mUserPressedTrue){
        boolean isAnswerTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messResId = 0;

        if(mIsCheater){
            messResId = R.string.judgment_toast;
        }else {
            if (mUserPressedTrue == isAnswerTrue) {
                messResId = R.string.correct_toast;
                // increment the number of correct answers
                mCountCorrect++;
            } else {
                messResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, messResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: called");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        outState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: called");
    }
}
