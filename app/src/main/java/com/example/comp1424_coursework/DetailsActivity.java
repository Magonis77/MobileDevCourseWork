package com.example.comp1424_coursework;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailsActivity extends AppCompatActivity {
    int id;
    TextView tvname;
    TextView tvlocation;
    TextView tvDate;
    TextView tvparking;
    TextView tvLenght;
    TextView tvdescription;
    TextView tvparkings;
    TextView tvDifficulty;
    TextView tvWeather;
    TextView tvHeartRate;
    TextView tvid;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);


        id = getIntent().getExtras().getInt("id");
        int id2 = getIntent().getExtras().getInt("id2");
        String name = getIntent().getExtras().getString("name");
        String location = getIntent().getExtras().getString("location");
        String date = getIntent().getExtras().getString("date");
        String parking = getIntent().getExtras().getString("parking");
        String lenght = getIntent().getExtras().getString("lenght");
        String difficulty = getIntent().getExtras().getString("difficulty");
        String weather = getIntent().getExtras().getString("weather");
        String heartrate = getIntent().getExtras().getString("heartrate");
        String description = getIntent().getExtras().getString("description");
        tvname = findViewById(R.id.textview_name2);
        tvlocation = findViewById(R.id.textView_location2);
        tvDate = findViewById(R.id.textView_Date2);
        tvparkings = findViewById(R.id.textView_parking2);
        tvLenght = findViewById(R.id.textView_lenght2);
        tvDifficulty = findViewById(R.id.textView_diff2);
        tvWeather = findViewById(R.id.textViewweather2);
        tvHeartRate = findViewById(R.id.textViewheartrate2);
        tvdescription = findViewById(R.id.textView_desc2);
        tvid = findViewById(R.id.textview_id2);
        String idString = String.valueOf(id2);
        tvid.setText("ID: " + idString);
        tvname.setText("Name: " +name);
        tvlocation.setText("Location: " + location);
        tvLenght.setText("Lenght: " + lenght);
        tvDate.setText("Date: " + date);
        tvparkings.setText("Parking: " + parking);
        tvDifficulty.setText("Difficulty: " + difficulty);
        tvWeather.setText("Weather: " + weather);
        tvHeartRate.setText("Heart rate: " + heartrate);
        tvdescription.setText("Description: " + description);

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
    private void backmethod() {
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


}
