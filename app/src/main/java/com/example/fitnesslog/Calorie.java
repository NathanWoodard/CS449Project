package com.example.fitnesslog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.view.MenuItem;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

public class Calorie extends AppCompatActivity {
    private static final String FILE_NAME = "calorie.txt";

    TextView mTextView;

    Button maxCalorieButton;
    TextView maxCalorieText;
    EditText maxCalorieEdit;
    Button addCalorieButton;
    TextView currentCalorieText;
    EditText addCalorieEdit;
    Button resetButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie);

        addCalorieButton = findViewById(R.id.addcalbutton);
        currentCalorieText = findViewById(R.id.textView4);
        addCalorieEdit = findViewById(R.id.editText);
        addCalorieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentCalorieText.getVisibility() == View.VISIBLE) {
                    currentCalorieText.setVisibility(View.GONE);
                    addCalorieEdit.setVisibility(View.VISIBLE);
                    addCalorieButton.setText("CONFIRM");
                } else {
                    try {
                        currentCalorieText.setText(String.valueOf(Integer.parseInt(currentCalorieText.getText().toString()) + Integer.parseInt(addCalorieEdit.getText().toString())));
                    } catch (NumberFormatException e) {
                        //does nothing if the user does not input a number at all cause for some reason java doesnt interpret an empty string as 0 when you parseint it
                    }
                    currentCalorieText.setVisibility(View.VISIBLE);
                    addCalorieEdit.getText().clear();
                    addCalorieEdit.setVisibility(View.GONE);
                    addCalorieButton.setText("ADD CALORIES");
                }
            }
        });

        resetButton = findViewById(R.id.resetbutton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCalorieText.setText("0");
            }
        });
        maxCalorieEdit = findViewById(R.id.editText2);
        maxCalorieText = findViewById(R.id.textView3);
        maxCalorieButton = findViewById(R.id.maxcalbutton);
        maxCalorieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (maxCalorieText.getVisibility() == View.VISIBLE) {
                    maxCalorieText.setVisibility(View.GONE);
                    maxCalorieEdit.setVisibility(View.VISIBLE);
                    maxCalorieButton.setText("CONFIRM");

                } else {
                    maxCalorieText.setText(maxCalorieEdit.getText().toString());
                    maxCalorieText.setVisibility(View.VISIBLE);
                    maxCalorieEdit.setVisibility(View.GONE);
                    maxCalorieButton.setText("CHANGE MAXIMUM");
                }
            }
        });
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTextView = findViewById(R.id.textView4);
    }

    private void saveData() {
        SharedPreferences sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("currentCalorie", Integer.parseInt(currentCalorieText.getText().toString()));
        editor.putInt("maxCalorie", Integer.parseInt(maxCalorieText.getText().toString()));
        editor.commit();
    }

    private void loadData() {
        SharedPreferences sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        currentCalorieText.setText(String.valueOf(sp.getInt("currentCalorie", 0)));
        maxCalorieText.setText(String.valueOf(sp.getInt("maxCalorie", 0)));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void save(View v) {
        String text = mTextView.getText().toString();
        FileOutputStream fos = null;

        mTextView.setText("0");
        Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME,
                Toast.LENGTH_LONG).show();

        try {
            Calendar c = Calendar.getInstance();
            fos = openFileOutput(FILE_NAME, MODE_APPEND);
            fos.write(text.getBytes());
            text = "\n";
            fos.write(text.getBytes());
            String dayLongName = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
            fos.write(dayLongName.getBytes());
            text = "\n";
            fos.write(text.getBytes());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void openCalorieLog(View v) {
        Intent intent = new Intent(this, CalorieLog.class);
        startActivity(intent);
    }
    @Override
    protected void onPause(){
        super.onPause();
        saveData();
    }

    @Override
    protected void onResume(){
        super.onResume();
        loadData();
    }
}
