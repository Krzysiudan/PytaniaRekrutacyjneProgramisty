package com.krzysiudan.pytaniarekrutacyjne;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.snackbar.Snackbar;

public class ActivitySingleQuestion extends AppCompatActivity {


    private int isShown = 0;
    private int wereCongratulated = 0;
    private ProgressBar progressBar;
    private int numberOfQuestionsInCategory;
    private AdView mAdView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_question);
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);

        Intent intent = getIntent();
        final String category = intent.getStringExtra("Category");

        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        final String question = databaseAccess.getQuestion(category);
        numberOfQuestionsInCategory = databaseAccess.getNumberOfQuestionsInCategory(category);
        int numberOfQuestionAnsweredCorrectly = databaseAccess.getNumberOfQuestionsAnsweredCorrectlyInCategory(category);

        final TextView textViewAnswerQuestion = (TextView) findViewById(R.id.textViewQuestion);
        textViewAnswerQuestion.setMovementMethod(new ScrollingMovementMethod());
        textViewAnswerQuestion.setText(question);

        final Button buttonShowAnswer = (Button) findViewById(R.id.buttonShowAnswer);
        ImageButton buttonAnswerCorrect = (ImageButton) findViewById(R.id.imageButtonCorrect);
        ImageButton buttonAnswerWrong = (ImageButton) findViewById(R.id.imageButtonWrong);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(numberOfQuestionsInCategory);
        progressBar.setProgress(numberOfQuestionAnsweredCorrectly);
        if(numberOfQuestionAnsweredCorrectly==numberOfQuestionsInCategory){
            buttonShowAnswer.setText("Resetuj");
        }


        buttonAnswerCorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewAnswerQuestion.scrollTo(0,0);
                databaseAccess.answerCorrect(textViewAnswerQuestion.getText().toString());
                String questionNew = databaseAccess.getQuestion(category);
                textViewAnswerQuestion.setText(questionNew);
                progressBar.setProgress(progressBar.getProgress()+1);
                isShown=0;
                showSnackbar("Rządzisz! Spróbuj się z następnym",viewGroup);
                Log.d("Activity","Actual progress : "+ progressBar.getProgress());
                Log.d("Activity","numberOfQuestionsInCategory : "+ numberOfQuestionsInCategory);
                if(progressBar.getProgress()==numberOfQuestionsInCategory && wereCongratulated == 0){
                    alertAllAnswersCorrect();
                    buttonShowAnswer.setText("Resetuj");
                    wereCongratulated=1;
                }


            }
        });

        buttonAnswerWrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewAnswerQuestion.scrollTo(0,0);
                String questionNew = databaseAccess.getQuestion(category);
                textViewAnswerQuestion.setText(questionNew);
                isShown=0;
                showSnackbar("Następnym razem Ci się uda!",viewGroup);


            }
        });

        buttonShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isShown==0 && progressBar.getProgress()!=numberOfQuestionsInCategory) {
                    String answer = databaseAccess.getAnswer(textViewAnswerQuestion.getText().toString());
                    textViewAnswerQuestion.setText(answer);
                    isShown = 1;
                }
                if(progressBar.getProgress()==numberOfQuestionsInCategory){
                    buttonShowAnswer.setText("Pokaż odpowiedź");
                    progressBar.setProgress(0);
                    databaseAccess.answersReset(category);
                    wereCongratulated = 0;

                }
            }
        });




    }

    private void showSnackbar(String text, View parentView){
        Snackbar.make(parentView, text, Snackbar.LENGTH_SHORT)
                .show();
    }

    private void alertAllAnswersCorrect(){
            new AlertDialog.Builder(this)
                    .setTitle("Gratulacje")
                    .setMessage("Znasz już wszystkie odpowiedzi z tej kategorii!")
                    .setPositiveButton((R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();

    }

}
