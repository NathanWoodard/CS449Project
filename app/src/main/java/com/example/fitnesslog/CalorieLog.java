package com.example.fitnesslog;

import android.os.Bundle;


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

public class CalorieLog extends AppCompatActivity {
    TextView[] test = new TextView[100];
    private static final String FILE_NAME = "calorie.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_log);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ConstraintLayout scroll = findViewById(R.id.info);
        ConstraintSet c = new ConstraintSet();

        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String day, calories;
            int i = 0;
            while ((calories = br.readLine()) != null) {
                day = br.readLine();
                if(i !=0 && test[i - 2].getText().toString().equals(day)){
                    test[i-1].setText(String.valueOf(Integer.parseInt(test[i-1].getText().toString()) + Integer.parseInt(calories)));
                }
                else{
                    TextView day_view = new TextView(this);
                    day_view.setText(day);
                    day_view.setId(View.generateViewId());
                    ConstraintLayout.LayoutParams day_params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                    day_view.setLayoutParams(day_params);
                    day_view.setTextSize(30);
                    scroll.addView(day_view);
                    test[i] = day_view;
                    TextView cal_view = new TextView(this);
                    cal_view.setText(calories);
                    cal_view.setId(View.generateViewId());
                    ConstraintLayout.LayoutParams cal_params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                    cal_view.setLayoutParams(cal_params);
                    cal_view.setTextSize(30);
                    scroll.addView(cal_view);
                    test[i+1] = cal_view;
                    i+=2;
                }


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
        c.connect(test[0].getId(), ConstraintSet.BOTTOM, R.id.info, ConstraintSet.BOTTOM);
        c.connect(test[0].getId(), ConstraintSet.LEFT, R.id.info, ConstraintSet.LEFT);
        c.connect(test[1].getId(), ConstraintSet.BOTTOM, R.id.info, ConstraintSet.BOTTOM);
        c.connect(test[1].getId(), ConstraintSet.RIGHT, R.id.info, ConstraintSet.RIGHT);
        for(int i = 2;i<100;i+=2){
            if(test[i]==null){
                break;
            }
            c.connect(test[i].getId(), ConstraintSet.BOTTOM, test[i-2].getId(), ConstraintSet.TOP);
            c.connect(test[i].getId(), ConstraintSet.START, R.id.info, ConstraintSet.START);
            c.connect(test[i+1].getId(), ConstraintSet.BOTTOM, test[i-2].getId(), ConstraintSet.TOP);
            c.connect(test[i+1].getId(), ConstraintSet.END, R.id.info, ConstraintSet.END);
        }
        c.applyTo(scroll);

    }



}
