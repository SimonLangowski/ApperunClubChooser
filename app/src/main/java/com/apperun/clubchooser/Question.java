package com.apperun.clubchooser;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Slang on 6/24/2017.
 */

public class Question {
    private String question;
    private ArrayList<String> choices;
    private int selected;

    public Question(String question){ //creates empty choice array, add with add choice method
        this(question, new ArrayList<String>(4));
    }

    public Question(String question, String[] arrayChoices){
        this.question = question;
        this.choices = new ArrayList<String>(4);
        selected = -1;
        for (int i = 0; i < arrayChoices.length; i++){
            choices.add(arrayChoices[i]);
        }
    }

    public Question(String question, ArrayList<String> choices){
        this.question = question;
        this.choices = choices;
        selected = -1;
    }

    public void addChoice(String choice){
        choices.add(choice);
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public void setSelected(int k){
        if ((k >= 0) && (k < choices.size())){
            selected = k;
        } else {
            System.out.println("Invalid selection number");
        }
    }

    public int getSelected(){
        return selected;
    }

    public String getAnswer(){
        if (selected == -1){
            return "No answer selected";
        } else {
            return choices.get(selected);
        }
    }
}
