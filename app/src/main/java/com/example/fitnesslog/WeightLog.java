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

public class WeightLog extends AppCompatActivity {
    TextView[] test = new TextView[100];
    TextView weighttext;
    private static final String FILE_NAME = "weight.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_log);
        Toolbar toolbar = (Toolbar) findViewById(R.id.weighttoolbar);
        setSupportActionBar(toolbar);
        ConstraintLayout scroll = findViewById(R.id.weightlayout);
        ConstraintSet c = new ConstraintSet();

        weighttext = findViewById(R.id.weightchange);

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
        c.connect(test[0].getId(), ConstraintSet.BOTTOM, R.id.weightlayout, ConstraintSet.BOTTOM);
        c.connect(test[0].getId(), ConstraintSet.LEFT, R.id.weightlayout, ConstraintSet.LEFT);
        c.connect(test[1].getId(), ConstraintSet.BOTTOM, R.id.weightlayout, ConstraintSet.BOTTOM);
        c.connect(test[1].getId(), ConstraintSet.RIGHT, R.id.weightlayout, ConstraintSet.RIGHT);
        int i;
        for(i = 2;i<100;i+=2){
            if(test[i]==null){
                break;
            }
            c.connect(test[i].getId(), ConstraintSet.BOTTOM, test[i-2].getId(), ConstraintSet.TOP);
            c.connect(test[i].getId(), ConstraintSet.START, R.id.weightlayout, ConstraintSet.START);
            c.connect(test[i+1].getId(), ConstraintSet.BOTTOM, test[i-2].getId(), ConstraintSet.TOP);
            c.connect(test[i+1].getId(), ConstraintSet.END, R.id.weightlayout, ConstraintSet.END);
        }
        i--;
        int weightchange = Integer.valueOf(test[i].getText().toString()) - Integer.valueOf(test[i-2].getText().toString());
        weighttext.setText("Most Recent Weight Change: " + weightchange);
        c.applyTo(scroll);

    }



}
