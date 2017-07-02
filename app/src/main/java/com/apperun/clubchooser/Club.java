package com.apperun.clubchooser;

import android.support.annotation.NonNull;

/**
 * Created by Slang on 6/24/2017.
 */

public class Club implements Comparable<Club>{

    private String clubName;
    private int academics;
    private int sports;
    private int arts;
    private int community;
    private int clubScore;

    public Club(String name){
        clubName = name;
        clubScore = 0;
    }

    public Club(String name, int academics, int artistic, int sports, int community){
        this(name);
        this.academics = academics;
        this.sports = sports;
        this.arts = artistic;
        this.community = community;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }


    public int getClubScore() {
        return clubScore;
    }

    public void setClubScore(int clubScore) {
        this.clubScore = clubScore;
    }

    public void computeClubScore(int totalAcademics, int totalArts, int totalSports, int totalCommunity){
        clubScore = (totalAcademics - this.academics) * (totalAcademics - this.academics) + (totalArts - this.arts) * (totalArts - this.arts) + (totalSports - this.sports) * (totalSports - this.sports) + (totalCommunity - this.community) * (totalCommunity - this.community);
    }

    @Override
    public int compareTo(@NonNull Club c) {
        if (this.clubScore == c.getClubScore()){
            return 0;
        } else if (this.clubScore < c.clubScore){
            return -1;//I'm first
        } else {
            return 1;
        }
    }
}
