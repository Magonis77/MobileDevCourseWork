package com.example.comp1424_coursework;

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

public class HikeEditActivity extends AppCompatActivity {
    int id;
    TextView tvname;
    TextView tvlocation;
    TextView tvDate;
    TextView tvparking;
    TextView tvLenght;
    TextView tvdescription;
    RadioButton tvparkingyes;
    RadioButton tvparkingno;
    Spinner tvDifficulty;
    TextView tvid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar);

        Button edit = (Button)findViewById(R.id.buttonedit);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInputs();
            }
        });



        id = getIntent().getExtras().getInt("id");
        int id2 = getIntent().getExtras().getInt("id2");
        String name = getIntent().getExtras().getString("name");
        String location = getIntent().getExtras().getString("location");
        String date = getIntent().getExtras().getString("date");
        String parking = getIntent().getExtras().getString("parking");
        String lenght = getIntent().getExtras().getString("lenght");
        String difficulty = getIntent().getExtras().getString("difficulty");
        String description = getIntent().getExtras().getString("description");
        tvname = findViewById(R.id.editTextTextPersonNameHike4);
        tvlocation = findViewById(R.id.editTextTextPersonNameLocation3);
        tvDate = findViewById(R.id.textViewDateSelector3);
        tvparkingyes = findViewById(R.id.radioButton5);
        tvparkingno = findViewById(R.id.radioButton4);
        tvLenght = findViewById(R.id.editTextNumberLenghtOfHike3);
        tvDifficulty = findViewById(R.id.spinner3);
        tvdescription = findViewById(R.id.editTextTextPersonNameDescription3);
        tvid = findViewById(R.id.textViewid);
        tvid.setText(String.valueOf(id2));
        tvname.setText(name);
        tvlocation.setText(location);
        tvDate.setText(date);
        try {
            switch (parking) {
                case "Yes":
                    tvparkingyes.setChecked(true);
                    break;
                case "No":
                    tvparkingno.setChecked(true);
                    break;
            }
        } catch(NullPointerException e) {

        }
        tvLenght.setText(lenght);
        try {
            if (difficulty.equals("Easy")) {
                tvDifficulty.setSelection(0);
            } else if (difficulty.equals("Medium")) {
                tvDifficulty.setSelection(1);
            } else if (difficulty.equals("Hard")) {
                tvDifficulty.setSelection(2);
            }
        } catch(NullPointerException e) {

        }
        tvdescription.setText(description);
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
        DialogFragment newFragment = new HikeInputActivity.DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public void updateDOB(LocalDate dob) {
        TextView dobText = (TextView)findViewById(R.id.textViewDateSelector3);
        dobText.setText(dob.toString());
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

    private void getInputs() {
        try {
            EditText HikeName = (EditText) findViewById(R.id.editTextTextPersonNameHike4);

            EditText Hikelocation = (EditText) findViewById(R.id.editTextTextPersonNameLocation3);
            TextView HikeDate = (TextView) findViewById(R.id.textViewDateSelector3);
            RadioGroup HikeParking = (RadioGroup) findViewById(R.id.radioGroup2);
            RadioButton radioButtonInput =
                    (RadioButton) findViewById(HikeParking.getCheckedRadioButtonId());
            EditText HikeLenght = (EditText) findViewById(R.id.editTextNumberLenghtOfHike3);
            Spinner Difficulty = (Spinner) findViewById(R.id.spinner3);
            EditText HikeDesc = (EditText) findViewById(R.id.editTextTextPersonNameDescription3);


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

        TextView id = (TextView) findViewById(R.id.textViewid);
        EditText HikeName = (EditText) findViewById(R.id.editTextTextPersonNameHike4);

        EditText Hikelocation = (EditText) findViewById(R.id.editTextTextPersonNameLocation3);
        TextView HikeDate = (TextView) findViewById(R.id.textViewDateSelector3);
        RadioGroup HikeParking = (RadioGroup) findViewById(R.id.radioGroup2);
        RadioButton radioButtonInput =
                (RadioButton) findViewById(HikeParking.getCheckedRadioButtonId());
        EditText HikeLenght = (EditText) findViewById(R.id.editTextNumberLenghtOfHike3);
        Spinner Difficulty = (Spinner) findViewById(R.id.spinner3);
        EditText HikeDesc = (EditText) findViewById(R.id.editTextTextPersonNameDescription3);


        String strid = id.getText().toString(),
                strName = HikeName.getText().toString(),
                strDifficulty = Difficulty.getSelectedItem().toString(),
                strParking = radioButtonInput.getText().toString(),
                strLenght = HikeLenght.getText().toString(),
                strLocation = Hikelocation.getText().toString(),
                strDate = HikeDate.getText().toString(),
                strDesc = HikeDesc.getText().toString();

        long hikeId = dbHelper.AmendDetails(strid,strName, strLocation,
                strDate,  strParking,
                strLenght,  strDifficulty,
                strDesc);

        Toast.makeText(this, "Hike has been amended with id:" + hikeId, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, HikeListActivity.class);
        startActivity(intent);
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
}