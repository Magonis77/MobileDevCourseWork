package com.example.comp1424_coursework;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonHikeInput = (Button)findViewById(R.id.buttonHikeInput);
        Button buttonHikeList = (Button)findViewById(R.id.buttonhikelist);
        Button buttonHikeSearch = (Button)findViewById(R.id.buttonsearch);
        Button buttonJSON = (Button)findViewById(R.id.btnjson);
        Button btnloc = (Button)findViewById(R.id.btnlocation);
        buttonHikeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Hikeinput();
            }
        });
        buttonHikeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HikeList();
            }
        });
        buttonHikeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HikeSearch();
            }
        });
        buttonJSON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSON();
            }
        });
        btnloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Hikeloc();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void Hikeinput() {
        Intent intent = new Intent(this, HikeInputActivity.class);
        startActivity(intent);
    }
    private void Hikeloc() {
        Intent intent = new Intent(this, locationtest.class);
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
    private void JSON() {
        Intent intent = new Intent(this, activity_json.class);
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