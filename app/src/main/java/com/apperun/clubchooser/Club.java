package com.apperun.clubchooser;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Slang on 6/24/2017.
 */

public class Club implements Serializable, Comparable<Club>{

    private String clubName;
    private String clubDescription;
    private String clubSponsor;
    private String grades;
    private String cost;
    private String membershipRequirements;
    private String meetings;
    private String events;
    static final long serialVersionUID = 1342895734852654L;
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

    public Club(String name, String description){
        clubName = name;
        this.clubDescription = description;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubDescription() {
        return clubDescription;
    }

    public void setClubDescription(String clubDescription) {
        this.clubDescription = clubDescription;
    }

    public String getClubSponsor() {
        return clubSponsor;
    }

    public void setClubSponsor(String clubSponsor) {
        this.clubSponsor = clubSponsor;
    }

    public String getGrades() {
        return grades;
    }

    public void setGrades(String grades) {
        this.grades = grades;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getMembershipRequirements() {
        return membershipRequirements;
    }

    public void setMembershipRequirements(String membershipRequirements) {
        this.membershipRequirements = membershipRequirements;
    }

    public String getMeetings() {
        return meetings;
    }

    public void setMeetings(String meetings) {
        this.meetings = meetings;
    }
    public String getEvents() {
        return events;
    }

    public void setEvents(String events) {
        this.events = events;
    }

    public int getAcademics() {
        return academics;
    }

    public void setAcademics(int academics) {
        this.academics = academics;
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
