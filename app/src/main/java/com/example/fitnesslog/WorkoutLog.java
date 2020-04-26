package com.example.fitnesslog;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class WorkoutLog extends AppCompatActivity {
    TextView[] test = new TextView[200];
    private static final String FILE_NAME = "workout.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_log);
        Toolbar toolbar = (Toolbar) findViewById(R.id.workouttoolbar);
        setSupportActionBar(toolbar);
        ConstraintLayout scroll = findViewById(R.id.workoutlayout);
        ConstraintSet c = new ConstraintSet();

        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String day, activity;
            int i = 0;
            while ((activity = br.readLine()) != null) {
                day = br.readLine();
                TextView day_view = new TextView(this);
                day_view.setText(day);
                day_view.setId(View.generateViewId());
                ConstraintLayout.LayoutParams day_params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                day_view.setLayoutParams(day_params);
                day_view.setTextSize(30);
                scroll.addView(day_view);
                test[i] = day_view;
                TextView workout_view = new TextView(this);
                workout_view.setText(activity);
                workout_view.setId(View.generateViewId());
                ConstraintLayout.LayoutParams workout_params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                workout_view.setLayoutParams(workout_params);
                workout_view.setTextSize(30);
                scroll.addView(workout_view);
                test[i + 1] = workout_view;
                i += 2;
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        c.clone(scroll);
        c.connect(test[0].getId(), ConstraintSet.BOTTOM, R.id.workoutlayout, ConstraintSet.BOTTOM);
        c.connect(test[0].getId(), ConstraintSet.LEFT, R.id.workoutlayout, ConstraintSet.LEFT);
        c.connect(test[1].getId(), ConstraintSet.BOTTOM, R.id.workoutlayout, ConstraintSet.BOTTOM);
        c.connect(test[1].getId(), ConstraintSet.RIGHT, R.id.workoutlayout, ConstraintSet.RIGHT);
        for (int i = 2; i < 100; i += 2) {
            if (test[i] == null) {
                break;
            }
            c.connect(test[i].getId(), ConstraintSet.BOTTOM, test[i - 2].getId(), ConstraintSet.TOP);
            c.connect(test[i].getId(), ConstraintSet.START, R.id.workoutlayout, ConstraintSet.START);
            c.connect(test[i + 1].getId(), ConstraintSet.BOTTOM, test[i - 2].getId(), ConstraintSet.TOP);
            c.connect(test[i + 1].getId(), ConstraintSet.END, R.id.workoutlayout, ConstraintSet.END);
        }
        c.applyTo(scroll);

    }
}
