package com.tcs.example.androidstudio.myexercise;

import android.os.Bundle;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;
import java.util.Locale;


public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Set action bar
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setTitle("EARTHQUAKES");

        //Get bundle with data of Earthquakes
        Bundle bundle = getIntent().getExtras();
        Earthquake earthquake = bundle.getParcelable("Earthquake");

        //Convert timestamp to date
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(earthquake.getTime()));
        String date = DateFormat.format("dd-MM-yyyy HH:mm:ss", cal).toString();

        //Get instance of Detail Fragment
        DetailFragment detailFragment = DetailFragment.newInstance(earthquake.getPlace(),earthquake.getMagnitude(),
                date,earthquake.getDepth());

        //Set Fragment in Activity
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(android.R.id.content, detailFragment, "DetailFragment");
        fragmentTransaction.commit();

        //Set latitude and longitude in LatLng Object
        LatLng UPV = new LatLng(Double.parseDouble(earthquake.getLatitude()), Double.parseDouble(earthquake.getLongitude()));

        //Get instance of Map Fragment
        GoogleMap map = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(UPV,5));

        //Set map attributes
        map.addMarker(new MarkerOptions()
                .position(UPV)
                .title("EARTHQUAKE")
                .snippet(earthquake.getPlace())
                .icon(BitmapDescriptorFactory
                        .fromResource(android.R.drawable.ic_menu_compass))
                .anchor(0.5f, 0.5f));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            Log.i("HOMEASUP","FINISH");
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
