package com.example.fitnesslog;

import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Calorie extends AppCompatActivity {
    Button maxCalorieButton;
    TextView maxCalorieText;
    EditText maxCalorieEdit;
    Button addCalorieButton;
    TextView currentCalorieText;
    EditText addCalorieEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie);

        addCalorieButton = findViewById(R.id.addcalbutton);
        currentCalorieText = findViewById(R.id.textView4);
        addCalorieEdit = findViewById(R.id.editText);
        addCalorieButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(currentCalorieText.getVisibility()==View.VISIBLE){
                    currentCalorieText.setVisibility(View.GONE);
                    addCalorieEdit.setVisibility(View.VISIBLE);
                    addCalorieButton.setText("CONFIRM");
                }
                else{
                    currentCalorieText.setText(String.valueOf(Integer.parseInt(currentCalorieText.getText().toString()) + Integer.parseInt(addCalorieEdit.getText().toString())));
                    currentCalorieText.setVisibility(View.VISIBLE);
                    addCalorieEdit.getText().clear();
                    addCalorieEdit.setVisibility(View.GONE);
                    addCalorieButton.setText("ADD CALORIES");
                }
            }
        });
        maxCalorieEdit = findViewById(R.id.editText2);
        maxCalorieText = findViewById(R.id.textView3);
        maxCalorieButton = findViewById(R.id.maxcalbutton);
        maxCalorieButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(maxCalorieText.getVisibility()==View.VISIBLE){
                    maxCalorieText.setVisibility(View.GONE);
                    maxCalorieEdit.setVisibility(View.VISIBLE);
                    maxCalorieButton.setText("CONFIRM");

                }
                else{
                    maxCalorieText.setText(maxCalorieEdit.getText().toString());
                    maxCalorieText.setVisibility(View.VISIBLE);
                    maxCalorieEdit.setVisibility(View.GONE);
                    maxCalorieButton.setText("CHANGE MAXIMUM");
                }
            }
        });
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
