package com.tcs.example.androidstudio.myexercise;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.provider.SyncStateContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ProgressDialog pDialog;
    private ListView list;
    private ArrayList<Earthquake> earthquakes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionbar = getSupportActionBar();
        //actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setIcon(R.drawable.ic_menu_white_24dp);
        actionbar.setTitle("EARTHQUAKES");

        //Initialize ListView
        list = new ListView(this);

        list.setOnItemClickListener(this);

        //Invoke AsyncTask
        new AsyncTaskC().execute();


        //Set ListView
        setContentView(list);

        Log.i("MESSAGE", "MAIN ACTIVITY");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.update) {

            //Invoke AsyncTask
            new AsyncTaskC().execute();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Earthquake earthquake = earthquakes.get(position);

        Bundle bundle = new Bundle();
        bundle.putParcelable("Earthquake", earthquake);
        Intent detailActivity = new Intent(this, DetailActivity.class);
        detailActivity.putExtras(bundle);
        startActivity(detailActivity);

        Log.i("ONCLICK","LISTENER OF LISTVIEW");

    }

    private static class CustomAdapter extends ArrayAdapter<Earthquake>{

        public CustomAdapter(Context context, ArrayList<Earthquake> earthquakes){

            super(context,0,earthquakes);
        }


        //Method to build custom item
        public View getView(int position, View convertView, ViewGroup parent){

            View row = convertView;

            //Inflate a new row if one isn't recycled
            if (row==null){

                row = LayoutInflater.from(getContext()).inflate(R.layout.custom_row, parent, false);
            }

            //Initialize Earthquake bean that represent an item if the list
            Earthquake item = getItem(position);

            //Initialize components of item
            ImageView right = (ImageView)row.findViewById(R.id.rightimage);
            TextView text1 = (TextView)row.findViewById(R.id.line1);
            TextView text2 = (TextView)row.findViewById(R.id.line2);

            String place = item.getPlace();
            String magnitude = item.getMagnitude();

            //Set values to components of item
            right.setImageResource(R.drawable.ic_info_outline_white_24dp);
            text1.setText(place);
            text2.setText(magnitude);


            double mag = Double.parseDouble(magnitude);

            if(mag < 1.0){
                //text2.setTextColor(Color.rgb(0,100,0));
                //row.setBackgroundColor(Color.rgb(0, 100, 0));
                row.setBackground(makeColorStateListForItem(position,Color.argb(100,0,100,0),Color.rgb(0,100,0)));
            }else if(mag >= 1.0 && mag <5.0){
                //text2.setTextColor(Color.rgb(255,215,0));
                //row.setBackgroundColor(Color.rgb(255, 215, 0));
                row.setBackground(makeColorStateListForItem(position,Color.argb(100,255,215,0),Color.rgb(255, 215, 0)));
            }else if(mag >= 5.0 && mag <9.0){
                //text2.setTextColor(Color.rgb(255, 140, 0));
                //row.setBackgroundColor(Color.rgb(255, 140, 0));
                row.setBackground(makeColorStateListForItem(position,Color.argb(100,255,140,0),Color.rgb(255, 140, 0)));
            }else if(mag >= 9.0 && mag < 10.0){
                //text2.setTextColor(Color.rgb(255,0,0));
                //row.setBackgroundColor(Color.rgb(255, 0, 0));
                row.setBackground(makeColorStateListForItem(position,Color.argb(100,255,0,0),Color.rgb(255, 0, 0)));
            }else{
                //text2.setTextColor(Color.rgb(165,42,42));
                //row.setBackgroundColor(Color.rgb(165, 42, 42));
                row.setBackground(makeColorStateListForItem(position,Color.argb(100,165,42,42),Color.rgb(165,42,42)));

            }

            return row;

        }

        private Drawable makeColorStateListForItem(int position,int press, int deft){

            int pressedColor = press;
            //int checkedColor = checkedColorForItem(position);
            int defaultColor = deft;



            StateListDrawable stateListDrawable = new StateListDrawable();

            stateListDrawable.addState(new int[]{android.R.attr.state_pressed},new ColorDrawable(pressedColor));
            //stateListDrawable.addState(new int[]{android.R.attr.state_checked},new ColorDrawable(checkedColor));
            stateListDrawable.addState(new int[]{0},new ColorDrawable(defaultColor));


            return stateListDrawable;
        }

        private int pressedColorForItem(int position){

            return Color.BLUE;
        }

        private int checkedColorForItem(int position){

            return Color.RED;
        }

        private int defaultColorForItem(int position){

            return Color.WHITE;
        }

    }

    //Class AsyncTaskC to consume webservice of Earthquakes
    class AsyncTaskC extends AsyncTask<Void, Void, String> {

        protected void onPreExecute() {

            //Initialize and start Progress Dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Cargando...");
            pDialog.setIndeterminate(false);
            pDialog.show();
        }



        protected String doInBackground(Void... params) {

                Log.i("BACKGROUND", "doInBackground");

                //Connect and consume webservice
                WebServiceConnection webserviceresponse = new WebServiceConnection();
                JSONObject jsonresponse = webserviceresponse.getResponseService();

                //Parse json and get elements
                ParserJSON parse = new ParserJSON();
                earthquakes = parse.parserJSON(jsonresponse);

            return null;
        }

        protected void onPostExecute(String result) {

            //Initialize and build the CustomerAdapter
            Log.i("POSTEXECUTE", "onPostExecute");
            CustomAdapter adapter = new CustomAdapter(MainActivity.this,earthquakes);

            //Set customer adapter to list
            list.setAdapter(adapter);

            //Finish the progress dialog
            pDialog.dismiss();
        }
    }
}
