package com.apperun.clubchooser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TitleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        Button start = (Button) findViewById(R.id.start);
        Button results= (Button) findViewById(R.id.restart); //no longer restarts
        EditText nameEntry = (EditText) findViewById(R.id.nameEntry);
        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                intent.putExtra("restart", true);
                startActivity(intent);
            }
        });
        //start over button
        results.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                intent.putExtra("restart", false);
                startActivity(intent);
            }
        });

    }


}
