package com.apperun.clubchooser;

/**
 * Created by Slang on 7/2/2017.
 */

public class DisplayClub {

    private String clubName;
    private String clubDescription;
    private String clubSponsor;
    private String grades;
    private String cost;
    private String membershipRequirements;
    private String meetings;
    private String events;

    public DisplayClub (String clubName){
        this.clubName = clubName;
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
}
