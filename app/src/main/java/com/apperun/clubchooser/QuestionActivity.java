package com.apperun.clubchooser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {

    TextView question;
    Button choice1;
    Button choice2;
    Button choice3;
    Button choice4;
    Button choice5;  //choice 5 may become a go back to last question button
    Button[] buttons;
    ArrayList<Question> questions;
    int currentQuestionNumber;
    SharedPreferences storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        question = (TextView) findViewById(R.id.question);
        choice1 = (Button) findViewById(R.id.choice1);
        choice2 = (Button) findViewById(R.id.choice2);
        choice3 = (Button) findViewById(R.id.choice3);
        choice4 = (Button) findViewById(R.id.choice4);
        choice5 = (Button) findViewById(R.id.choice5);
        buttons = new Button[] {choice1, choice2, choice3, choice4, choice5};
        questions = new ArrayList<Question>();
        Question testQuestion = new Question("What is your favorite color?", new String[] {"Yellow", "Red", "Green"});
        testQuestion.addChoice("blue");
        questions.add(testQuestion);
        storage = getPreferences(0);
        if (storage.contains("questionNumber")) {
            currentQuestionNumber = storage.getInt("questionNumber", 0);
        } else {
            currentQuestionNumber = 0;
        }
        if (getIntent().getBooleanExtra("restart", false)){
            currentQuestionNumber = 0;
            SharedPreferences.Editor editor = storage.edit();
            //editor.clear() //removes ALL stored preferences across app
            for (int i = 0; i < questions.size(); i++) {
                String key = "question" + i;
                if (storage.contains(key)) {
                    editor.remove(key);
                }
            }
            editor.commit();
        } else {  //get old answers
            SharedPreferences.Editor editor = storage.edit();
            for (int i = 0; i < questions.size(); i++) {
                String key = "question" + i;
                if (storage.contains(key)) {
                    int previousAnswer = storage.getInt(key, 0);
                    questions.get(i).setSelected(previousAnswer);
                } else {
                    //currentQuestionNumber = i; //should be true but retrieved from storage already
                    break; //all other questions yet to be answered
                }
            }
            editor.commit();
        }



        if (currentQuestionNumber < questions.size()) {
            updateQuestionDisplay(questions.get(currentQuestionNumber));
        } else {
            question.setText("Please restart");
            for (int i = 0; i < buttons.length; i++){
                buttons[i].setVisibility(View.INVISIBLE);
            }
            //should automatically display last result screen when implemented properly
        }

    }

    public void updateQuestionDisplay(Question q){
        final Question q2 = q;
        question.setText(q.getQuestion());
        ArrayList<String> choices = q.getChoices();
        for (int i = 0; i < buttons.length; i++){
            if (i < choices.size()){
                Button b = buttons[i];
                b.setText(choices.get(i));
                final int i2 = i;
                b.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        q2.setSelected(i2);
                        nextQuestion(q2);
                    }
                });
                b.setVisibility(View.VISIBLE);
            } else {
                buttons[i].setVisibility(View.GONE);
            }
        }
    }

    public void nextQuestion(Question currentQuestion){
        currentQuestionNumber++; //store q number and q answer
        SharedPreferences.Editor editor = storage.edit();
        editor.putInt("questionNumber", currentQuestionNumber);
        String key = "question" + (currentQuestionNumber - 1); //since writing data for last question now
        editor.putInt(key, currentQuestion.getSelected());
        editor.commit();
        if (currentQuestionNumber < questions.size()){
            updateQuestionDisplay(questions.get(currentQuestionNumber));
        } else {
            calculateClubs();
        }
    }

    public void calculateClubs(){
        //do something here

        String answer = questions.get(0).getAnswer();
        Toast toast = Toast.makeText(getApplicationContext(), "You answered " + answer, Toast.LENGTH_LONG);
        toast.show();
        //maybe add a results activity
        Intent intent = new Intent(getApplicationContext(), DescriptionActivity.class);
        //use put extra to add data to intent
        startActivity(intent);
    }

}
