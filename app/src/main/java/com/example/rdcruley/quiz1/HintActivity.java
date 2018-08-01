package com.example.rdcruley.quiz1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HintActivity extends AppCompatActivity {
    private Button mShowAnswerButton;
    private TextView mAnswerTextView;
    private boolean clickedHint = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);

        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //boolean hint = getIntent().getBooleanExtra("HINT", false);
                //mAnswerTextView.setText("" + hint);
                int hint = getIntent().getIntExtra("HINT",0);
                mAnswerTextView.setText(hint);
                Intent data = new Intent();
                data.putExtra("y", RESULT_OK);
                setResult(RESULT_OK, data);
            }
        });

    }


}
