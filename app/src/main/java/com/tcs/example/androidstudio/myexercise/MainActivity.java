package com.tcs.example.androidstudio.myexercise;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        ListView list = new ListView(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.custom_row,
                R.id.line1, new String[] {"Bill","Tom","Sally","Jenny"});

        list.setAdapter(adapter);



        setContentView(list);
*/
        //setContentView(R.layout.activity_main);

        ListView list = new ListView(this);
        setContentView(list);

        CustomAdapter adapter = new CustomAdapter(this, R.layout.custom_row,
                android.R.id.text1, new String[] {"Bill","Tom","Sally","Jenny"});

        list.setAdapter(adapter);

        new AsyncTaskC().execute();

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static class CustomAdapter extends ArrayAdapter<String>{

        public CustomAdapter(Context context, int layout, int resId, String[] items ){

            super(context, layout, resId, items);
        }

        public View getView(int position, View convertView, ViewGroup parent){

            View row = convertView;
            //Inflate a new row if one isn't recycled
            if (row==null){

                row = LayoutInflater.from(getContext()).inflate(R.layout.custom_row, parent, false);
            }

            String item = getItem(position);

            //ImageView left = (ImageView)row.findViewById(R.id.leftimage);
            //ImageView right = (ImageView)row.findViewById(R.id.rightimage);
            TextView text = (TextView)row.findViewById(android.R.id.text1);

            //left.setImageResource(R.drawable.icon);
            //right.setImageResource(R.drawable.icon);
            text.setText(item);

            return row;

        }

    }

    class AsyncTaskC extends AsyncTask<String, Void, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            try {

                ParserJSON parse = new ParserJSON();
                parse.consult();


            } catch (Exception e) {
                this.exception = e;
                return null;
            }
            return "";
        }

        protected void onPostExecute(String feed) {

        }
    }
}
