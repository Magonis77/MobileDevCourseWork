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
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button save = (Button)findViewById(R.id.buttonsubmit);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInputs();
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemNext:
                getInputs();
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
            ((MainActivity)getActivity()).updateDOB(dob);

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
            RadioGroup HikeParking = (RadioGroup) findViewById(R.id.radioGroup3);
            RadioButton radioButtonInput =
                    (RadioButton) findViewById(HikeParking.getCheckedRadioButtonId());
            EditText HikeLenght = (EditText) findViewById(R.id.editTextNumberLenghtOfHike);
            Spinner Difficulty = (Spinner) findViewById(R.id.spinner);
            EditText HikeDesc = (EditText) findViewById(R.id.editTextTextPersonNameDescription);


            String strName = HikeName.getText().toString(),
                    strDifficulty = Difficulty.getSelectedItem().toString(),
                    strParking = radioButtonInput.getText().toString(),
                    strLenght = HikeLenght.getText().toString(),
                    strLocation = Hikelocation.getText().toString(),
                    strDate = HikeDate.getText().toString(),
                    strDesc = HikeDesc.getText().toString();
                if (strName.isEmpty() || strDifficulty.isEmpty()
                        || strParking.isEmpty() || strLenght.isEmpty()
                        || strLocation.isEmpty() || strDate.isEmpty()) {
                    displaymissinginfo();
                }
            if (!strName.isEmpty() && !strDifficulty.isEmpty()
                    && !strParking.isEmpty() && !strLenght.isEmpty()
                    && !strLocation.isEmpty() && !strDate.isEmpty()
                    && !strDesc.isEmpty()) {
                        displayConfAlert(strName, strLocation, strDate, strParking, strLenght, strDifficulty, strDesc);
            }
            if (!strName.isEmpty() && !strDifficulty.isEmpty()
                    && !strParking.isEmpty() && !strLenght.isEmpty()
                    && !strLocation.isEmpty() && !strDate.isEmpty() && strDesc.isEmpty()) {

                displayConfAlertnoDesc(strName, strLocation, strDate, strParking, strLenght, strDifficulty);
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
                                        String strLenght, String strDifficulty) {
        new AlertDialog.Builder(this).setTitle("Is the information correct?").setMessage(
                "Details entered:\n" + "Hike Name:  " + strName
                        + "\n" + "Location:  " + strLocation
                        + "\n" + "Hike Date:  " + strDate
                        + "\n" + "Parking Availability:  " + strParking
                        + "\n" + "Hike length:  " + strLenght
                        + "\n" + "Hike Difficulty:  " + strDifficulty).setNegativeButton("Back",
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
                                  String strDesc) {

        new AlertDialog.Builder(this).setTitle("Is the information correct?").setMessage(
                "Details entered:\n" + "Hike Name:  " + strName
                        + "\n" + "Location:  " + strLocation
                        + "\n" + "Hike Date:  " + strDate
                        + "\n" + "Parking Availability:  " + strParking
                        + "\n" + "Hike length:  " + strLenght
                        + "\n" + "Hike Difficulty:  " + strDifficulty
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
        RadioGroup HikeParking = (RadioGroup) findViewById(R.id.radioGroup3);
        RadioButton radioButtonInput =
                (RadioButton) findViewById(HikeParking.getCheckedRadioButtonId());
        EditText HikeLenght = (EditText) findViewById(R.id.editTextNumberLenghtOfHike);
        Spinner Difficulty = (Spinner) findViewById(R.id.spinner);
        EditText HikeDesc = (EditText) findViewById(R.id.editTextTextPersonNameDescription);


        String strName = HikeName.getText().toString(),
                strDifficulty = Difficulty.getSelectedItem().toString(),
                strParking = radioButtonInput.getText().toString(),
                strLenght = HikeLenght.getText().toString(),
                strLocation = Hikelocation.getText().toString(),
                strDate = HikeDate.getText().toString(),
                strDesc = HikeDesc.getText().toString();

        long hikeId = dbHelper.InsertDetails(strName, strLocation,
                 strDate,  strParking,
                 strLenght,  strDifficulty,
                 strDesc);

        Toast.makeText(this, "Hike has been created with id:" + hikeId, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, DetailsActivity.class);
        startActivity(intent);
    }
}