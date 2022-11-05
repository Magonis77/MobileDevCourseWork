package com.example.comp1424_coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class HikeObservationAddActivity extends AppCompatActivity {

    TextView tvid;
    TextView tvtime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_observation_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar7);
        setSupportActionBar(toolbar);
        int id2 = getIntent().getExtras().getInt("id2");
        tvid = findViewById(R.id.textViewhikeid);
        tvid.setText(String.valueOf(id2));
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String times = dateTimeFormatter.format(time);
        tvtime = findViewById(R.id.textViewOBSTime);
        tvtime.setText(times);

        Button add = (Button)findViewById(R.id.buttonaddobs);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInputs();
            }
        });

    }

    private void Hikeinput() {
        Intent intent = new Intent(this, HikeInputActivity.class);
        startActivity(intent);
    }

    private void HikeList() {
        Intent intent = new Intent(this, HikeListActivity.class);
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

    private void getInputs() {
        try {
            TextView HikeID = (TextView) findViewById(R.id.textViewhikeid);
            EditText OBSName = (EditText) findViewById(R.id.editTextTextPersonObsName);
            TextView OBSTime = (TextView) findViewById(R.id.textViewOBSTime);
            EditText HikeDate = (EditText) findViewById(R.id.editTextTextPersoncomments);


            String strName = OBSName.getText().toString(),
                    strTime = OBSTime.getText().toString(),
                    strComments = HikeDate.getText().toString();

            if (strName.isEmpty() || strTime.isEmpty()) {
                displaymissinginfo();
            }
            if (!strName.isEmpty() && !strTime.isEmpty()
                    && !strComments.isEmpty()) {
                displayConfAlert(strName, strTime, strComments);
            }
            if (!strName.isEmpty() && !strTime.isEmpty()
                    && !strComments.isEmpty()) {

                displayConfAlertnoDesc(strName, strTime);
            }


        } catch (NullPointerException n) {
            displayNothingAlert();
        }
    }

    private void displayConfAlertnoDesc(String strName, String strTime) {
        new AlertDialog.Builder(this).setTitle("Is the information correct?").setMessage(
                "Details entered:\n" + "Observation Name:  " + strName
                        + "\n" + "Time:  " + strTime
        ).setNegativeButton("Back",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                saveDetails();
            }
        }).show();
    }

    private void displayConfAlert(String strName, String strTime,
                                  String strComments) {

        new AlertDialog.Builder(this).setTitle("Is the information correct?").setMessage(
                "Details entered:\n" + "Observation Name:  " + strName
                        + "\n" + "Time:  " + strTime
                        + "\n" + "Comments:  " + strComments
        ).setNegativeButton("Back",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                saveDetails();
            }
        }).show();
    }

    private void displaymissinginfo() {
        new AlertDialog.Builder(this).setTitle("All required fields not filled!").setMessage(
                "Please enter details in all required fields before submiting!").setNeutralButton("Back",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();
    }

    private void displayNothingAlert() {
        new AlertDialog.Builder(this).setTitle("No information entered!").setMessage(
                "Please enter details before submiting!").setNeutralButton("Back",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();
    }

    private void saveDetails() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        TextView HikeID = (TextView) findViewById(R.id.textViewhikeid);
        EditText OBSName = (EditText) findViewById(R.id.editTextTextPersonObsName);
        TextView OBSTime = (TextView) findViewById(R.id.textViewOBSTime);
        EditText HikeDate = (EditText) findViewById(R.id.editTextTextPersoncomments);


        String strName = OBSName.getText().toString(),
                strTime = OBSTime.getText().toString(),
                strComments = HikeDate.getText().toString(),
                strhikeid = HikeID.getText().toString();


        long obsID = dbHelper.InsertObservations(strName, strTime,
                strComments,strhikeid);

        Toast.makeText(this, "Observation has been added with id:" + obsID, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, HikeListActivity.class);
        startActivity(intent);


    }
}