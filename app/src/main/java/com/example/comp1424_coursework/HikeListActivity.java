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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HikeListActivity extends AppCompatActivity {
    int id2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar6);

        Button deleteall = (Button) findViewById(R.id.buttondeleteall);
        setSupportActionBar(toolbar);
        //get db
        DatabaseHelper dbHandler = new DatabaseHelper(this);
        //add a product
        //retrieve products from db
        ArrayList<Hikes> hikeslist = dbHandler.getHikes();
        // Create the adapter
        HikesAdapter adapter = new HikesAdapter(this, hikeslist);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.list_view5);
        //set adapter
        listView.setAdapter(adapter);
        deleteall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Deletedatabasealert();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Hikes hikes = adapter.getItem(position);
                id2 = hikes.get_id();
                String name = hikes.get_hikename();
                String location = hikes.get_hikelocation();
                String date = hikes.get_hikedate();
                String parking = hikes.get_hikeparking();
                String lenght = hikes.get_hikelenght();
                String difficulty = hikes.get_hikedifficulty();
                String weather = hikes.get_hikeweather();
                String heartrate = hikes.get_hikeheartrate();
                String description = hikes.get_hikedesc();
                PopupMenu popupMenu = new PopupMenu(HikeListActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.optionsmenu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.Observations:
                                Intent intentobservation = new Intent(HikeListActivity.this, HikeObservationListActivity.class);
                                intentobservation.putExtra("id2", id2);
                                startActivity(intentobservation);
                                finish();
                                Toast.makeText(HikeListActivity.this, name, Toast.LENGTH_LONG).show();
                                return true;
                            case R.id.ObservationsAdd:
                                Intent intentobservationadd = new Intent(HikeListActivity.this, HikeObservationAddActivity.class);
                                intentobservationadd.putExtra("id2", id2);
                                startActivity(intentobservationadd);
                                finish();
                                Toast.makeText(HikeListActivity.this, name, Toast.LENGTH_LONG).show();
                                return true;
                            case R.id.Edit:
                                Intent intentedithike = new Intent(HikeListActivity.this, HikeEditActivity.class);
                                intentedithike.putExtra("id2", id2);
                                intentedithike.putExtra("id", id);
                                intentedithike.putExtra("name", name);
                                intentedithike.putExtra("location", location);
                                intentedithike.putExtra("date", date);
                                intentedithike.putExtra("parking", parking);
                                intentedithike.putExtra("lenght", lenght);
                                intentedithike.putExtra("difficulty", difficulty);
                                intentedithike.putExtra("weather", weather);
                                intentedithike.putExtra("heartrate", heartrate);
                                intentedithike.putExtra("description", description);
                                startActivity(intentedithike);
                                finish();
                                Toast.makeText(HikeListActivity.this, name, Toast.LENGTH_LONG).show();
                                return true;
                            case R.id.Delete:
                                displaydeletenotification();
                                return true;
                        }
                        return true;

                    }
                });
                popupMenu.show();
            }
        });
    }



    private void displaydeletenotification() {
        new AlertDialog.Builder(this).setTitle("Are you sure you want to delete this hike?").setMessage(
                "If you delete the hike won't be recoverable!").setNegativeButton("Back",
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) { }
                }).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DeleteEntry();
            }
        }).show();
    }

    private void DeleteEntry() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        String strid = Integer.valueOf(id2).toString();

        dbHelper.DeleteEntry(strid);

        Intent intent = new Intent(this, HikeListActivity.class);
        startActivity(intent);

    }
    private void Deletedatabasealert() {
        new AlertDialog.Builder(this).setTitle("Are you sure you want to delete all?").setMessage(
                "If you delete the hikes won't be recoverable!").setNegativeButton("Back",
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) { }
                }).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DeleteAllDB();
            }
        }).show();

    }

    private void DeleteAllDB() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        dbHelper.deleteALL();

        Toast.makeText(this, "Hikes has been Deleted", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
}