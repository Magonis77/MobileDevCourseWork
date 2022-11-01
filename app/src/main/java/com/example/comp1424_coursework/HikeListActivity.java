package com.example.comp1424_coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HikeListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar6);
        setSupportActionBar(toolbar);
        //get db
        DatabaseHelper dbHandler = new DatabaseHelper(this);
        //add a product
        //retrieve products from db
        ArrayList<Hikes> hikeslist = dbHandler.getHikes();
        // Create the adapter
        HikesAdapter adapter = new HikesAdapter(this, hikeslist);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.list_view);
        //set adapter
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Hikes hikes = adapter.getItem(position);
                int id2 = hikes.get_id();
                String name = hikes.get_hikename();
                String location = hikes.get_hikelocation();
                String date = hikes.get_hikedate();
                String parking = hikes.get_hikeparking();
                String lenght = hikes.get_hikelenght();
                String difficulty = hikes.get_hikedifficulty();
                String description = hikes.get_hikedesc();
                Intent intent = new Intent(HikeListActivity.this, HikeEditActivity.class);
                intent.putExtra("id2", id2);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("location", location);
                intent.putExtra("date", date);
                intent.putExtra("parking", parking);
                intent.putExtra("lenght", lenght);
                intent.putExtra("difficulty", difficulty);
                intent.putExtra("description", description);
                startActivity(intent);
                finish();
                Toast.makeText(HikeListActivity.this, name, Toast.LENGTH_LONG).show();
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