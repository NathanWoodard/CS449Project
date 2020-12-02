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

public class Weight extends AppCompatActivity {

    //File Stuff
    private static final String FILE_NAME = "weight.txt";
    TextView mTextView;
    //END File Stuff

    Button setWeight;
    Button submitWeight;
    Button weightLog;

    TextView currentWeightInput;

    EditText userEditWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        mTextView = findViewById(R.id.CurrentWeightInput);

        setWeight = findViewById(R.id.SetWeight);
        submitWeight = findViewById(R.id.SubmitWeight);
        weightLog = findViewById(R.id.WeightLog);

        currentWeightInput = findViewById(R.id.CurrentWeightInput);

        userEditWeight = (findViewById(R.id.UserEditWeight));

        setWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentWeightInput.getVisibility() == View.VISIBLE) {
                    currentWeightInput.setVisibility(View.GONE);
                    userEditWeight.setVisibility(View.VISIBLE);
                    setWeight.setText("CONFIRM");
                }
                else {
                    try {
                        currentWeightInput.setText(String.valueOf(Integer.parseInt(userEditWeight.getText().toString())));
                    } catch (NumberFormatException e) {
                        //does nothing if the user does not input a number at all cause for some reason java doesnt interpret an empty string as 0 when you parseint it
                    }
                    currentWeightInput.setVisibility(View.VISIBLE);
                    userEditWeight.getText().clear();
                    userEditWeight.setVisibility(View.GONE);
                    setWeight.setText("SET WEIGHT");
                }
            }
        });
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveData() {
        SharedPreferences sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("currentWeightInput", Integer.parseInt(currentWeightInput.getText().toString()));
        editor.commit();
    }

    private void loadData() {
        SharedPreferences sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        currentWeightInput.setText(String.valueOf(sp.getInt("currentWeightInput", 0)));
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

    public void openWeightLog(View v){
        Intent intent = new Intent(this, WeightLog.class);
        startActivity(intent);
    }

}
