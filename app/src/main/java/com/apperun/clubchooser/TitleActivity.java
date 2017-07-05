package com.apperun.clubchooser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TitleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.titleLayout);
        layout.setMinimumWidth(getWindowManager().getDefaultDisplay().getWidth());
        Button start = (Button) findViewById(R.id.start);
        Button results= (Button) findViewById(R.id.restart); //no longer restarts
        TextView name = (TextView) findViewById(R.id.name);
        final EditText nameEntry = (EditText) findViewById(R.id.nameEntry);

        final SharedPreferences storage;
        storage = getPreferences(0);
        boolean k = false;
        if (storage.contains("name")){
            nameEntry.setVisibility(View.INVISIBLE);
            String welcome = "Welcome back " + storage.getString("name", "!") + "!";
            name.setText(welcome);
        } else {
            name.setVisibility(View.INVISIBLE);
            k = true;
        }
        final boolean keepName = k;

        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                intent.putExtra("restart", true);
                if (keepName){
                    SharedPreferences.Editor editor = storage.edit();
                    editor.putString("name", nameEntry.getText().toString());
                    editor.commit();
                }
                startActivity(intent);
            }
        });
        //start over button
        results.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                intent.putExtra("restart", false);
                if (keepName){
                    SharedPreferences.Editor editor = storage.edit();
                    editor.putString("name", nameEntry.getText().toString());
                    editor.commit();
                }
                startActivity(intent);
            }
        });

    }


}
