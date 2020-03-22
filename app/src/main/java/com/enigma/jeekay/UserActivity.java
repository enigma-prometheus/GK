package com.enigma.jeekay;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {

    private TextView textViewName;

    @Override
    protected void onCreate(Bundle si){
        super.onCreate(si);
        setContentView(R.layout.activity_user);

        textViewName = (TextView)findViewById(R.id.text1);
        String nameFromIntent  = getIntent().getStringExtra("Email");
        textViewName.setText("Welcome " + nameFromIntent);
    }
}
