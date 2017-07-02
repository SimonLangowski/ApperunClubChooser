package com.apperun.clubchooser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.QuoteSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.CharacterCodingException;
import java.util.ArrayList;
import java.util.Collections;

public class QuestionActivity extends AppCompatActivity {

    TextView question;
    Button choice1, choice2,choice3, choice4;
    Button[] buttons;
    ArrayList<Question> questions;
    ArrayList<Club> clubs;
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

        buttons = new Button[] {choice1, choice2, choice3, choice4};

        questions = new ArrayList<Question>();
        Question[] q = makeQuestions();
        clubs = makeClubs();
        for (int i = 0; i < q.length; i++){
            questions.add(q[i]);
        }
        storage = getPreferences(0);
        if (storage.contains("questionNumber")) {
            currentQuestionNumber = storage.getInt("questionNumber", 0);
        } else {
            currentQuestionNumber = 0;
        }
        if (getIntent().getBooleanExtra("restart", false)){
            currentQuestionNumber = 0;
            SharedPreferences.Editor editor = storage.edit();
            editor.clear(); //removes ALL stored preferences across app
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

        question.setText(R.string.question);

        if (currentQuestionNumber < questions.size()) {
            updateQuestionDisplay(questions.get(currentQuestionNumber));
        } else {
            question.setText("Please restart");
            for (int i = 0; i < buttons.length; i++){
                buttons[i].setVisibility(View.INVISIBLE);
            }
            calculateClubs(); //jump to results screen
        }

    }

    public void updateQuestionDisplay(Question q){
        final Question q2 = q;
            Choice[] choices = q.getChoices();
            for (int i = 0; i < buttons.length; i++){
                if (i < choices.length){
                    Button b = buttons[i];
                    b.setText(choices[i].getChoiceName());
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
        int totalAcademics = 0;
        int totalArtistic = 0;
        int totalSports = 0;
        int totalCommunity = 0;

        for (Question q : questions){
            if (q.getAnswer() == null){
                continue;
            }
            totalAcademics += q.getAnswer().getAcademics();
            totalArtistic += q.getAnswer().getArts();
            totalSports += q.getAnswer().getSports();
            totalCommunity += q.getAnswer().getCommunity();
        }

        for (Club c : clubs){
            c.computeClubScore(totalAcademics,totalArtistic,totalSports,totalCommunity);
        }
        Collections.sort(clubs);
        question.setText(clubs.get(0).getClubName());
        for (int i = 0; i < buttons.length; i++){
            buttons[i].setText(clubs.get(i + 1).getClubName());
        }
    }

    public ArrayList<Club> makeClubs(){
        InputStream is = getApplicationContext().getResources().openRawResource(R.raw.clublist);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        String clubName = "hi";
        int academics = 0;
        int artistic = 0;
        int sports = 0;
        int community = 0;
        ArrayList<Club> clubList = new ArrayList<Club>();
        try {
            while ((line = br.readLine()) != null) {
                int counter = 0;
                int temp = 0;
                for (int i = 0; i < line.length(); i++){
                    if (line.charAt(i) == ','){
                        if (counter == 0) {
                            clubName = line.substring(0, i);
                            temp = i;
                            counter++;
                        } else if (counter == 1){
                            academics = Integer.parseInt(line.substring(temp + 1, i));
                            temp = i;
                            counter++;
                        } else if (counter == 2){
                            artistic = Integer.parseInt(line.substring(temp + 1, i));
                            temp = i;
                            counter++;
                        } else if (counter == 3){
                            sports = Integer.parseInt(line.substring(temp + 1, i));
                            temp = i;
                            counter++;
                        } else if (counter == 4){
                            community = Integer.parseInt(line.substring(temp + 1, i));
                            temp = i;
                            break;
                        }
                    }
                }
                clubList.add(new Club(clubName, academics, artistic, sports, community));
            }
        } catch (IOException e){
            System.out.println("Club file not found");
        }
        return clubList;
    }


    public Question[] makeQuestions(){
        Choice art = new Choice("Art", 0,0,2,0);
        Choice reading = new Choice("Reading",1,0,1,0);
        Choice drama = new Choice("Drama", 0,0,2,0);
        Choice running = new Choice("Running",0,2,0,0);
        Choice writing = new Choice("Writing",1,0,1,0);
        Choice discussingPhilosophy = new Choice("Discussing philosophy",1,0,1,0 );
        Choice speakingInAForeignLanguage = new Choice("Speaking in a foreign language",0,0,2,0);
        Choice doingMath = new Choice("Doing math", 1,1,0,0);
        Choice makingFood = new Choice("Making food",0,0,1,1);
        Choice goingToAFootballGame = new Choice("Going to a football game",0,1,0,1);
        Choice eatingFood = new Choice("Eating food",0,0,0,2);
        Choice workingWithMoney = new Choice("Working with money",1,0,0,1);
        Choice media = new Choice("Media", 0,0,2,0);
        Choice debate = new Choice("Debate",1,1,0,0);
        Choice design = new Choice("Design",2,0,0,0);
        Choice jeopary = new Choice("Jeopardy",1,1,0,0);
        Choice trivia = new Choice("Trivia",0,2,0,0);
        Choice captureTheFlag = new Choice("Capture the flag",0,2,0,0);
        Choice volunteering = new Choice("Volunteering",0,0,0,2);
        Choice learning = new Choice("Learning",0,0,0,2);
        Choice readingScientificTheory = new Choice("Reading scientific theory",2,0,0,0);
        Choice programming = new Choice("Programming",2,0,0,0);
        Choice improvisation = new Choice("Improvisation", 0,1,0,1);
        Choice dancing = new Choice("Dancing",0,1,1,0);
        Choice mentoring = new Choice("Mentoring",0,0,0,2);
        Choice medicine = new Choice("Medicine",2,0,0,0);
        Choice photography = new Choice("Photography",0,0,2,0);
        Choice leadership = new Choice("Leadership",0,0,0,2);
        Choice music = new Choice("Music",0,0,2,0);
        Choice solvingPuzzles = new Choice("Solving puzzles",2,0,0,0);
        Choice scientificExperiments = new Choice("Scientific experiments",2,0,0,0);
        Choice competition = new Choice("Competition",0,2,0,0);
        Choice doingStunts = new Choice("Doing stunts",0,0,2,0);
        Choice takingRisks = new Choice("Taking Risks",0,2,0,0);
        Choice practicing = new Choice("Practicing",0,2,0,0);
        Choice recycling = new Choice("Recycling",0,0,0,2);
        Choice helpingPeople = new Choice("Helping people",0,0,0,2);
        Choice teamwork = new Choice("Teamwork",0,2,0,0);
        Choice publicService = new Choice("Public service",0,0,0,2);
        Choice conversation = new Choice("Conversation",0,0,0,2);

        Question q1 = new Question(art,running,volunteering,doingMath);
        Question q2 = new Question(captureTheFlag,programming,recycling,reading);
        Question q3 = new Question(drama,leadership,jeopary,learning);
        Question q4 = new Question(writing,debate,speakingInAForeignLanguage,goingToAFootballGame);
        Question q5 = new Question(photography,medicine,mentoring,competition);
        Question q6 = new Question(readingScientificTheory,discussingPhilosophy,makingFood,takingRisks);
        Question q7 = new Question(improvisation,trivia,media,music);
        Question q8 = new Question(practicing,doingStunts,solvingPuzzles,eatingFood);
        Question q9 = new Question(publicService,teamwork,design,scientificExperiments);
        Question q10 = new Question(conversation,dancing,workingWithMoney,helpingPeople);

        Question[] questions = new Question[] {q1,q2,q3,q4,q5,q6,q7,q8,q9,q10};
        return questions;
    }

}
