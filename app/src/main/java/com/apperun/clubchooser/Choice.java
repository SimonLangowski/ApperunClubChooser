package com.apperun.clubchooser;

/**
 * Created by Slang on 7/1/2017.
 */

public class Choice {

    private String choiceName;
    private int sports;
    private int arts;
    private int community;
    private int academics;

    public Choice(String choiceName, int academics, int sports, int arts, int community){
        this.choiceName = choiceName;
        this.sports = sports;
        this.arts = arts;
        this.community = community;
        this.academics = academics;
    }

    public Choice(String choiceName){
        this(choiceName, 0, 0, 0, 0);
    }

    public String getChoiceName() {
        return choiceName;
    }

    public void setChoiceName(String choiceName) {
        this.choiceName = choiceName;
    }

    public int getSports() {
        return sports;
    }

    public void setSports(int sports) {
        this.sports = sports;
    }

    public int getArts() {
        return arts;
    }

    public void setArts(int arts) {
        this.arts = arts;
    }

    public int getCommunity() {
        return community;
    }

    public void setCommunity(int community) {
        this.community = community;
    }

    public int getAcademics() {
        return academics;
    }

    public void setAcademics(int academics) {
        this.academics = academics;
    }
}
