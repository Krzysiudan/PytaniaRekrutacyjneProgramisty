package com.krzysiudan.pytaniarekrutacyjne;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ActivitySingleQuestion extends AppCompatActivity {
    private int isShown = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_question);

        Intent intent = getIntent();
        final String category = intent.getStringExtra("Category");

        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        final String question = databaseAccess.getQuestion(category);

        final TextView textViewAnswerQuestion = (TextView) findViewById(R.id.textViewQuestion);
        textViewAnswerQuestion.setMovementMethod(new ScrollingMovementMethod());
        textViewAnswerQuestion.setText(question);

        Button buttonShowAnswer = (Button) findViewById(R.id.buttonShowAnswer);
        ImageButton buttonAnswerCorrect = (ImageButton) findViewById(R.id.imageButtonCorrect);
        ImageButton buttonAnswerWrong = (ImageButton) findViewById(R.id.imageButtonWrong);

        buttonAnswerCorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseAccess.answerCorrect(textViewAnswerQuestion.getText().toString());
                String questionNew = databaseAccess.getQuestion(category);
                textViewAnswerQuestion.setText(questionNew);
                isShown=0;


            }
        });

        buttonAnswerWrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String questionNew = databaseAccess.getQuestion(category);
                textViewAnswerQuestion.setText(questionNew);
                isShown=0;

            }
        });

        buttonShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isShown==0) {
                    String answer = databaseAccess.getAnswer(textViewAnswerQuestion.getText().toString());
                    textViewAnswerQuestion.setText(answer);
                    isShown = 1;
                }
            }
        });




    }
}
