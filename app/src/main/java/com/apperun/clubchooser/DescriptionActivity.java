package com.apperun.clubchooser;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class DescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String clubName = getIntent().getStringExtra("clubname");
        DisplayClub club = getClubData(clubName);
        displayData(club);
    }

    public void displayData(DisplayClub club) {
        TextView sponsor = (TextView) findViewById(R.id.sponsor);
        TextView grades = (TextView) findViewById(R.id.grades);
        TextView description = (TextView) findViewById(R.id.description);
        TextView costs = (TextView) findViewById(R.id.costs);
        TextView requirements = (TextView) findViewById(R.id.requirements);
        TextView meetings = (TextView) findViewById(R.id.meetings);
        TextView events = (TextView) findViewById(R.id.events);

        setTitle(club.getClubName());
        description.setText(getString(R.string.description, club.getClubDescription()));
        if (club.getClubSponsor().equals("")) {
            sponsor.setVisibility(View.GONE);
        } else {
            sponsor.setText(getString(R.string.sponsor, club.getClubSponsor()));
        }
        if (club.getGrades().equals("")) {
            grades.setVisibility(View.GONE);
        } else {
            grades.setText(getString(R.string.grade, club.getGrades()));
        }
        if (club.getCost().equals("")) {
            costs.setVisibility(View.GONE);
        } else {
            costs.setText(getString(R.string.costs, club.getCost()));
        }
        if (club.getMembershipRequirements().equals("")) {
            requirements.setVisibility(View.GONE);
        } else {
            requirements.setText(getString(R.string.requirements,club.getMembershipRequirements()));
        }
        if (club.getMeetings().equals("")) {
            meetings.setVisibility(View.GONE);
        } else {
            meetings.setText(getString(R.string.meetings, club.getMeetings()));
        }
        if (club.getEvents().equals("")) {
            events.setVisibility(View.GONE);
        } else {
            events.setText(getString(R.string.events, club.getEvents()));
        }
    }

    public DisplayClub getClubData(String clubName){
        InputStream is = getApplicationContext().getResources().openRawResource(R.raw.clublist2);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        DisplayClub displayClub = new DisplayClub(clubName);
        int counter = 0;
        try {
            while ((line = br.readLine()) != null) {
                for (int i = 0; i < line.length(); i++){
                    if (line.charAt(i) == '{'){
                        String temp = "";
                        int j = i;
                        while (true){
                            j++;
                            while (j == line.length()){
                                temp = temp + line.substring(i + 1) + " ";
                                line = br.readLine();
                                j = 0;
                                i = -1;
                            }
                            if (line.charAt(j) == '}') {
                                break; //found end
                            }
                        }
                        String word = temp + line.substring(i + 1, j);
                        if (counter == 0){
                            if (!word.equals(clubName)){
                                break;
                            }
                        }
                        switch(counter){
                            case 1: //numbers
                            case 2: //numbers
                            case 3: //numbers
                            case 4: //numbers
                            case 5: //numbers
                            case 6: break; //blank line
                            case 7: displayClub.setClubSponsor(word); break;
                            case 8: displayClub.setGrades(word); break;
                            case 9: displayClub.setClubDescription(word); break;
                            case 10: displayClub.setEvents(word); break;
                            case 11: displayClub.setMeetings(word); break;
                            case 12: displayClub.setMembershipRequirements(word); break;
                            case 13: displayClub.setCost(word);
                                    return displayClub;
                            default: break;

                        }
                        counter = (counter + 1) % 14;
                    }
                }
            }
            br.close();
        } catch (IOException e){
            System.out.println("Club data not found");
        }
        return displayClub;
    }
}
