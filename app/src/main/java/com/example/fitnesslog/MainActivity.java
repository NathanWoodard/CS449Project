package com.example.fitnesslog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    Button calorieButton;
    Button workoutButton;
    Button sleepButton;
    Button weightButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calorieButton = findViewById(R.id.calorie);
        workoutButton = findViewById(R.id.workout);
        workoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openWorkout();
            }
        });
        sleepButton = findViewById(R.id.sleep);
        workoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openSleep();
            }
        });
        weightButton = findViewById(R.id.weight);
        workoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openWeight();
            }
        });
    }
    public void btnCal(View view){
        startActivity(new Intent(this, Calorie.class));
    }
    public void openWorkout(){
        Intent intent = new Intent(this, Workout.class);
        startActivity(intent);
    }
    public void openSleep(){
        Intent intent = new Intent(this, Sleep.class);
        startActivity(intent);
    }
    public void openWeight(){
        Intent intent = new Intent(this, Weight.class);
        startActivity(intent);
    }
}
