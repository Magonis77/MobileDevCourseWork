package com.example.comp1424_coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class HikeSearchListActivity extends AppCompatActivity {
    int id2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_search_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar12);
        setSupportActionBar(toolbar);
        //get db
        DatabaseHelper dbHandler = new DatabaseHelper(this);
        //add a product
        //retrieve products from db
        String name = getIntent().getExtras().getString("name");
        String filter = getIntent().getExtras().getString("filter");
        System.out.println(filter);
        ArrayList<Hikes> hikeslist = null;
        try {
            switch (filter) {
                case "Name":
                    hikeslist = dbHandler.SearchDBName(name);
                    break;
                case "Location":
                    hikeslist = dbHandler.SearchDBLoc(name);
                    break;
                case "Lenght":
                    hikeslist = dbHandler.SearchDBLenght(name);
                    break;
                case "Date":
                    hikeslist = dbHandler.SearchDBDate(name);
                    break;
            }
        } catch (NullPointerException e) {

        }
        System.out.println("im in searchlist" + name);
        // Create the adapter
        SearchAdapter adapter = new SearchAdapter(this, hikeslist);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.listview6);
        //set adapter
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override //get the hike information and add to intent and start next activity of displaying info
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
                Intent intentedithike = new Intent(HikeSearchListActivity.this, DetailsActivity.class);
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
                Toast.makeText(HikeSearchListActivity.this, name, Toast.LENGTH_LONG).show();
            }

    });
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