package com.tcs.example.androidstudio.myexercise;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by 825124 on 13/05/2015.
 */
public class ParserJSON {

    public JSONObject consult()
    {
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

        return null;
    }



}
