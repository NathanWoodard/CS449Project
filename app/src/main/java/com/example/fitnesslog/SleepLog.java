package com.example.fitnesslog;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SleepLog extends AppCompatActivity {
    TextView[] test = new TextView[100];
    private static final String FILE_NAME = "sleep.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_log);
        Toolbar toolbar = (Toolbar) findViewById(R.id.sleeptoolbar);
        setSupportActionBar(toolbar);
        ConstraintLayout scroll = findViewById(R.id.sleeplayout);
        ConstraintSet c = new ConstraintSet();

        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            // StringBuilder sb = new StringBuilder();
            String day, weight;
            int i = 0;
            while ((weight = br.readLine()) != null) {
                day = br.readLine();
                if(i !=0 && test[i - 2].getText().toString().equals(day)){
                    test[i-1].setText(String.valueOf(Integer.parseInt(test[i-1].getText().toString()) + Integer.parseInt(weight)));
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
                    TextView weight_view = new TextView(this);
                    weight_view.setText(weight);
                    weight_view.setId(View.generateViewId());
                    ConstraintLayout.LayoutParams weight_params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                    weight_view.setLayoutParams(weight_params);
                    weight_view.setTextSize(30);
                    scroll.addView(weight_view);
                    test[i+1] = weight_view;
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
        c.connect(test[0].getId(), ConstraintSet.BOTTOM, R.id.sleeplayout, ConstraintSet.BOTTOM);
        c.connect(test[0].getId(), ConstraintSet.LEFT, R.id.sleeplayout, ConstraintSet.LEFT);
        c.connect(test[1].getId(), ConstraintSet.BOTTOM, R.id.sleeplayout, ConstraintSet.BOTTOM);
        c.connect(test[1].getId(), ConstraintSet.RIGHT, R.id.sleeplayout, ConstraintSet.RIGHT);
        for(int i = 2;i<100;i+=2){
            if(test[i]==null){
                break;
            }
            c.connect(test[i].getId(), ConstraintSet.BOTTOM, test[i-2].getId(), ConstraintSet.TOP);
            c.connect(test[i].getId(), ConstraintSet.START, R.id.sleeplayout, ConstraintSet.START);
            c.connect(test[i+1].getId(), ConstraintSet.BOTTOM, test[i-2].getId(), ConstraintSet.TOP);
            c.connect(test[i+1].getId(), ConstraintSet.END, R.id.sleeplayout, ConstraintSet.END);
        }
        c.applyTo(scroll);

    }



}
