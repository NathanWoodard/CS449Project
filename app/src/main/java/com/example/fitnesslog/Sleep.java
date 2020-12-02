package com.example.fitnesslog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

public class Sleep extends AppCompatActivity {


    //File Stuff
    private static final String FILE_NAME = "sleep.txt";
    TextView mTextView;
    //END File Stuff

    Button setSleep;
    Button submitSleep;
    Button sleepLog;

    TextView currentSleepInput;

    EditText userInputSleep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
        mTextView = findViewById(R.id.CurrentSleepInput);

        setSleep = findViewById(R.id.SetSleep);
        submitSleep = findViewById(R.id.SubmitSleep);
        sleepLog = findViewById(R.id.SleepLog);

        currentSleepInput = findViewById(R.id.CurrentSleepInput);

        userInputSleep = (findViewById(R.id.UserInputSleep));

        setSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSleepInput.getVisibility() == View.VISIBLE) {
                    currentSleepInput.setVisibility(View.GONE);
                    userInputSleep.setVisibility(View.VISIBLE);
                    setSleep.setText("CONFIRM");
                }
                else {
                    try {
                        currentSleepInput.setText(String.valueOf(Integer.parseInt(userInputSleep.getText().toString())));
                    } catch (NumberFormatException e) {
                        //does nothing if the user does not input a number at all cause for some reason java doesnt interpret an empty string as 0 when you parseint it
                    }
                    currentSleepInput.setVisibility(View.VISIBLE);
                    userInputSleep.getText().clear();
                    userInputSleep.setVisibility(View.GONE);
                    setSleep.setText("SET Sleep");
                }
            }
        });
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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


    private void saveData() {
        SharedPreferences sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("currentWeightInput", Integer.parseInt(currentSleepInput.getText().toString()));
        editor.commit();
    }

    private void loadData() {
        SharedPreferences sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        currentSleepInput.setText(String.valueOf(sp.getInt("currentWeightInput", 0)));
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

    public void openSleepLog(View v){
        Intent intent = new Intent(this, SleepLog.class);
        startActivity(intent);
    }

}
