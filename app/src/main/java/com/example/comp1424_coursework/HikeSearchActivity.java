package com.example.comp1424_coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class HikeSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        Button search = (Button)findViewById(R.id.buttonSearch);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInputs();
            }
        });

    }

    private void getInputs() {
        EditText HikeName = (EditText) findViewById(R.id.editTextTextPersonSearch);

        String strname = HikeName.getText().toString();
        System.out.println("Im in search activity " + strname);
        Intent intent = new Intent(this, HikeSearchListActivity.class);
        RadioGroup Filters = (RadioGroup) findViewById(R.id.radioGroup4);
        RadioButton radioButtonInput =
                (RadioButton) findViewById(Filters.getCheckedRadioButtonId());
        intent.putExtra("filter", radioButtonInput.getText().toString());
        intent.putExtra("name", strname);
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