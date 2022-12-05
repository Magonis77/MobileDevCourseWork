package com.example.comp1424_coursework;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.time.LocalDate;

public class HikeInputActivity extends AppCompatActivity {

    FusedLocationProviderClient mFusedLocationClient;
    //Defines the permission for location
    int PERMISSION_ID = 44;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_input);
        Button save = (Button)findViewById(R.id.buttonsubmit);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // method to get the location
        getLastLocation();
        save.setOnClickListener(new View.OnClickListener() {
            @Override // listener on create hike button click to get inputs
            public void onClick(View view) {
                getInputs();
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
    }
    //gets the last location
    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            //enters the location information into the location field
                            EditText Hikelocation = (EditText) findViewById(R.id.editTextTextPersonNameLocation);
                            Hikelocation.setText(location.getLatitude() + "," +location.getLongitude() );
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }
    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            EditText Hikelocation = (EditText) findViewById(R.id.editTextTextPersonNameLocation);
            Hikelocation.setText(mLastLocation.getLatitude() + "," + mLastLocation.getLongitude());
        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
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

    //creates the date picker on selection
    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            LocalDate d = LocalDate.now();
            int year = d.getYear();
            int month = d.getMonthValue();
            int day = d.getDayOfMonth();

            return new DatePickerDialog(getActivity(), this, year, --month, day);
        }

        @Override   //gets the date selected
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            LocalDate dob = LocalDate.of(year, ++month, day);
            ((HikeInputActivity)getActivity()).updateDOB(dob);

        }

    }
    //shows the calendar
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public void updateDOB(LocalDate dob) {
        TextView dobText = (TextView)findViewById(R.id.textViewDateSelector);
        dobText.setText(dob.toString());
    }
    //gets the information that is entered by the user and the app
    private void getInputs() {
        try {
            EditText HikeName = (EditText) findViewById(R.id.editTextTextPersonNameHike);

            EditText Hikelocation = (EditText) findViewById(R.id.editTextTextPersonNameLocation);
            TextView HikeDate = (TextView) findViewById(R.id.textViewDateSelector);
            RadioGroup HikeParking = (RadioGroup) findViewById(R.id.radioGroup);
            RadioButton radioButtonInput =
                    (RadioButton) findViewById(HikeParking.getCheckedRadioButtonId());
            EditText HikeLenght = (EditText) findViewById(R.id.editTextNumberLenghtOfHike);
            Spinner Difficulty = (Spinner) findViewById(R.id.spinner);
            EditText Weather = (EditText) findViewById(R.id.editTextTextPersonWeather);
            EditText HeartRate = (EditText) findViewById(R.id.editTextNumberHeartRate);
            EditText HikeDesc = (EditText) findViewById(R.id.editTextTextPersonNameDescription);


            String strName = HikeName.getText().toString(),
                    strDifficulty = Difficulty.getSelectedItem().toString(),
                    strParking = radioButtonInput.getText().toString(),
                    strLenght = HikeLenght.getText().toString(),
                    strLocation = Hikelocation.getText().toString(),
                    strDate = HikeDate.getText().toString(),
                    strWeather = Weather.getText().toString(),
                    strHeartRate = HeartRate.getText().toString(),
                    strDesc = HikeDesc.getText().toString();
            //checks if the fields are entered
            if (strName.isEmpty() || strDifficulty.isEmpty()
                    || strParking.isEmpty() || strLenght.isEmpty()
                    || strLocation.isEmpty() || strDate.isEmpty() || strWeather.isEmpty() || strHeartRate.isEmpty()) {
                displaymissinginfo();
            }
            //checks if the info is entered with description and then shows the alert box to double check enetered information
            if (!strName.isEmpty() && !strDifficulty.isEmpty()
                    && !strParking.isEmpty() && !strLenght.isEmpty()
                    && !strLocation.isEmpty() && !strDate.isEmpty() && !strWeather.isEmpty() && !strHeartRate.isEmpty()
                    && !strDesc.isEmpty()) {
                displayConfAlert(strName, strLocation, strDate, strParking, strLenght, strDifficulty,strWeather,strHeartRate, strDesc);
            }
            //checks if the info is entered without description and shows the information in alert box for double checking
            if (!strName.isEmpty() && !strDifficulty.isEmpty()
                    && !strParking.isEmpty() && !strLenght.isEmpty()
                    && !strLocation.isEmpty() && !strDate.isEmpty() && !strWeather.isEmpty() && !strHeartRate.isEmpty() && strDesc.isEmpty()) {

                displayConfAlertnoDesc(strName, strLocation, strDate, strParking, strLenght,strWeather,strHeartRate, strDifficulty);
            }


        }catch(NullPointerException n){
            displayNothingAlert();
        }
    }
    //alert box that asks the user to enter all required information
    private void displaymissinginfo() {
        new AlertDialog.Builder(this).setTitle("All required fields not filled!").setMessage(
                "Please enter details in all required fields before submiting!").setNeutralButton("Back",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) { }
                }).show();
    }
    //alert box that asks the user to enter information
    private void displayNothingAlert() {
        new AlertDialog.Builder(this).setTitle("No information entered!").setMessage(
                "Please enter details before submiting!").setNeutralButton("Back",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) { }
                }).show();
    }
    //alert box that displays the informatin without description to double check.
    private void displayConfAlertnoDesc(String strName, String strLocation,
                                        String strDate, String strParking,
                                        String strLenght,String strWeather,String strHeartRate, String strDifficulty) {
        new AlertDialog.Builder(this).setTitle("Is the information correct?").setMessage(
                "Details entered:\n" + "Hike Name:  " + strName
                        + "\n" + "Location:  " + strLocation
                        + "\n" + "Hike Date:  " + strDate
                        + "\n" + "Parking Availability:  " + strParking
                        + "\n" + "Hike length:  " + strLenght
                        + "\n" + "Hike Difficulty:  "+ strDifficulty
                        + "\n" + "Hike Weather:  " + strWeather
                        + "\n" + "Hike Heart Rate:  " + strHeartRate
                        ).setNegativeButton("Back",
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) { }
                }).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                saveDetails();
            }
        }).show();
    }
    //alert box that displays entered info for confirmation with description.
    private void displayConfAlert(String strName, String strLocation,
                                  String strDate, String strParking,
                                  String strLenght, String strDifficulty,
                                  String strWeather, String strHeartRate,
                                  String strDesc) {

        new AlertDialog.Builder(this).setTitle("Is the information correct?").setMessage(
                "Details entered:\n" + "Hike Name:  " + strName
                        + "\n" + "Location:  " + strLocation
                        + "\n" + "Hike Date:  " + strDate
                        + "\n" + "Parking Availability:  " + strParking
                        + "\n" + "Hike length:  " + strLenght
                        + "\n" + "Hike Difficulty:  " + strDifficulty
                        + "\n" + "Hike Weather:  " + strWeather
                        + "\n" + "Hike Heart Rate:  " + strHeartRate
                        + "\n" + "Hike Description:  " + strDesc).setNegativeButton("Back",
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) { }
                }).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                saveDetails();
            }
        }).show();
    }
    //saves the details into the database by amending the selected hike for edit
    private void saveDetails() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        EditText HikeName = (EditText) findViewById(R.id.editTextTextPersonNameHike);

        EditText Hikelocation = (EditText) findViewById(R.id.editTextTextPersonNameLocation);
        TextView HikeDate = (TextView) findViewById(R.id.textViewDateSelector);
        RadioGroup HikeParking = (RadioGroup) findViewById(R.id.radioGroup);
        RadioButton radioButtonInput =
                (RadioButton) findViewById(HikeParking.getCheckedRadioButtonId());
        EditText HikeLenght = (EditText) findViewById(R.id.editTextNumberLenghtOfHike);
        Spinner Difficulty = (Spinner) findViewById(R.id.spinner);
        EditText Weather = (EditText) findViewById(R.id.editTextTextPersonWeather);
        EditText HeartRate = (EditText) findViewById(R.id.editTextNumberHeartRate);
        EditText HikeDesc = (EditText) findViewById(R.id.editTextTextPersonNameDescription);


        String strName = HikeName.getText().toString(),
                strDifficulty = Difficulty.getSelectedItem().toString(),
                strParking = radioButtonInput.getText().toString(),
                strLenght = HikeLenght.getText().toString(),
                strLocation = Hikelocation.getText().toString(),
                strDate = HikeDate.getText().toString(),
                strWeather = Weather.getText().toString(),
                strHeartRate = HeartRate.getText().toString(),
                strDesc = HikeDesc.getText().toString();

        long hikeId = dbHelper.InsertDetails(strName, strLocation,
                strDate,  strParking,
                strLenght,  strDifficulty,
                strWeather, strHeartRate,
                strDesc);

        Toast.makeText(this, "Hike has been created with id:" + hikeId, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, HikeListActivity.class);
        startActivity(intent);
    }
}