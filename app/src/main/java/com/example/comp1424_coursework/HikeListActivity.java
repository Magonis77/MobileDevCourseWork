package com.example.comp1424_coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class HikeListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar6);
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
}