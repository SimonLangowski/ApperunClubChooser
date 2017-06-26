package com.apperun.clubchooser;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class DescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Club testClub = new Club("Math Club", "Where people do math");
        testClub.setClubSponsor("Linda Jones");
        testClub.setGrades("13th graders only");
        displayData(testClub);

    }

    public void displayData(Club club) {
        TextView sponsor = (TextView) findViewById(R.id.sponsor);
        TextView grades = (TextView) findViewById(R.id.grades);
        TextView description = (TextView) findViewById(R.id.description);
        TextView costs = (TextView) findViewById(R.id.costs);
        TextView requirements = (TextView) findViewById(R.id.requirements);
        TextView meetings = (TextView) findViewById(R.id.meetings);
        TextView events = (TextView) findViewById(R.id.events);

        setTitle(club.getClubName());
        description.setText(getString(R.string.description, club.getClubDescription()));
        if (club.getClubSponsor() == null) {
            sponsor.setVisibility(View.GONE);
        } else {
            sponsor.setText(getString(R.string.sponsor, club.getClubSponsor()));
        }
        if (club.getGrades() == null) {
            grades.setVisibility(View.GONE);
        } else {
            grades.setText(getString(R.string.grade, club.getGrades()));
        }
        if (club.getCost() == null) {
            costs.setVisibility(View.GONE);
        } else {
            costs.setText(getString(R.string.costs, club.getCost()));
        }
        if (club.getMembershipRequirements() == null) {
            requirements.setVisibility(View.GONE);
        } else {
            requirements.setText(getString(R.string.requirements,club.getMembershipRequirements()));
        }
        if (club.getMeetings() == null) {
            meetings.setVisibility(View.GONE);
        } else {
            meetings.setText(getString(R.string.meetings, club.getMeetings()));
        }
        if (club.getEvents() == null) {
            events.setVisibility(View.GONE);
        } else {
            events.setText(getString(R.string.events, club.getEvents()));
        }
    }
}
