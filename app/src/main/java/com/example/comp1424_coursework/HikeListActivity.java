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
            @Override //listener of the list on when the item in the list is clicked
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //gets the information of that hike from Db
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
                //pop ups the menu with options
                PopupMenu popupMenu = new PopupMenu(HikeListActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.optionsmenu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override //listens for what option is selected
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.Observations:
                                //passes the information of the hike id to obersvations screen and shows observations screen
                                Intent intentobservation = new Intent(HikeListActivity.this, HikeObservationListActivity.class);
                                intentobservation.putExtra("id2", id2);
                                startActivity(intentobservation);
                                finish();
                                Toast.makeText(HikeListActivity.this, name, Toast.LENGTH_LONG).show();
                                return true;
                            case R.id.ObservationsAdd:
                                //passes the information to the observations add screen and opens that screen.
                                Intent intentobservationadd = new Intent(HikeListActivity.this, HikeObservationAddActivity.class);
                                intentobservationadd.putExtra("id2", id2);
                                startActivity(intentobservationadd);
                                finish();
                                Toast.makeText(HikeListActivity.this, name, Toast.LENGTH_LONG).show();
                                return true;
                            case R.id.Edit:
                                //passes the hike information to edit screen and opens that screen
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
                                //displays the delete confirmation alert box
                                displaydeletenotification();
                                return true;
                            case R.id.ShowonMap:
                                //starts the screen of the map and passes the location information of specific hike
                                Intent intentmap = new Intent(HikeListActivity.this, activity_map.class);
                                intentmap.putExtra("location", location);
                                startActivity(intentmap);
                                finish();
                                Toast.makeText(HikeListActivity.this, name, Toast.LENGTH_LONG).show();
                                return true;
                        }
                        return true;

                    }
                });
                popupMenu.show();
            }
        });
    }


    //alert box that asks if user really wants to delete the hike
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
    //deletes the hike from db
    private void DeleteEntry() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        String strid = Integer.valueOf(id2).toString();

        dbHelper.DeleteEntry(strid);

        Intent intent = new Intent(this, HikeListActivity.class);
        startActivity(intent);

    }
    //delete all data from the db alert box that makes sure the user didnt click it by accident
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
    //deletes all data from db
    private void DeleteAllDB() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        dbHelper.deleteALL();

        Toast.makeText(this, "Hikes has been Deleted", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    //Starts new intent(screen) when Create hike is selected from the drop down menu in toolbar
    private void Hikeinput() {
        Intent intent = new Intent(this, HikeInputActivity.class);
        startActivity(intent);
    }
    //Starts new intent(screen) when Hike List is selected from the drop down menu in toolbar
    private void HikeList() {
        Intent intent = new Intent(this, HikeListActivity.class);
        startActivity(intent);
    }
    //Starts new intent(screen) when Create hike is selected from the drop down menu in toolbar
    private void HikeSearch() {
        Intent intent = new Intent(this, HikeSearchActivity.class);
        startActivity(intent);
    }
    //Starts new intent(screen) when Main Menu is selected from the drop down menu in toolbar
    private void MainMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override //listens for drop down menu clicks on the toolbar
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