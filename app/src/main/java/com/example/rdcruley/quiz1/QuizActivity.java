package com.example.rdcruley.quiz1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static java.sql.Types.NULL;

public class QuizActivity extends AppCompatActivity {

    private ImageButton mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mHintButton;
    private Button mCloseButton;
    private TextView mQuestionTextView;
    private TextView mScoreTextView;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question1_1,false,R.string.question2_1),
            new Question(R.string.question1_2,true,R.string.question2_2),
            new Question(R.string.question1_3,false,R.string.question2_3),
            new Question(R.string.question1_4,true,R.string.question2_4),
            new Question(R.string.question1_5,false,R.string.question2_5)
    };
    private int mCurrentIndex = 0;
    private int mCurrentScore = 0;
    private boolean answercorrect = false;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    public void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        mScoreTextView.setText("Score: " + mCurrentScore);
    }
    public void checkAnswer(boolean userPressedTrue){
        boolean answerisTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResID = 0;
        if (userPressedTrue==answerisTrue){
            messageResID = R.string.correct_toast;
            mCurrentScore+=10; }
        else
            messageResID = R.string.incorrect_toast;
        Toast.makeText(QuizActivity.this,messageResID,
                Toast.LENGTH_SHORT).show();
    }

    public void hintOnClick(View view) {
        Intent i = new Intent(QuizActivity.this, HintActivity.class);
        // i.putExtra("HINT",mHintBank[mCurrentIndex].isAnswerTrue());
        i.putExtra("HINT", mQuestionBank[mCurrentIndex].getHintResId());
        // startActivity(i);
        int x = 0;
        startActivityForResult(i, x);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (resultCode==RESULT_OK){
            mCurrentScore-=5;
            Toast.makeText(QuizActivity.this,"5 point penalty for showing hint",
                    Toast.LENGTH_SHORT).show();
            mScoreTextView.setText("Score: " + mCurrentScore);
        }

    }

    public void closeOnClick(View view) {
        finishAffinity();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mTrueButton=(ImageButton)findViewById(R.id.true_button);
        mFalseButton=(Button)findViewById(R.id.false_button);
        mNextButton=(Button)findViewById(R.id.next_button);
        mHintButton=(Button)findViewById(R.id.hint_button);
        mCloseButton = (Button)findViewById(R.id.close_button);
        mQuestionTextView=(TextView) findViewById(R.id.question_text_view);
        mScoreTextView=(TextView) findViewById(R.id.score_text);

        updateQuestion();

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex++;
                if (mCurrentIndex>=5) {
                    Toast.makeText(QuizActivity.this, R.string.done,
                            Toast.LENGTH_LONG).show();
                    mScoreTextView.setText("Final Score: " + mCurrentScore);
                    // setup database to store high score
                    database = FirebaseDatabase.getInstance();
                    myRef = database.getReference("message");
                    // check value in database to see if current higher

                    //ValueEventListener x = myRef.addListenerForSingleValueEvent();
                        // ...
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            int value = dataSnapshot.getValue(Integer.class);
                            Log.d("TAG", "Value is: " + value);
                            if(value >= mCurrentScore)
                                myRef.setValue(mCurrentScore);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.w("TAG", "Failed to read value.", error.toException());
                        }
                    });
                    // Write a message to the database
                    // Log.v("currentscore",""+mCurrentScore);

                }
                else
                updateQuestion();
            }
        });





    }
}
