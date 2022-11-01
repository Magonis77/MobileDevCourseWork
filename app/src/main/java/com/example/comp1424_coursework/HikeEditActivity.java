package com.example.comp1424_coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar);

        id = getIntent().getExtras().getInt("id");
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
        tvparkingyes = findViewById(R.id.radioButton);
        tvparkingno = findViewById(R.id.radioButton3);
        tvLenght = findViewById(R.id.editTextNumberLenghtOfHike3);
        tvDifficulty = findViewById(R.id.spinner3);
        tvdescription = findViewById(R.id.editTextTextPersonNameDescription3);
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
}