package com.krzysiudan.pytaniarekrutacyjne;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.snackbar.Snackbar;

public class ActivitySingleQuestion extends AppCompatActivity {


    private int isShown = 0;
    private int answerShown = 0;
    private int wereCongratulated = 0;
    private ProgressBar progressBar;
    private ImageButton buttonShowImage;
    private DatabaseAccess databaseAccess;
    private int numberOfQuestionsInCategory;
    private AdView mAdView;
    private String question;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_question);
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);

        Intent intent = getIntent();
        final String category = intent.getStringExtra("Category");

        databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        question = databaseAccess.getQuestion(category);
        numberOfQuestionsInCategory = databaseAccess.getNumberOfQuestionsInCategory(category);
        int numberOfQuestionAnsweredCorrectly = databaseAccess.getNumberOfQuestionsAnsweredCorrectlyInCategory(category);

        final TextView textViewAnswerQuestion = (TextView) findViewById(R.id.textViewQuestion);
        textViewAnswerQuestion.setMovementMethod(new ScrollingMovementMethod());
        textViewAnswerQuestion.setText(question);

       final Button buttonShowAnswer = findViewById(R.id.buttonShowAnswer);
        ImageButton buttonAnswerCorrect =  findViewById(R.id.imageButtonCorrect);
        ImageButton buttonAnswerWrong =  findViewById(R.id.imageButtonWrong);
        buttonShowImage = findViewById(R.id.imageButtonShowImage);
        buttonShowImage.setVisibility(View.GONE);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        progressBar =  findViewById(R.id.progressBar);
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
                showSnackBar("Rządzisz! Spróbuj się z następnym",viewGroup);
                Log.d("Activity","Actual progress : "+ progressBar.getProgress());
                Log.d("Activity","numberOfQuestionsInCategory : "+ numberOfQuestionsInCategory);
                if(progressBar.getProgress()==numberOfQuestionsInCategory && wereCongratulated == 0){
                    alertAllAnswersCorrect();
                    buttonShowAnswer.setText("Resetuj");
                    wereCongratulated=1;
                }
                hideButtonShowImage();
            }
        });

        buttonAnswerWrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewAnswerQuestion.scrollTo(0,0);
                String questionNew = databaseAccess.getQuestion(category);
                textViewAnswerQuestion.setText(questionNew);
                isShown=0;
                showSnackBar("Następnym razem Ci się uda!",viewGroup);
                hideButtonShowImage();


            }
        });

        buttonShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer ="";
                if(isShown==0 && progressBar.getProgress()!=numberOfQuestionsInCategory) {
                    answer = databaseAccess.getAnswer(textViewAnswerQuestion.getText().toString());
                    textViewAnswerQuestion.setText(answer);
                    isShown = 1;
                }
                if(progressBar.getProgress()==numberOfQuestionsInCategory){
                    buttonShowAnswer.setText("Pokaż odpowiedź");
                    progressBar.setProgress(0);
                    databaseAccess.answersReset(category);
                    wereCongratulated = 0;
                }
                int id = databaseAccess.getIdNumber(answer);
                int countOfImages = databaseAccess.checkHowManyImages(id);
                if(countOfImages!=0){
                    showButtonShowImage();
                }
            }
        });

        buttonShowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer = textViewAnswerQuestion.getText().toString();
                Log.d("Database","Answer for id search: " + answer);
                int id = databaseAccess.getIdNumber(answer);
                int countImages = databaseAccess.checkHowManyImages(id);
                byte[] image = databaseAccess.getImageInByteArray(id);
                showImage(image);
            }
        });


    }

    private void showSnackBar(String text, View parentView){
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

    @Override
    protected void onStart() {
        super.onStart();

    }
    @Override
    protected void onStop() {
        super.onStop();
    }



    private void showImage(byte[] image){
        FragmentManager fm = getSupportFragmentManager();
        FragmentShowingImages fragmentShowingImages = FragmentShowingImages.newInstance(image);
        fragmentShowingImages.show(fm,"fragment_show_image");

    }

    private void showButtonShowImage(){
        buttonShowImage.setVisibility(View.VISIBLE);
    }

    private void hideButtonShowImage(){
        buttonShowImage.setVisibility(View.INVISIBLE);
    }

}
