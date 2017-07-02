package com.apperun.clubchooser;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Slang on 6/24/2017.
 */

public class Question {

    private Choice[] choices;
    private int selected;
    private int id;
    private int counter;
    private static int questionCounter = 0;


    public Question(Choice choice1, Choice choice2, Choice choice3, Choice choice4){
        this(new Choice[] {choice1, choice2, choice3, choice4});
    }

    public Question(Choice[] arrayChoices){
        selected = -1;
        this.choices = arrayChoices;
        id = questionCounter;
        questionCounter++;
        counter = 0;
    }

    public void addChoice(Choice choice){
        choices[counter] = choice;
        counter++;
    }

    public void addChoices(Choice[] choices){
        for (int i = 0; i < choices.length; i++){
            this.choices[counter] = choices[i];
            counter++;
        }
    }

    public Choice[] getChoices() {
        return choices;
    }

    public void setSelected(int k){
        if ((k >= 0) && (k < choices.length)){
            selected = k;
        } else {
            System.out.println("Invalid selection number");
        }
    }

    public int getSelected(){
        return selected;
    }

    public Choice getAnswer(){
        if (selected == -1){
            return null;
        } else {
            return choices[selected];
        }
    }

    public void setChoices(Choice[] choices) {
        this.choices = choices;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getQuestionCounter() {
        return questionCounter;
    }

    public static void setQuestionCounter(int questionCounter) {
        Question.questionCounter = questionCounter;
    }

}
