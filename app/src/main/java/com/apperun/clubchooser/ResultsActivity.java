package com.apperun.clubchooser;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class ResultsActivity extends AppCompatActivity {

    ArrayList<Club> clubs;
    int totalAcademics;
    int totalArtistic;
    int totalSports;
    int totalCommunity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_acitivty);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Results");
        Bundle extras = getIntent().getExtras();
        totalAcademics = extras.getInt("totalAcademics", 0);
        totalArtistic = extras.getInt("totalArtistic", 0);
        totalCommunity = extras.getInt("totalCommunity", 0);
        totalSports = extras.getInt("totalSports", 0);

        Button menu = (Button) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        clubs = makeClubs();
        if ((extras.getBoolean("listOnly", false))){
            for (Club c : clubs){ //set scores to 0?
                c.setClubScore(0);
            }
        } else {
            for (Club c : clubs){
                c.computeClubScore(totalAcademics,totalArtistic,totalSports,totalCommunity);
            }
        }
        Collections.sort(clubs);

        TableLayout table = (TableLayout)findViewById(R.id.table);
        for (int i = 0; i < clubs.size(); i++){
            TableRow row = new TableRow(getApplicationContext());
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            Button clubClicker = new Button(getApplicationContext());
            String text = "" + (i+1) + ")" + " " + clubs.get(i).getClubName();
            clubClicker.setText(text);
            clubClicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button) v;
                    Intent intent = new Intent(getApplicationContext(), DescriptionActivity.class);
                    String clubName = b.getText().toString().substring(b.getText().toString().indexOf(')') + 2);
                    intent.putExtra("clubname", clubName);
                    startActivity(intent);
                }
            });
            clubClicker.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT));
            clubClicker.setGravity(Gravity.CENTER_HORIZONTAL);
            clubClicker.getBackground().setColorFilter(getResources().getColor(R.color.gold), PorterDuff.Mode.MULTIPLY); //make yellow
            clubClicker.setTextColor(Color.BLUE); //make blue
            row.setGravity(Gravity.CENTER_HORIZONTAL);
            row.addView(clubClicker);
            table.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
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
                            break; //go to next line
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
}
