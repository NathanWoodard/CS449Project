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


public class Workout extends AppCompatActivity {
    private static String FILE_NAME = "workout.txt";

    TextView workoutText;
    EditText stringEdit;
    EditText numberEdit;
    Button addButton;
    Button submitButton;
    Button logButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        workoutText = findViewById(R.id.workouttext);
        stringEdit = findViewById(R.id.workouttype);
        numberEdit = findViewById(R.id.workouttime);
        addButton = findViewById(R.id.addworkoutbutton);
        submitButton = findViewById(R.id.submitworkout);
        logButton = findViewById(R.id.workoutlogbutton);

        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (workoutText.getVisibility() == View.VISIBLE) {
                    workoutText.setVisibility(View.GONE);
                    stringEdit.setVisibility(View.VISIBLE);
                    addButton.setText("CONFIRM");
                }
                else if(stringEdit.getVisibility() == View.VISIBLE){
                    stringEdit.setVisibility(View.GONE);
                    numberEdit.setVisibility(View.VISIBLE);
                }
                else {
                    try {
                        workoutText.setText(String.valueOf(Integer.parseInt(workoutText.getText().toString()) + Integer.parseInt(numberEdit.getText().toString())));

                    } catch (NumberFormatException e) {
                        //does nothing if the user does not input a number at all cause for some reason java doesnt interpret an empty string as 0 when you parseint it
                    }
                    workoutText.setVisibility(View.VISIBLE);
                    numberEdit.setVisibility(View.GONE);
                    addButton.setText("ADD WORKOUT");
                    save(numberEdit.getText().toString(), stringEdit.getText().toString());
                    numberEdit.getText().clear();
                    stringEdit.getText().clear();
                }
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                save(workoutText.getText().toString(), "Total");
            }
        });
    }
    public void save(String numberText, String stringText) {
        String newline = "\n";
        String space = " ";
        FileOutputStream fos = null;

        Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME,
                Toast.LENGTH_LONG).show();

        try {
            Calendar c = Calendar.getInstance();
            fos = openFileOutput(FILE_NAME, MODE_APPEND);
            fos.write(stringText.getBytes());
            fos.write(space.getBytes());
            fos.write(numberText.getBytes());
            fos.write(newline.getBytes());
            String dayLongName = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
            fos.write(dayLongName.getBytes());
            fos.write(newline.getBytes());

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
    private void saveData() {
        SharedPreferences sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("currentWeightInput", Integer.parseInt(workoutText.getText().toString()));
        editor.commit();
    }

    private void loadData() {
        SharedPreferences sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        workoutText.setText(String.valueOf(sp.getInt("currentWeightInput", 0)));
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

    public void openWorkoutLog(View v){
        Intent intent = new Intent(this, WorkoutLog.class);
        startActivity(intent);
    }
}
