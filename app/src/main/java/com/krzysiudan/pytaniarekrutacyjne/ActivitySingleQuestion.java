package com.krzysiudan.pytaniarekrutacyjne;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ActivitySingleQuestion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_question);

        Intent intent = getIntent();
        String selectedCategoty = intent.getStringExtra("Category");

        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        final String question = databaseAccess.getQuestion(selectedCategoty);

        final TextView textViewAnswerQuestion = (TextView) findViewById(R.id.textViewQuestion);
        Button buttonShowAnswer = (Button) findViewById(R.id.buttonShowAnswer);

        textViewAnswerQuestion.setText(question);

        buttonShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer = databaseAccess.getAnswer(question);
                textViewAnswerQuestion.setText(answer);
            }
        });


    }
}
