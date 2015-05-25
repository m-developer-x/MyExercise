package com.tcs.example.androidstudio.myexercise;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by 825124 on 13/05/2015.
 */
public class ParserJSON {

    private final String KEY_FEATURES = "features";
    private final String KEY_PROPERTIES = "properties";
    private final String KEY_GEOMETRY = "geometry";
    private final String KEY_PLACE = "place";
    private final String KEY_MAG = "mag";
    private final String KEY_TIME = "time";
    private final String KEY_COORDINATES = "coordinates";





    public JSONObject parserJSON(String json)
    {
        /*
        String url = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";

        Log.i("START", "CONECTION");
        // ESTABLECIMIENTO CONEXION CON EL WEBSERVICE
        try
        {
            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httppost = new HttpPost(url);

            HttpResponse httpResponse = httpclient.execute(httppost);
            HttpEntity httpEntity = httpResponse.getEntity();




            Log.i("RESPONSE X", EntityUtils.toString(httpEntity));


        }
        catch (UnsupportedEncodingException e)
        {
            Log.i("RESPONSE", "UNSUPPORTEDENCODINGEXCEPTION");
            e.printStackTrace();
        }
        catch (ClientProtocolException e)
        {
            Log.i("RESPONSE", "CLIENTPROTOCOLEXCEPTION");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            Log.i("RESPONSE", "IOEXCEPTION");
            e.printStackTrace();
        }
        catch(Exception e)
        {
            Log.i("RESPONSE E", e.toString());
        }
        */
        return null;
    }

    public ArrayList<Earthquake> parserJSON(JSONObject json){

        ArrayList<Earthquake> earthquakes = new ArrayList<Earthquake>();

        try {
            JSONArray features = json.getJSONArray(KEY_FEATURES);
            int length = features.length();

            for(int i = 0; i < length; i++){

                //Initialize Earthquake bean
                Earthquake earthquake = new Earthquake();

                //Getting json object by index
                JSONObject jsonObject = features.getJSONObject(i);

                //Getting json object with "properties" identifier
                JSONObject jsonObjectProperties = jsonObject.getJSONObject(KEY_PROPERTIES);

                //Getting place, magnitude and time of json object properties
                String place = jsonObjectProperties.getString(KEY_PLACE);
                String magnitude = jsonObjectProperties.getString(KEY_MAG);
                String time = jsonObjectProperties.getString(KEY_TIME);

                //Getting json object with "geometry" identifier
                JSONObject jsonObjectGeometry = jsonObject.getJSONObject(KEY_GEOMETRY);

                //Getting json array for coordinates
                JSONArray coordinates = jsonObjectGeometry.getJSONArray(KEY_COORDINATES);

                //Getting lonfitude, latitude and depth of json array coordinates
                String longitude = coordinates.getString(0);
                String latitude = coordinates.getString(1);
                String depth = coordinates.getString(2);

                //Setting earthquake attributes
                earthquake.setPlace(place);
                earthquake.setMagnitude(magnitude);
                earthquake.setTime(time);
                earthquake.setLongitude(longitude);
                earthquake.setLatitude(latitude);
                earthquake.setDepth(depth);


                //Log.i("PLACE:", place);
                //Log.i("MAG:",magnitude);

                //Log.i("TIME:", time);
                //Log.i("LONGITUDE:",longitude);
                //Log.i("LATITUDE:",latitude);
                //Log.i("DEPTH:",depth);

                //Add earthquake to ArrayList
                earthquakes.add(earthquake);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return earthquakes;
    }



}
