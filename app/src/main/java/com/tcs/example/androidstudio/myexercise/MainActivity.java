package com.tcs.example.androidstudio.myexercise;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.provider.SyncStateContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ProgressDialog pDialog;
    private ListView list;
    private ArrayList<Earthquake> earthquakes = null;
    private static final int READ_BLOCK_SIZE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setDisplayShowTitleEnabled(true);

        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        //actionbar.setIcon(R.drawable.ic_menu_white_24dp);
        actionbar.setTitle("EARTHQUAKES");


        //Initialize ListView
        //list = new ListView(this);

        list = (ListView)findViewById(R.id.list);

        list.setOnItemClickListener(this);

        //Start the operation to consume the webservice
        getWebServiceData();

        //Set ListView
        //setContentView(list);

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

            //Start the operation to consume the webservice
            getWebServiceData();

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

    public void getWebServiceData(){

        if(isNetworkAvailable(this)){
            //Invoke AsyncTask to consume web service
            new AsyncTaskC().execute();
        }else{

            new AlertDialog.Builder(this)
                    .setTitle("The Network Connection is not Available")
                    .setMessage("Do you want to get data of the last request?")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                                //Execute AsynTask to read a local json file
                                new AsyncTaskCLocal().execute();

                        }})
                    .setNegativeButton(android.R.string.no, null).show();

            /*
            Toast message =
                    Toast.makeText(getApplicationContext(),
                            "The Network Connection is not Available", Toast.LENGTH_SHORT);

            message.show();
            */
        }
    }

    //Method to check network available
    public boolean isNetworkAvailable(Context c) {
        ConnectivityManager connectMgr = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectMgr != null) {
            NetworkInfo[] netInfo = connectMgr.getAllNetworkInfo();
            if (netInfo != null) {
                for (NetworkInfo net : netInfo) {
                    if (net.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        else {

        }
        return false;


    }

    //Class CustomerAdapter to generate custom items for listview
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
            text2.setText("Magnitude: "+magnitude);


            double mag = Double.parseDouble(magnitude);

            if(mag < 1.0){
                //Green Color
                //text2.setTextColor(Color.rgb(0,100,0));
                //row.setBackgroundColor(Color.rgb(0, 100, 0));
                row.setBackground(makeColorStateListForItem(position,Color.argb(100,76,175,80),Color.rgb(76,175,80)));
            }else if(mag >= 1.0 && mag <5.0){
                //Yellow Color
                //text2.setTextColor(Color.rgb(255,215,0));
                //row.setBackgroundColor(Color.rgb(255, 215, 0));
                row.setBackground(makeColorStateListForItem(position,Color.argb(100,255,235,59),Color.rgb(255, 235, 59)));
            }else if(mag >= 5.0 && mag <9.0){
                //Orange Color
                //text2.setTextColor(Color.rgb(255, 140, 0));
                //row.setBackgroundColor(Color.rgb(255, 140, 0));
                row.setBackground(makeColorStateListForItem(position,Color.argb(100,255,152,0),Color.rgb(255, 152, 0)));
            }else if(mag >= 9.0 && mag < 10.0){
                //Red Color
                //text2.setTextColor(Color.rgb(255,0,0));
                //row.setBackgroundColor(Color.rgb(255, 0, 0));
                row.setBackground(makeColorStateListForItem(position,Color.argb(100,244,67,54),Color.rgb(244, 67, 54)));
            }else{
                //text2.setTextColor(Color.rgb(165,42,42));
                //row.setBackgroundColor(Color.rgb(165, 42, 42));
                row.setBackground(makeColorStateListForItem(position,Color.argb(100,165,42,42),Color.rgb(165,42,42)));

            }

            return row;

        }

        //Set state color to press and unpress item
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
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.show();
        }



        protected String doInBackground(Void... params) {

                Log.i("BACKGROUND", "doInBackground");

                //Connect and consume webservice
                WebServiceConnection webserviceresponse = new WebServiceConnection();

                if(webserviceresponse.getResponseService()){

                    //Store json file to use in offline mode
                    try {
                        FileOutputStream fileout=openFileOutput("jsonCache.txt", MODE_PRIVATE);
                        OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
                        outputWriter.write(webserviceresponse.getJSONString());
                        outputWriter.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                        return "error";
                    }

                    //Parse json and get elements
                    ParserJSON parse = new ParserJSON();
                    earthquakes = parse.parserJSON(webserviceresponse.getJSONObject());


                }else{
                    return "error";
                }


            return "ok";
        }

        protected void onPostExecute(String result) {

            Log.i("POSTEXECUTE", "onPostExecute");

            if(result.equals("ok")){
                Log.i("RESULT", result);
                //Initialize and build the CustomerAdapter
                CustomAdapter adapter = new CustomAdapter(MainActivity.this,earthquakes);

                //Set customer adapter to list
                list.setAdapter(adapter);

            }else{
                Toast message =
                        Toast.makeText(getApplicationContext(),
                                "There is a problem to recover data, try again later!!!", Toast.LENGTH_SHORT);

                message.show();
            }

            //Finish the progress dialog
            pDialog.dismiss();
        }
    }



    //Class AsyncTaskCLocal to get data of local json file
    class AsyncTaskCLocal extends AsyncTask<Void, Void, String> {

        protected void onPreExecute() {

            //Initialize and start Progress Dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.show();
        }



        protected String doInBackground(Void... params) {

            Log.i("BACKGROUND", "doInBackground");

            //Read local JSON Fila
            try {
                FileInputStream fileIn=openFileInput("jsonCache.txt");
                InputStreamReader InputRead= new InputStreamReader(fileIn);

                char[] inputBuffer= new char[READ_BLOCK_SIZE];
                String s="";
                int charRead;

                while ((charRead=InputRead.read(inputBuffer))>0) {
                    // char to string conversion
                    String readstring=String.copyValueOf(inputBuffer,0,charRead);
                    s +=readstring;
                }
                InputRead.close();

                JSONObject jsonObject = new JSONObject(s);

                //Parse json and get elements
                ParserJSON parse = new ParserJSON();
                earthquakes = parse.parserJSON(jsonObject);

            } catch (Exception e) {
                e.printStackTrace();
                return "error";
            }

            return "ok";
        }

        protected void onPostExecute(String result) {

            Log.i("POSTEXECUTE", "onPostExecute");

            if(result.equals("ok")){
                Log.i("RESULT", result);
                //Initialize and build the CustomerAdapter
                CustomAdapter adapter = new CustomAdapter(MainActivity.this,earthquakes);

                //Set customer adapter to list
                list.setAdapter(adapter);

            }else{
                Toast message =
                        Toast.makeText(getApplicationContext(),
                                "There is a problem to recover data, try again later!!!", Toast.LENGTH_SHORT);

                message.show();
            }

            //Finish the progress dialog
            pDialog.dismiss();
        }
    }


}
