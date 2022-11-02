package com.example.comp1424_coursework;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;

public class HikeInputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_input);
        Button save = (Button)findViewById(R.id.buttonsubmit);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInputs();
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
    }

    private void Hikeinput() {
        Intent intent = new Intent(this, HikeInputActivity.class);
        startActivity(intent);
    }
    private void HikeList() {
        Intent intent = new Intent(this, HikeListActivity.class);
        startActivity(intent);
    }
    private void HikeObservations() {
        Intent intent = new Intent(this, HikeObservationAddActivity.class);
        startActivity(intent);
    }
    private void HikeSearch() {
        Intent intent = new Intent(this, HikeSearchActivity.class);
        startActivity(intent);
    }
    private void MainMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemCreateHike:
                Hikeinput();
                return true;
            case R.id.itemHikeList:
                HikeList();
                return true;
            case R.id.itemObservationsAdd:
                HikeObservations();
                return true;
            case R.id.itemSearch:
                HikeSearch();
                return true;
            case R.id.itemMenu:
                MainMenu();
                return true;
            case R.id.itemExit:
                Toast.makeText(getApplicationContext(),
                        "You asked to exit, but why not start another app?",
                        Toast.LENGTH_LONG).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            LocalDate d = LocalDate.now();
            int year = d.getYear();
            int month = d.getMonthValue();
            int day = d.getDayOfMonth();

            return new DatePickerDialog(getActivity(), this, year, --month, day);
        }

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            LocalDate dob = LocalDate.of(year, ++month, day);
            ((HikeInputActivity)getActivity()).updateDOB(dob);

        }

    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public void updateDOB(LocalDate dob) {
        TextView dobText = (TextView)findViewById(R.id.textViewDateSelector);
        dobText.setText(dob.toString());
    }
    private void getInputs() {
        try {
            EditText HikeName = (EditText) findViewById(R.id.editTextTextPersonNameHike);

            EditText Hikelocation = (EditText) findViewById(R.id.editTextTextPersonNameLocation);
            TextView HikeDate = (TextView) findViewById(R.id.textViewDateSelector);
            RadioGroup HikeParking = (RadioGroup) findViewById(R.id.radioGroup);
            RadioButton radioButtonInput =
                    (RadioButton) findViewById(HikeParking.getCheckedRadioButtonId());
            EditText HikeLenght = (EditText) findViewById(R.id.editTextNumberLenghtOfHike);
            Spinner Difficulty = (Spinner) findViewById(R.id.spinner);
            EditText Weather = (EditText) findViewById(R.id.editTextTextPersonWeather);
            EditText HeartRate = (EditText) findViewById(R.id.editTextNumberHeartRate);
            EditText HikeDesc = (EditText) findViewById(R.id.editTextTextPersonNameDescription);


            String strName = HikeName.getText().toString(),
                    strDifficulty = Difficulty.getSelectedItem().toString(),
                    strParking = radioButtonInput.getText().toString(),
                    strLenght = HikeLenght.getText().toString(),
                    strLocation = Hikelocation.getText().toString(),
                    strDate = HikeDate.getText().toString(),
                    strWeather = Weather.getText().toString(),
                    strHeartRate = HeartRate.getText().toString(),
                    strDesc = HikeDesc.getText().toString();
            if (strName.isEmpty() || strDifficulty.isEmpty()
                    || strParking.isEmpty() || strLenght.isEmpty()
                    || strLocation.isEmpty() || strDate.isEmpty() || strWeather.isEmpty() || strHeartRate.isEmpty()) {
                displaymissinginfo();
            }
            if (!strName.isEmpty() && !strDifficulty.isEmpty()
                    && !strParking.isEmpty() && !strLenght.isEmpty()
                    && !strLocation.isEmpty() && !strDate.isEmpty() && !strWeather.isEmpty() && !strHeartRate.isEmpty()
                    && !strDesc.isEmpty()) {
                displayConfAlert(strName, strLocation, strDate, strParking, strLenght, strDifficulty,strWeather,strHeartRate, strDesc);
            }
            if (!strName.isEmpty() && !strDifficulty.isEmpty()
                    && !strParking.isEmpty() && !strLenght.isEmpty()
                    && !strLocation.isEmpty() && !strDate.isEmpty() && !strWeather.isEmpty() && !strHeartRate.isEmpty() && strDesc.isEmpty()) {

                displayConfAlertnoDesc(strName, strLocation, strDate, strParking, strLenght,strWeather,strHeartRate, strDifficulty);
            }


        }catch(NullPointerException n){
            displayNothingAlert();
        }
    }

    private void displaymissinginfo() {
        new AlertDialog.Builder(this).setTitle("All required fields not filled!").setMessage(
                "Please enter details in all required fields before submiting!").setNeutralButton("Back",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) { }
                }).show();
    }

    private void displayNothingAlert() {
        new AlertDialog.Builder(this).setTitle("No information entered!").setMessage(
                "Please enter details before submiting!").setNeutralButton("Back",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) { }
                }).show();
    }

    private void displayConfAlertnoDesc(String strName, String strLocation,
                                        String strDate, String strParking,
                                        String strLenght,String strWeather,String strHeartRate, String strDifficulty) {
        new AlertDialog.Builder(this).setTitle("Is the information correct?").setMessage(
                "Details entered:\n" + "Hike Name:  " + strName
                        + "\n" + "Location:  " + strLocation
                        + "\n" + "Hike Date:  " + strDate
                        + "\n" + "Parking Availability:  " + strParking
                        + "\n" + "Hike length:  " + strLenght
                        + "\n" + "Hike Difficulty:  "+ strDifficulty
                        + "\n" + "Hike Weather:  " + strWeather
                        + "\n" + "Hike Heart Rate:  " + strHeartRate
                        ).setNegativeButton("Back",
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) { }
                }).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                saveDetails();
            }
        }).show();
    }

    private void displayConfAlert(String strName, String strLocation,
                                  String strDate, String strParking,
                                  String strLenght, String strDifficulty,
                                  String strWeather, String strHeartRate,
                                  String strDesc) {

        new AlertDialog.Builder(this).setTitle("Is the information correct?").setMessage(
                "Details entered:\n" + "Hike Name:  " + strName
                        + "\n" + "Location:  " + strLocation
                        + "\n" + "Hike Date:  " + strDate
                        + "\n" + "Parking Availability:  " + strParking
                        + "\n" + "Hike length:  " + strLenght
                        + "\n" + "Hike Difficulty:  " + strDifficulty
                        + "\n" + "Hike Weather:  " + strWeather
                        + "\n" + "Hike Heart Rate:  " + strHeartRate
                        + "\n" + "Hike Description:  " + strDesc).setNegativeButton("Back",
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) { }
                }).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                saveDetails();
            }
        }).show();
    }

    private void saveDetails() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        EditText HikeName = (EditText) findViewById(R.id.editTextTextPersonNameHike);

        EditText Hikelocation = (EditText) findViewById(R.id.editTextTextPersonNameLocation);
        TextView HikeDate = (TextView) findViewById(R.id.textViewDateSelector);
        RadioGroup HikeParking = (RadioGroup) findViewById(R.id.radioGroup);
        RadioButton radioButtonInput =
                (RadioButton) findViewById(HikeParking.getCheckedRadioButtonId());
        EditText HikeLenght = (EditText) findViewById(R.id.editTextNumberLenghtOfHike);
        Spinner Difficulty = (Spinner) findViewById(R.id.spinner);
        EditText Weather = (EditText) findViewById(R.id.editTextTextPersonWeather);
        EditText HeartRate = (EditText) findViewById(R.id.editTextNumberHeartRate);
        EditText HikeDesc = (EditText) findViewById(R.id.editTextTextPersonNameDescription);


        String strName = HikeName.getText().toString(),
                strDifficulty = Difficulty.getSelectedItem().toString(),
                strParking = radioButtonInput.getText().toString(),
                strLenght = HikeLenght.getText().toString(),
                strLocation = Hikelocation.getText().toString(),
                strDate = HikeDate.getText().toString(),
                strWeather = Weather.getText().toString(),
                strHeartRate = HeartRate.getText().toString(),
                strDesc = HikeDesc.getText().toString();

        long hikeId = dbHelper.InsertDetails(strName, strLocation,
                strDate,  strParking,
                strLenght,  strDifficulty,
                strWeather, strHeartRate,
                strDesc);

        Toast.makeText(this, "Hike has been created with id:" + hikeId, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, HikeListActivity.class);
        startActivity(intent);
    }
}